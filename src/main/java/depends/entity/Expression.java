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
	public Expression parent;
	public String text; // for debug purpose


	private TypeEntity type; // the type we care - for relation calculation
	public String rawType; //the raw type name
	public String identifier; // the varName, or method name, etc.
	public boolean isSet = false; // is a set relation from right to leftHand
	public boolean isDot = false; // is a dot expression, will decuce variable tfype left to right
	public boolean isCall = false;
	public boolean isLogic = false;
	public boolean isCreate = false;
	public boolean isCast = false;
	public boolean deriveTypeFromChild = true;
	List<Tuple<String, String>> relations = new ArrayList<>();
	public TypeEntity getType() {
		return type;
	}

	public void setType(TypeEntity type, TypeInfer typeInferer) {
		if (this.type!=null) return;
		this.type = type;
		deduceParentType(typeInferer);
	}
	
	public Expression(Integer id, Integer parentId) {
		this.id = id;
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("[").append(text).append("]").append("|")
			.append("rawType:").append(rawType).append("|")
			.append("identifier:").append(identifier).append("|")
		    .append("prop:").append(isDot?"[dot]":"")
		                      .append(isSet?"[set]":"")
		                      .append(isLogic?"[bool]":"")
		                      .append(isCall?"[call]":"").append("|")
		    .append("parent:").append(parent==null?"none":parent.text)
			.append("type:").append(type).append("|");
		return s.toString();
	}

	/**
	 * deduce type of parent based on child's type
	 * @param expressionList
	 * @param typeInfer
	 */
	public void deduceParentType(TypeInfer typeInfer) {
		if (this.type==null) return;
		if (this.parent==null) return;
		Expression parent = this.parent;
		if (parent.type != null)return;
		if (parent.firstChildId!=this.id) return;
		if (this.type.equals(TypeInfer.buildInType)) {
			parent.setType(TypeInfer.buildInType,typeInfer);
			return;
		}else if (this.type.equals(TypeInfer.externalType)){
			parent.setType(TypeInfer.externalType,typeInfer);
			return;
		}
		
		/* if it is a logic expression, the return type/type is boolean. */
		if (parent.isLogic) {
			parent.setType(TypeInfer.buildInType,typeInfer);
		}
		/* if it is a.b, and we already get a's type, b's type could be identified easily  */
		else if (parent.isDot) {
			if (parent.isCall) {
				FunctionEntity func = typeInfer.resolveFunctionBindings(this.getType(), parent.identifier);
				if (func!=null)
					parent.setType(func.getReturnType(), typeInfer);
			}else {
				parent.setType(typeInfer.inferType(this.getType(), parent.identifier, true),typeInfer);
				if (parent.type!=null) return;
				VarEntity var = this.getType().resolveVarBindings(parent.identifier);
				if (var!=null)
					parent.setType(var.getType(), typeInfer);
			}
		}
		/* if other situation, simple make the parent and child type same */
		else {
			parent.setType(type, typeInfer);
		}
	}
}