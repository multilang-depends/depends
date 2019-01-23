package depends.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import depends.extractor.java.JavaParser.ExpressionContext;
import depends.relations.Inferer;

/**
 * ContainerEntity for example file, class, method, etc.
 * they could contain vars, functions, ecpressions, type parameters, etc.
 */
public abstract class ContainerEntity extends DecoratedEntity {
	private static final Logger logger = LoggerFactory.getLogger(ContainerEntity.class);

	private ArrayList<VarEntity> vars;
	private ArrayList<FunctionEntity> functions;
	private HashMap<Object, Expression> expressions;
	private Collection<String> mixins;
	private Collection<ContainerEntity> resolvedMixins;

	public ContainerEntity(String rawName, Entity parent, Integer id) {
		super(rawName, parent, id);
		vars = new ArrayList<>();
		functions = new ArrayList<>();
		mixins = new ArrayList<>();
		resolvedMixins = new ArrayList<>();
		expressions = new HashMap<>();
	}
	


	public void addVar(VarEntity var) {
		if (logger.isDebugEnabled()) {
			logger.debug("var found: "+var.getRawName() +  ":" + var.getRawType());
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
	}

	

	/**
	 * For all data in the class, infer their types.
	 * Should be override in sub-classes 
	 */
	public void inferLocalLevelEntities(Inferer inferer) {
		super.inferLocalLevelEntities(inferer);
		for (VarEntity var : this.vars) {
			var.inferLocalLevelEntities(inferer);
		}
		for (FunctionEntity func:this.functions) {
			func.inferLocalLevelEntities(inferer);
		}
		resolvedMixins = identiferToContainerEntity(inferer, mixins);
	}

	private Collection<ContainerEntity> identiferToContainerEntity(Inferer inferer, Collection<String> identifiers) {
		ArrayList<ContainerEntity> r = new ArrayList<>();
		for (String identifier : identifiers) {
			Entity entity = inferer.resolveName(this, identifier, true);
			if (entity==null) {
				continue;
			}
			if (entity instanceof ContainerEntity)
				r.add((ContainerEntity)entity);
		}
		return r;
	}

	/**
	 * Resolve all expression's type
	 * @param inferer
	 */
	public void resolveExpressions(Inferer inferer) {
		for (Expression expression : expressions.values()) {
			//1. if expression's type existed, break;
			if (expression.getType() != null)
				continue;
			
			//2. if expression's rawType existed, directly infer type by rawType
			//   if expression's rawType does not existed, infer type based on identifiers
			if (expression.rawType != null) {
				expression.setType(inferer.inferTypeFromName(this, expression.rawType),null,inferer);
			}else if (expression.isDot){ //wait for previous
				continue;
			} else if (expression.rawType!=null) {
				expression.setType(inferer.inferTypeFromName(this, expression.rawType),null,inferer);
				if (expression.getType() !=null) {
					 continue;
				}
			}
			if (expression.identifier!=null) { 
				Entity entity = inferer.resolveName(this, expression.identifier, true);
				if (entity!=null) {
					expression.setType(entity.getType(),entity,inferer);
					continue;
				}
				if (expression.isCall) {
					FunctionEntity func = this.lookupFunctionInVisibleScope(expression.identifier);
					if (func!=null) {
						expression.setType(func.getType(),func,inferer);
					}
				}else {
					
					VarEntity varEntity = this.lookupVarsInVisibleScope(expression.identifier);
					if (varEntity!=null) {
						expression.setType( varEntity.getType(),varEntity,inferer);
					}
				}
			}
		}
	}


	



	public String dumpExpressions() {
		StringBuilder sb = new StringBuilder();
		for (Expression exp:expressions.values()) {
			sb.append(exp.toString()).append("\n");
		}
		return sb.toString();
	}
	


	protected FunctionEntity lookupFunctionLocally(String functionName) {
		for (FunctionEntity func : getFunctions()) {
			if (func.getRawName().equals(functionName))
				return func;
		}
		return null;
	}
	
	public FunctionEntity lookupFunctionInVisibleScope(String functionName) {
		ContainerEntity fromEntity = this;
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
	 * To found the var. Must be invoked after all entities var binding solved
	 * @param fromEntity
	 * @param varName
	 * @return
	 */
	public VarEntity lookupVarsInVisibleScope(String varName) {
		
		ContainerEntity fromEntity = this;
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

	private VarEntity lookupVarLocally(String varName) {
		for (VarEntity var:getVars()) {
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
