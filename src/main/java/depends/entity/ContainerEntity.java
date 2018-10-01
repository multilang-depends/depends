package depends.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import depends.entity.repo.EntityRepo;
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

	public Collection<TypeEntity> identiferToTypes(TypeInfer typeInferer, Collection<String> identifiers) {
		ArrayList<TypeEntity> r = new ArrayList<>();
		for (String typeParameter : identifiers) {
			TypeEntity typeEntity = inferType(typeInferer, typeParameter);
			if (typeEntity != null)
				r.add(typeEntity);
		}
		return r;
	}

	private TypeEntity inferType(TypeInfer typeInferer, String rawName) {
		return typeInferer.inferType(this, rawName);
	}

	public void inferLocalLevelTypes(TypeInfer typeInferer) {
		setResolvedTypeParameters(identiferToTypes(typeInferer, typeParameters));
		resolvedAnnotations = identiferToTypes(typeInferer, annotations);
		for (VarEntity var : this.vars) {
			var.inferLocalLevelTypes(typeInferer);
		}
		for (FunctionEntity func:this.functions) {
			func.inferLocalLevelTypes(typeInferer);
		}
		resolveExpressions(this,typeInferer);
	}

	private void resolveExpressions(ContainerEntity container, TypeInfer typeInferer) {
		for (Expression expression : expressions.values()) {
			if (expression.type != null)
				continue;
			if (expression.rawType != null) {
				expression.type = typeInferer.inferType(this, expression.rawType);
			}

			if (expression.identifier != null && expression.rawType == null) {
				if (expression.identifier.contains(".")) {
					Tuple<TypeEntity, String> result = locateType(typeInferer, this, expression.identifier);
					if (result != null) {
						if (result.y == null) {
							expression.type = result.x;
						} else {
							expression.type = typeInferer.inferType(result.x, result.y);
						}
					}
				} else {
					expression.type = lookupVarDefinition(expression.identifier);
					if (expression.type==null)
						expression.type = typeInferer.inferType(this, expression.identifier);
				}
			}
			if (expression.type != null) {
				expression.refreshParent(this.expressions,typeInferer);
			}
		}
	}

	private Tuple<TypeEntity, String> locateType(TypeInfer typeInferer, ContainerEntity fromEntity,
			String qualifiedName) {
		String localName = null;
		while (true) {
			TypeEntity type = typeInferer.inferType(fromEntity, qualifiedName);
			if (type != null)
				return new Tuple<TypeEntity, String>(type, localName);
			int lpos = qualifiedName.lastIndexOf(".");
			if (lpos < 0)
				return null;
			localName = localName == null ? qualifiedName.substring(lpos + 1)
					: localName + "." + qualifiedName.substring(lpos + 1);
			qualifiedName = qualifiedName.substring(0, lpos);
			type = typeInferer.inferType(fromEntity, qualifiedName);
			return new Tuple<TypeEntity, String>(type,localName);
		}
	}

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
