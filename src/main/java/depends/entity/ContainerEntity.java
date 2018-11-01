package depends.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import depends.entity.types.FunctionEntity;
import depends.entity.types.TypeEntity;
import depends.entity.types.VarEntity;

public abstract class ContainerEntity extends Entity {
	private ArrayList<VarEntity> vars;
	private ArrayList<FunctionEntity> functions;
	private HashMap<Integer, Expression> expressions;
	private Collection<String> typeParameters; // Generic type parameters like <T>, <String>, <? extends Object>
	private Collection<String> annotations = new ArrayList<>();
	private Collection<TypeEntity> resolvedTypeParameters = new ArrayList<>();
	private Collection<TypeEntity> resolvedAnnotations = new ArrayList<>();

	public void addAnnotation(String name) {
		this.annotations.add(name);
	}

	public ContainerEntity(String rawName, Entity parent, Integer id) {
		super(rawName, parent, id);
		vars = new ArrayList<>();
		functions = new ArrayList<>();
		expressions = new HashMap<>();
		typeParameters = new ArrayList<>();
	}

	public void addTypeParameter(String typeName) {
		this.typeParameters.add(typeName);
	}

	public void addVar(VarEntity var) {
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

	public HashMap<Integer, Expression> expressions() {
		return expressions;
	}

	public void addExpression(Expression expression) {
		expressions.put(expression.id, expression);
	}

	/**
	 * A common utility function used to transfer the identifiers 
	 * to types.
	 * @param typeInferer - the inferer object 
	 * @param identifiers - the identifiers will be translated
	 * @return The translated Types
	 */
	protected Collection<TypeEntity> identiferToTypes(TypeInfer typeInferer, Collection<String> identifiers) {
		ArrayList<TypeEntity> r = new ArrayList<>();
		for (String typeParameter : identifiers) {
			TypeEntity typeEntity = typeInferer.inferType(this, typeParameter,true);
			if (typeEntity != null)
				r.add(typeEntity);
		}
		return r;
	}

	/**
	 * For all data in the class, infer their types.
	 * Should be override in sub-classes 
	 */
	public void inferLocalLevelTypes(TypeInfer typeInferer) {
		setResolvedTypeParameters(identiferToTypes(typeInferer, typeParameters));
		resolvedAnnotations = identiferToTypes(typeInferer, annotations);
		for (VarEntity var : this.vars) {
			var.inferLocalLevelTypes(typeInferer);
		}
		for (FunctionEntity func:this.functions) {
			func.inferLocalLevelTypes(typeInferer);
		}
		resolveExpressions(typeInferer);
	}

	/**
	 * Resolve all expression's type
	 * @param typeInferer
	 */
	private void resolveExpressions(TypeInfer typeInferer) {
		for (Expression expression : expressions.values()) {
			//1. if expression's type existed, break;
			if (expression.getType() != null)
				continue;
			
			//2. if expression's rawType existed, directly infer type by rawType
			//   if expression's rawType does not existed, infer type based on identifiers
			if (expression.rawType != null) {
				expression.setType(typeInferer.inferType(this, expression.rawType,true),typeInferer);
			}else if (expression.isDot){ //wait for previous
				continue;
			} else if (expression.rawType!=null) {
				expression.setType(typeInferer.inferType(this, expression.rawType, true),typeInferer);
				if (expression.getType() !=null) {
					 continue;
				}
			}
			if (expression.identifier!=null) { 
				TypeEntity type = typeInferer.inferType(this, expression.identifier, false);
				if (type!=null) {
					expression.setType(type,typeInferer);
					continue;
				}
				if (expression.isCall) {
					FunctionEntity func = typeInferer.resolveFunctionBindings(this, expression.identifier);
					if (func!=null) {
						expression.setType(func.getReturnType(),typeInferer);
					}
				}else {
					VarEntity varEntity = this.resolveVarBindings(expression.identifier);
					if (varEntity!=null) {
						expression.setType( varEntity.getType(),typeInferer);
					}
				}
			}
		}
	}


	

	public Collection<TypeEntity> getResolvedTypeParameters() {
		return resolvedTypeParameters;
	}

	public void setResolvedTypeParameters(Collection<TypeEntity> resolvedTypeParameters) {
		this.resolvedTypeParameters = resolvedTypeParameters;
	}

	public Collection<TypeEntity> getResolvedAnnotations() {
		return resolvedAnnotations;
	}

	public void setResolvedAnnotations(Collection<TypeEntity> resolvedAnnotations) {
		this.resolvedAnnotations = resolvedAnnotations;
	}

	public String dumpExpressions() {
		StringBuilder sb = new StringBuilder();
		for (Expression exp:expressions.values()) {
			sb.append(exp.toString()).append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * To found the var. Must be invoked after all entities var binding solved
	 * @param fromEntity
	 * @param varName
	 * @return
	 */
	public VarEntity resolveVarBindings(String varName) {
		for (VarEntity var:getVars()) {
			if (var.getRawName().equals(varName))
				return var;
		}
		if (parent !=null && parent instanceof ContainerEntity)
			return ((ContainerEntity)parent).resolveVarBindings(varName);
		return null;
	}
}
