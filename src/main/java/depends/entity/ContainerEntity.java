package depends.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import depends.entity.types.FunctionEntity;
import depends.entity.types.TypeEntity;
import depends.entity.types.VarEntity;
import depends.util.Tuple;

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
			TypeEntity typeEntity = typeInferer.inferType(this, typeParameter);
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
			if (expression.type != null)
				continue;
			
			//2. if expression's rawType existed, directly infer type by rawType
			//   if expression's rawType does not existed, infer type based on identifiers
			if (expression.rawType != null) {
				expression.type = typeInferer.inferType(this, expression.rawType);
			}else if(expression.identifier != null) {
				if (expression.identifier.contains(".")) {
					/* if it is a qualified name */
					Tuple<TypeEntity, String> result = typeInferer.locateTypeOfQualifiedName(this, expression.identifier);
					if (result != null) {
						if (result.y == null) {
							expression.type = result.x;
						} else {
							expression.type = typeInferer.inferType(result.x, result.y);
						}
					}
				} else {
					/* expression identifiers could be a variable, or a type. try them */
					expression.type = lookupVarDefinition(expression.identifier);
					if (expression.type==null)
						expression.type = typeInferer.inferType(this, expression.identifier);
				}
			}
			
			//3. if new found type of expression, update parent expressions
			if (expression.type != null) {
				expression.deduceParentType(this.expressions,typeInferer);
			}
		}
	}


	/**
	 * Found the given type of given var;
	 * Must be invoked after all vars' type have been resolved
	 * Should be override if new vars have been added, for example 
	 * Function Type could resolve parameters as vars
	 * @param identifier  - var identifiers
	 * @return
	 */
	public TypeEntity lookupVarDefinition(String identifier) {
		for (VarEntity var : this.vars) {
			if (var.getRawName().equals(identifier))
				return var.getType();
		}
		if (this.parent != null && this.parent instanceof ContainerEntity)
			return ((ContainerEntity) this.parent).lookupVarDefinition(identifier);
		return null;
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
}
