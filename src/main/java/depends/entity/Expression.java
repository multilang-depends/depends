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

	public void refreshParent(HashMap<Integer, Expression> expressionRepo, TypeInfer bindingResolver) {
		if (this.type==null) return;
		if (this.returnType==null) this.returnType = this.type; //we use return type as recurisely calcuation
		if (this.parentId==null) return;
		if (expressionRepo==null) return;
		Expression parent = expressionRepo.get(this.parentId);
		if (parent==null) return;
		if (parent.type != null)return;
		if (parent.firstChildId!=this.id) return;
		if (parent.type!=null) return;
		if (parent.isLogic) {
			parent.returnType = bindingResolver.inferType(this.returnType, "<Built-in>");
			parent.type = bindingResolver.inferType(this.returnType, "<Built-in>");
		}
		else if (parent.isDot && (!parent.isCall)) {
			VarEntity returnType = bindingResolver.resolveVarBindings(this.returnType, parent.identifier);
			if (returnType != null)
				parent.type = returnType.getType();
		} else if (parent.isDot && parent.isCall) {
			FunctionEntity returnType = bindingResolver.resolveFunctionBindings(this.returnType, parent.identifier);
			if (returnType != null){
				parent.returnType =  returnType.getReturnType();
			}
			parent.type = this.type; //in call relation, we count call in object, instead of function
		} else
			parent.type = type;
		
		parent.refreshParent(expressionRepo, bindingResolver);
	}
}