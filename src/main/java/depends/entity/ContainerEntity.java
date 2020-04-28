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
import java.lang.ref.WeakReference;
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
	WeakReference<HashMap<Object, Expression>> expressionWeakReference;
	private ArrayList<Expression> expressionList;
	private int expressionCount = 0;
	private Collection<GenericName> mixins;
	private Collection<ContainerEntity> resolvedMixins;

	private ArrayList<VarEntity> vars() {
		if (vars==null)
			vars = new ArrayList<>();
		return this.vars;
	}
	
	private Collection<GenericName> mixins() {
		if (mixins==null)
			mixins = new ArrayList<>();
		return this.mixins;
	}

	private ArrayList<FunctionEntity> functions() {
		if (functions==null)
			functions = new ArrayList<>();
		return this.functions;
	}
	
	public ContainerEntity() {
	}

	public ContainerEntity(GenericName rawName, Entity parent, Integer id) {
		super(rawName, parent, id);
	}

	public void addVar(VarEntity var) {
		if (logger.isDebugEnabled()) {
			logger.debug("var found: " + var.getRawName() + ":" + var.getRawType());
		}
		this.vars().add(var);
	}

	public ArrayList<VarEntity> getVars() {
		if (vars==null)
			return new ArrayList<>();
		return this.vars();
	}

	public void addFunction(FunctionEntity functionEntity) {
		this.functions().add(functionEntity);
	}

	public ArrayList<FunctionEntity> getFunctions() {
		if (functions==null)
			return new ArrayList<>();
		return this.functions;
	}

	public HashMap<Object, Expression> expressions() {
		if (expressionWeakReference==null)
			expressionWeakReference= new WeakReference<HashMap<Object, Expression>>(new HashMap<>());
		HashMap<Object, Expression> r = expressionWeakReference.get();
		if (r==null) return new HashMap<>();
		return r;
	}

	public void addExpression(Object key, Expression expression) {
		expressions().put(key, expression);
		expressionList().add(expression);
		expressionCount = expressionList.size();
	}

	public boolean containsExpression(Object key) {
		return 	expressions().containsKey(key);
	}
	/**
	 * For all data in the class, infer their types. Should be override in
	 * sub-classes
	 */
	public void inferLocalLevelEntities(Inferer inferer) {
		super.inferLocalLevelEntities(inferer);
		for (VarEntity var : this.vars()) {
			if (var.getParent()!=this) {
				var.inferLocalLevelEntities(inferer);
			}
		}
		for (FunctionEntity func : this.getFunctions()) {
			if (func.getParent()!=this) {
				func.inferLocalLevelEntities(inferer);
			}
		}
		if (inferer.isEagerExpressionResolve()) {
			reloadExpression(inferer.getRepo());
			resolveExpressions(inferer);
			cacheExpressions();
		}
		resolvedMixins = identiferToContainerEntity(inferer, getMixins());
	}

	private Collection<GenericName> getMixins() {
		if (mixins==null)
			return new ArrayList<>();
		return mixins;
	}

	private Collection<ContainerEntity> identiferToContainerEntity(Inferer inferer, Collection<GenericName> identifiers) {
		if (identifiers.size()==0) return null;
		ArrayList<ContainerEntity> r = new ArrayList<>();
		for (GenericName identifier : identifiers) {
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
		
		if (this instanceof FunctionEntity) {
			((FunctionEntity)this).linkReturnToLastExpression();
		}
		
		if (expressionList==null) return;
		if(expressionList.size()>10000) return;
		for (Expression expression : expressionList) {
			// 1. if expression's type existed, break;
			if (expression.getType() != null)
				continue;
			if (expression.isDot()) { // wait for previous
				continue;
			}
			if (expression.getRawType() == null && expression.getIdentifier() == null)
				continue;

			// 2. if expression's rawType existed, directly infer type by rawType
			// if expression's rawType does not existed, infer type based on identifiers
			if (expression.getRawType() != null) {
				expression.setType(inferer.inferTypeFromName(this, expression.getRawType()), null, inferer);
				if (expression.getType() != null) {
					continue;
				}
			}
			if (expression.getIdentifier() != null) {
				Entity entity = inferer.resolveName(this, expression.getIdentifier(), true);
				String composedName = expression.getIdentifier().toString();
				Expression theExpr = expression;
				if (entity==null) {
					while(theExpr.getParent()!=null && theExpr.getParent().isDot()) {
						theExpr = theExpr.getParent();
						if (theExpr.getIdentifier()==null) break;
						composedName = composedName + "." + theExpr.getIdentifier().toString();
						entity = inferer.resolveName(this, GenericName.build(composedName), true);
						if (entity!=null)
							break;
					}
				}
				if (entity != null) {
					expression.setType(entity.getType(), entity, inferer);
					continue;
				}
				if (expression.isCall()) {
					List<Entity> funcs = this.lookupFunctionInVisibleScope(expression.getIdentifier());
					if (funcs != null) {
						for (Entity func:funcs) {
							expression.setType(func.getType(), func, inferer);
						}
					}
				} else {

					Entity varEntity = this.lookupVarInVisibleScope(expression.getIdentifier());
					if (varEntity != null) {
						expression.setType(varEntity.getType(), varEntity, inferer);
					}
				}
			}
		}
	}
	
	public void cacheChildExpressions() {
		cacheExpressions();
		for (Entity child:getChildren()) {
			if (child instanceof ContainerEntity) {
				((ContainerEntity)child).cacheChildExpressions();
			}
		}
	}


	public void cacheExpressions() {
		if (expressionWeakReference==null) return;
		if (expressionList==null) return;
		this.expressions().clear();
		this.expressionWeakReference.clear();
		cacheExpressionListToFile();
		this.expressionList.clear();
		this.expressionList=null;
		this.expressionList = new ArrayList<>();
	}

	public void clearExpressions() {
		if (expressionWeakReference==null) return;
		if (expressionList==null) return;
		this.expressions().clear();
		this.expressionWeakReference.clear();
		this.expressionList.clear();
		this.expressionList=null;
		this.expressionList = new ArrayList<>();
	}
	
	private void cacheExpressionListToFile() {
		if (expressionCount ==0) return;
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
		if (expressionCount ==0) return;
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
	

	public List<Expression> expressionList() {
		if (expressionList==null) 
			expressionList = new ArrayList<>();
		return expressionList;
	}

	public boolean containsExpression() {
		return expressions().size() > 0;
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
	public List<Entity> lookupFunctionInVisibleScope(GenericName functionName) {
		List<Entity> functions = new ArrayList<>();
		if (this.getMutliDeclare() != null) {
			for (Entity fromEntity : this.getMutliDeclare().getEntities()) {
				Entity f = lookupFunctionBottomUpTillTopContainer(functionName, fromEntity);
				if (f != null) {
					functions.add(f);
					return functions;
				}
			}
		} else {
			ContainerEntity fromEntity = this;
			Entity f = lookupFunctionBottomUpTillTopContainer(functionName, fromEntity);
			if (f != null) {
				functions.add(f);
				return functions;
			}
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
	private Entity lookupFunctionBottomUpTillTopContainer(GenericName functionName, Entity fromEntity) {
		while (fromEntity != null) {
			if (fromEntity instanceof ContainerEntity) {
				FunctionEntity func = ((ContainerEntity) fromEntity).lookupFunctionLocally(functionName);
				if (func != null)
					return func;
			}
			for (Entity child:this.getChildren()) {
				if (child instanceof AliasEntity) {
					if (child.getRawName().equals(functionName))
						return child;
				}
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
	public FunctionEntity lookupFunctionLocally(GenericName functionName) {
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
	public Entity lookupVarInVisibleScope(GenericName varName) {
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
	private Entity lookupVarBottomUpTillTopContainer(GenericName varName, ContainerEntity fromEntity) {
		while (fromEntity != null) {
			if (fromEntity instanceof ContainerEntity) {
				VarEntity var = ((ContainerEntity) fromEntity).lookupVarLocally(varName);
				if (var != null)
					return var;
			}
			for (Entity child:this.getChildren()) {
				if (child instanceof AliasEntity) {
					if (child.getRawName().equals(varName))
						return child;
				}
			}
			fromEntity = (ContainerEntity) this.getAncestorOfType(ContainerEntity.class);
		}
		return null;
	}

	public VarEntity lookupVarLocally(GenericName varName) {
		for (VarEntity var : getVars()) {
			if (var.getRawName().equals(varName))
				return var;
		}
		return null;
	}
	
	public VarEntity lookupVarLocally(String varName) {
		return this.lookupVarLocally(GenericName.build(varName));
	}

	public void addMixin(GenericName moduleName) {
		mixins().add(moduleName);
	}

	public Collection<ContainerEntity> getResolvedMixins() {
		if (resolvedMixins==null) return new ArrayList<>();
		return resolvedMixins;
	}


}
