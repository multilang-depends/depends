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
public abstract class ContainerEntity extends Entity {
	private static final Logger logger = LoggerFactory.getLogger(ContainerEntity.class);

	private ArrayList<VarEntity> vars;
	private ArrayList<FunctionEntity> functions;
	private HashMap<Object, Expression> expressions;
	private Collection<String> typeParameters; // Generic type parameters like <T>, <String>, <? extends Object>
	private Collection<String> annotations = new ArrayList<>();
	private Collection<TypeEntity> resolvedTypeParameters = new ArrayList<>();
	private Collection<TypeEntity> resolvedAnnotations = new ArrayList<>();

	public ContainerEntity(String rawName, Entity parent, Integer id) {
		super(rawName, parent, id);
		vars = new ArrayList<>();
		functions = new ArrayList<>();
		expressions = new HashMap<>();
		typeParameters = new ArrayList<>();
	}
	
	public void addAnnotation(String name) {
		this.annotations.add(name);
	}
	
	public void addTypeParameter(String typeName) {
		this.typeParameters.add(typeName);
	}
	
	public void addTypeParameter(List<String> parameters) {
		this.typeParameters.addAll(parameters);
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
	 * A common utility function used to transfer the identifiers 
	 * to types.
	 * @param inferer - the inferer object 
	 * @param identifiers - the identifiers will be translated
	 * @return The translated Types
	 */
	protected Collection<TypeEntity> identiferToTypes(Inferer inferer, Collection<String> identifiers) {
		ArrayList<TypeEntity> r = new ArrayList<>();
		for (String typeParameter : identifiers) {
			TypeEntity typeEntity = inferer.inferTypeFromName(this, typeParameter);
			if (typeEntity==null) {
				if (((ContainerEntity)getParent()).isGenericTypeParameter(typeParameter)) {
					typeEntity = Inferer.genericParameterType;
				}
			}
			if (typeEntity != null)
				r.add(typeEntity);
		}
		return r;
	}

	/**
	 * For all data in the class, infer their types.
	 * Should be override in sub-classes 
	 */
	public void inferLocalLevelEntities(Inferer inferer) {
		resolvedTypeParameters = identiferToTypes(inferer, typeParameters);
		resolvedAnnotations = identiferToTypes(inferer, annotations);
		for (VarEntity var : this.vars) {
			var.inferLocalLevelEntities(inferer);
		}
		for (FunctionEntity func:this.functions) {
			func.inferLocalLevelEntities(inferer);
		}
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


	

	public Collection<TypeEntity> getResolvedTypeParameters() {
		return resolvedTypeParameters;
	}


	public Collection<TypeEntity> getResolvedAnnotations() {
		return resolvedAnnotations;
	}


	public String dumpExpressions() {
		StringBuilder sb = new StringBuilder();
		for (Expression exp:expressions.values()) {
			sb.append(exp.toString()).append("\n");
		}
		return sb.toString();
	}
	


	public boolean isGenericTypeParameter(String rawType) {
		if (this.typeParameters.contains(rawType)) return true;
		if (this.getParent()==null || !(this.getParent() instanceof ContainerEntity))
			return false;
		return ((ContainerEntity)getParent()).isGenericTypeParameter(rawType);
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
}
