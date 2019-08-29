/*
MIT License

Copyright (c) 2018-2019 Gang ZHANG

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package depends.entity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import depends.entity.repo.EntityRepo;
import depends.relations.Inferer;
import depends.util.TemporaryFile;

/**
 * ContainerEntity for example file, class, method, etc. they could contain
 * vars, functions, ecpressions, type parameters, etc.
 */
public abstract class ContainerEntity extends DecoratedEntity {
	private static final Logger logger = LoggerFactory.getLogger(ContainerEntity.class);

	private ArrayList<VarEntity> vars;
	private ArrayList<FunctionEntity> functions;
	private HashMap<Object, Expression> expressions;
	private ArrayList<Expression> expressionList;
	private Collection<String> mixins;
	private Collection<ContainerEntity> resolvedMixins;

	public ContainerEntity() {
		vars = new ArrayList<>();
		functions = new ArrayList<>();
		mixins = new ArrayList<>();
		resolvedMixins = new ArrayList<>();
		expressions = new HashMap<>();
		expressionList = new ArrayList<>();
	}

	public ContainerEntity(String rawName, Entity parent, Integer id) {
		super(rawName, parent, id);
		vars = new ArrayList<>();
		functions = new ArrayList<>();
		mixins = new ArrayList<>();
		resolvedMixins = new ArrayList<>();
		expressions = new HashMap<>();
		expressionList = new ArrayList<>();
	}

	public void addVar(VarEntity var) {
		if (logger.isDebugEnabled()) {
			logger.debug("var found: " + var.getRawName() + ":" + var.getRawType());
		}
		this.vars.add(var);
	}

	public ArrayList<VarEntity> getVars() {
		return this.vars;
	}

	public void addFunction(FunctionEntity functionEntity) {
		this.functions.add(functionEntity);
	}

	public ArrayList<FunctionEntity> getFunctions() {
		return this.functions;
	}

	public HashMap<Object, Expression> expressions() {
		return expressions;
	}

	public void addExpression(Object key, Expression expression) {
		expressions.put(key, expression);
		expressionList.add(expression);
	}

	/**
	 * For all data in the class, infer their types. Should be override in
	 * sub-classes
	 */
	public void inferLocalLevelEntities(Inferer inferer) {
		super.inferLocalLevelEntities(inferer);
		for (VarEntity var : this.vars) {
			var.inferLocalLevelEntities(inferer);
		}
		for (FunctionEntity func : this.functions) {
			func.inferLocalLevelEntities(inferer);
		}
		resolvedMixins = identiferToContainerEntity(inferer, mixins);
		if (inferer.isEagerExpressionResolve()) {
			this.resolveExpressions(inferer);
		}
	}

	private Collection<ContainerEntity> identiferToContainerEntity(Inferer inferer, Collection<String> identifiers) {
		ArrayList<ContainerEntity> r = new ArrayList<>();
		for (String identifier : identifiers) {
			Entity entity = inferer.resolveName(this, identifier, true);
			if (entity == null) {
				continue;
			}
			if (entity instanceof ContainerEntity)
				r.add((ContainerEntity) entity);
		}
		return r;
	}

	/**
	 * Resolve all expression's type
	 * 
	 * @param inferer
	 */
	public void resolveExpressions(Inferer inferer) {
		for (Expression expression : expressionList) {
			// 1. if expression's type existed, break;
			if (expression.getType() != null)
				continue;
			if (expression.isDot) { // wait for previous
				continue;
			}
			if (expression.rawType == null && expression.identifier == null)
				continue;

			// 2. if expression's rawType existed, directly infer type by rawType
			// if expression's rawType does not existed, infer type based on identifiers
			if (expression.rawType != null) {
				expression.setType(inferer.inferTypeFromName(this, expression.rawType), null, inferer);
				if (expression.getType() != null) {
					continue;
				}
			}
			if (expression.identifier != null) {
				Entity entity = inferer.resolveName(this, expression.identifier, true);
				if (entity != null) {
					expression.setType(entity.getType(), entity, inferer);
					continue;
				}
				if (expression.isCall) {
					FunctionEntity func = this.lookupFunctionInVisibleScope(expression.identifier);
					if (func != null) {
						expression.setType(func.getType(), func, inferer);
					}
				} else {

					VarEntity varEntity = this.lookupVarInVisibleScope(expression.identifier);
					if (varEntity != null) {
						expression.setType(varEntity.getType(), varEntity, inferer);
					}
				}
			}
		}
	}

	public void cacheExpressions() {
		this.expressions = new HashMap<>();
		cacheExpressionListToFile();
		this.expressionList = new ArrayList<>();
	}

	public void clearExpressions() {
		this.expressions = new HashMap<>();
		this.expressionList = new ArrayList<>();
	}
	
	private void cacheExpressionListToFile() {
		try {
			FileOutputStream fileOut = new FileOutputStream(TemporaryFile.getInstance().exprPath(this.id));
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this.expressionList);
			out.close();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void reloadExpression(EntityRepo repo) {
		try
	      {
	         FileInputStream fileIn = new FileInputStream(TemporaryFile.getInstance().exprPath(this.id));
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         expressionList = (ArrayList<Expression>) in.readObject();
	         if (expressionList==null) expressionList = new ArrayList<>();
	         for (Expression expr:expressionList) {
	        	 expr.reload(repo,expressionList);
	         }
	         in.close();
	         fileIn.close();
	      }catch(IOException | ClassNotFoundException i)
	      {
	         return;
	      }
	}
	
	public TypeEntity getLastExpressionType() {
		//TODO: 
		/*
    java.lang.NullPointerException
	at depends.entity.ContainerEntity.getLastExpressionType(ContainerEntity.java:228)
	at depends.entity.FunctionEntity.inferLocalLevelEntities(FunctionEntity.java:92)
	at depends.entity.ContainerEntity.inferLocalLevelEntities(ContainerEntity.java:118)
	at depends.entity.TypeEntity.inferLocalLevelEntities(TypeEntity.java:69)
	at depends.entity.Entity.inferEntities(Entity.java:171)
	at depends.entity.Entity.inferEntities(Entity.java:173)
	at depends.relations.Inferer.resolveTypes(Inferer.java:91)
	at depends.relations.Inferer.resolveAllBindings(Inferer.java:78)
	at depends.extractor.AbstractLangProcessor.resolveBindings(AbstractLangProcessor.java:112)
	at depends.extractor.AbstractLangProcessor.buildDependencies(AbstractLangProcessor.java:101)
	at depends.Main.executeCommand(Main.java:121)
	at depends.Main.main(Main.java:60) 
		 * */
		for (int i = this.expressionList.size() - 1; i >= 0; i--) {
			Expression expr = this.expressionList.get(i);
			if (expr.isStatement)
				return expr.getType();
		}
		return null;
	}

	public List<Expression> expressionList() {
		return expressionList;
	}

	public boolean containsExpression() {
		return expressions.size() > 0;
	}

	public String dumpExpressions() {
		StringBuilder sb = new StringBuilder();
		for (Expression exp : expressionList) {
			sb.append(exp.toString()).append("\n");
		}
		return sb.toString();
	}


	
	/**
	 * The entry point of lookup functions. It will treat multi-declare entities and
	 * normal entity differently. - for multiDeclare entity, it means to lookup all
	 * entities - for normal entity, it means to lookup entities from current scope
	 * still root
	 * 
	 * @param functionName
	 * @return
	 */
	public FunctionEntity lookupFunctionInVisibleScope(String functionName) {
		if (this.getMutliDeclare() != null) {
			for (ContainerEntity fromEntity : this.getMutliDeclare().getEntities()) {
				FunctionEntity f = lookupFunctionBottomUpTillTopContainer(functionName, fromEntity);
				if (f != null)
					return f;
			}
		} else {
			ContainerEntity fromEntity = this;
			return lookupFunctionBottomUpTillTopContainer(functionName, fromEntity);
		}
		return null;
	}

	/**
	 * lookup function bottom up till the most outside container
	 * 
	 * @param functionName
	 * @param fromEntity
	 * @return
	 */
	private FunctionEntity lookupFunctionBottomUpTillTopContainer(String functionName, ContainerEntity fromEntity) {
		while (fromEntity != null) {
			if (fromEntity instanceof ContainerEntity) {
				FunctionEntity func = ((ContainerEntity) fromEntity).lookupFunctionLocally(functionName);
				if (func != null)
					return func;
			}
			fromEntity = (ContainerEntity) this.getAncestorOfType(ContainerEntity.class);
		}
		return null;
	}

	/**
	 * lookup function in local entity. It could be override such as the type entity
	 * (it should also lookup the inherit/implemented types
	 * 
	 * @param functionName
	 * @return
	 */
	public FunctionEntity lookupFunctionLocally(String functionName) {
		for (FunctionEntity func : getFunctions()) {
			if (func.getRawName().equals(functionName))
				return func;
		}
		return null;
	}

	/**
	 * The entry point of lookup var. It will treat multi-declare entities and
	 * normal entity differently. - for multiDeclare entity, it means to lookup all
	 * entities - for normal entity, it means to lookup entities from current scope
	 * still root
	 * 
	 * @param varName
	 * @return
	 */
	public VarEntity lookupVarInVisibleScope(String varName) {
		ContainerEntity fromEntity = this;
		return lookupVarBottomUpTillTopContainer(varName, fromEntity);
	}

	/**
	 * To found the var.
	 * 
	 * @param fromEntity
	 * @param varName
	 * @return
	 */
	private VarEntity lookupVarBottomUpTillTopContainer(String varName, ContainerEntity fromEntity) {
		while (fromEntity != null) {
			if (fromEntity instanceof ContainerEntity) {
				VarEntity var = ((ContainerEntity) fromEntity).lookupVarLocally(varName);
				if (var != null)
					return var;
			}
			fromEntity = (ContainerEntity) this.getAncestorOfType(ContainerEntity.class);
		}
		return null;
	}

	public VarEntity lookupVarLocally(String varName) {
		for (VarEntity var : getVars()) {
			if (var.getRawName().equals(varName))
				return var;
		}
		return null;
	}

	public void addMixin(String moduleName) {
		mixins.add(moduleName);
	}

	public Collection<ContainerEntity> getResolvedMixins() {
		return resolvedMixins;
	}



}
