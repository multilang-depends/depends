package depends.entity;

import java.util.ArrayList;
import java.util.HashMap;

import depends.entity.types.FunctionEntity;
import depends.entity.types.VarEntity;
import depends.extractor.HandlerContext;

public abstract class ContainerEntity extends Entity {

    ArrayList<VarEntity> vars;
    ArrayList<FunctionEntity> functions;
	HashMap<Integer, Expression> expressions;

	public ContainerEntity(String fullName, int parentId, Integer id) {
		super(fullName, parentId, id);
		vars = new ArrayList<>();
		functions = new ArrayList<>();
		expressions = new HashMap<>();
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
	public void updateParentReturnType(Integer id, String type, HandlerContext context) {
		if (type==null ) return;
		Expression thisExpression = expressions.get(id);
		if (thisExpression==null) return;
		Expression d = expressions.get(thisExpression.parentId);
		if (d==null) return;
		if (d.returnType!=null) return;
		if (d.isDot && (!d.isCall))
			d.returnType = context.inferVarType(context.resolveTypeNameRef(type), d.identifier);
		else if (d.isDot && d.isCall)
			d.returnType = context.inferFunctionType(context.resolveTypeNameRef(type),d.identifier);
		else
			d.returnType = type;
		updateParentReturnType(d.id,d.returnType,context);
	}

}
