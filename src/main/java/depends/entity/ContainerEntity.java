package depends.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import depends.entity.repo.EntityRepo;
import depends.entity.types.FunctionEntity;
import depends.entity.types.TypeEntity;
import depends.entity.types.VarEntity;

public abstract class ContainerEntity extends Entity {

    ArrayList<VarEntity> vars;
    ArrayList<FunctionEntity> functions;

    HashMap<Integer, Expression> expressions;
    Collection<String> typeParameters;      //Generic type parameters like <T>, <String>, <? extends Object>
	Collection<String> annotations = new ArrayList<>();
    Collection<TypeEntity> resolvedTypeParameters= new ArrayList<>();
    Collection<TypeEntity> resolvedAnnotations= new ArrayList<>();
    
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
	 * Recursively update all types of parent after obtain the child type <br>
	 * In AST, the parent type is determined by child. <br>
	 * e.g : <br>
	 *       (i=1)++
	 *       i is an integer, i=1 is also an integer (determined by i)
	 *       (i=1)++ is an integer (determined by i=1)
	 * @param ctx
	 * @param type
	 */
	public void updateParentReturnType(Integer expressionId, TypeEntity type, EntityRepo bindingResolver) {
		if (type==null ) return;
		Expression thisExpression = expressions.get(expressionId);
		if (thisExpression==null) return;
		Expression parentExpression = expressions.get(thisExpression.parentId);
		if (parentExpression==null) return;
		if (parentExpression.returnType!=null) return;
		if (parentExpression.isDot && (!parentExpression.isCall)){
			VarEntity returnType = bindingResolver.resolveVarBindings(type, parentExpression.identifier);
			if (returnType!=null)
				parentExpression.returnType = returnType.getType();
		}
		else if (parentExpression.isDot && parentExpression.isCall){
			FunctionEntity returnType = bindingResolver.resolveFunctionBindings(type,parentExpression.identifier);
			if (returnType!=null)
				parentExpression.returnType = returnType.getReturnType();			
		}
		else
			parentExpression.returnType = type;
		updateParentReturnType(parentExpression.id,parentExpression.returnType,bindingResolver);
	}

	public Collection<TypeEntity> identiferToTypes(TypeInfer typeInferer,Collection<String> identifiers) {
		ArrayList<TypeEntity> r = new ArrayList<>();

		for (String typeParameter:identifiers) {
			TypeEntity typeEntity = typeInferer.inferType(this, typeParameter);
			if (typeEntity!=null)
				r.add(typeEntity);
		}
		return r;
	}

	public  void inferLocalLevelTypes(TypeInfer typeInferer){
		resolvedTypeParameters= identiferToTypes(typeInferer,typeParameters);
		resolvedAnnotations= identiferToTypes(typeInferer,annotations);
		for (VarEntity var:this.vars) {
			var.inferLocalLevelTypes(typeInferer);
		}
	}

}
