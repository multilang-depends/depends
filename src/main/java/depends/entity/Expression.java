package depends.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import depends.entity.types.FunctionEntity;
import depends.entity.types.TypeEntity;
import depends.entity.types.VarEntity;
import depends.util.Tuple;

public class Expression {
	public Integer id;
	public Integer parentId;
	public Integer firstChildId; //by default, parent expression type determined by most left child
	
	public String text; // for debug purpose
	public TypeEntity returnType; // the type we care - for type deduction like foo().bar.x
	public TypeEntity type; // the type we care - for relation calculation
	public String rawType; //the raw type name
	public String identifier; // the varName, or method name, etc.
	public boolean isSet = false; // is a set relation from right to leftHand
	public boolean isDot = false; // is a dot expression, will decuce variable tfype left to right
	public boolean isCall = false;
	public boolean isLogic = false;
	List<Tuple<String, String>> relations = new ArrayList<>();

	public Expression(Integer id, Integer parentId) {
		this.id = id;
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("[").append(text).append("]").append("\n")
			.append("return:").append(returnType).append("\n")
			.append("rawType  :").append(rawType).append("\n")
			.append("type  :").append(type).append("\n")
			.append("var   :").append(identifier).append("\n")
		    .append("prop  :").append(isDot?"[dot]":"")
		                      .append(isSet?"[set]":"")
		                      .append(isLogic?"[bool]":"")
		                      .append(isCall?"[call]":"").append("\n");

		for (Tuple<String, String> item : relations) {
			s.append(item.y).append("->").append(item.x).append(",");
		}
		return s.toString();
	}

	/**
	 * deduce type of parent based on child's type
	 * @param expressionList
	 * @param typeInfer
	 */
	public void deduceParentType(HashMap<Integer, Expression> expressionList, TypeInfer typeInfer) {
		if (this.type==null) return;
		if (this.returnType==null) this.returnType = this.type; //we use return type as recurisely calcuation
		if (this.parentId==null) return;
		if (expressionList==null) return;
		Expression parent = expressionList.get(this.parentId);
		if (parent==null) return;
		if (parent.type != null)return;
		if (parent.firstChildId!=this.id) return;
		if (parent.type!=null) return;
		
		/* if it is a logic expression, the return type/type is boolean. */
		if (parent.isLogic) {
			parent.returnType = typeInfer.inferType(this.returnType, "<Built-in>");
			parent.type = typeInfer.inferType(this.returnType, "<Built-in>");
		}
		
		/* if it is a.b, and we already get a's type, b's type could be identified easily  */
		else if (parent.isDot && (!parent.isCall)) {
			VarEntity returnType = typeInfer.resolveVarBindings(this.returnType, parent.identifier);
			if (returnType != null)
				parent.type = returnType.getType();
		}
		/* if it is a.foo, and we already get a's type, foo's type could be identified easily  */
		else if (parent.isDot && parent.isCall) {
			FunctionEntity function = typeInfer.resolveFunctionBindings(this.returnType, parent.identifier);
			if (function != null){
				parent.returnType =  function.getReturnType();
			}
			/* type is used for relation calculation, while return type is used for continuous type calculation */
			parent.type = this.type; //in call relation, we count call in object, instead of function
		} 
		/* if other situation, simple make the parent and child type same */
		else {
			parent.type = type;
		}
		
		parent.deduceParentType(expressionList, typeInfer);
	}
}