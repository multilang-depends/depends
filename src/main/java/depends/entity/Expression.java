package depends.entity;

import java.util.ArrayList;
import java.util.List;

import depends.relations.Inferer;

public class Expression {
	public Integer id;
	public Integer deduceTypeBasedId; //by default, parent expression type determined by most left child
	public Expression parent;
	public String text; // for debug purpose
	public String rawType; //the raw type name
	public String identifier; // the varName, or method name, etc.
	public boolean isSet = false; // is a set relation from right to leftHand
	public boolean isDot = false; // is a dot expression, will decuce variable tfype left to right
	public boolean isCall = false;
	public boolean isLogic = false;
	public boolean isCreate = false;
	public boolean isCast = false;
	public boolean isThrow = false;
	public boolean isStatement = false; //statement is only used for return type calcuation in some langs such as ruby
	                                    //they will not be treat as real expressions in case of relation calculation
	public boolean deriveTypeFromChild = true;
	private TypeEntity type; // the type we care - for relation calculation. 
	                         //for leaf, it equals to referredEntity.getType. otherwise, depends on child's type strategy
	private Entity referredEntity;
	private List<VarEntity> deducedTypeVars;
	private List<FunctionEntity> deducedTypeFunctions;
	public TypeEntity getType() {
		return type;
	}

	public void setType(TypeEntity type, Entity referredEntity, Inferer inferer) {
		boolean changedType = false;
		if (this.type==null && type!=null) {
			this.type = type;
			for (VarEntity var:deducedTypeVars) {
				var.setType(this.type);
			}
			for (FunctionEntity func:deducedTypeFunctions) {
				func.addReturnType(this.type);
			}
			changedType = true;
		}
		if (this.referredEntity==null && referredEntity!=null) {
			this.referredEntity  = referredEntity;
		}
		if (this.referredEntity==null)
			this.referredEntity = this.type;
		if (changedType)
			deduceTheParentType(inferer);
	}
	
	public Expression(Integer id) {
		this.id = id;
		deducedTypeVars = new ArrayList<>();
		deducedTypeFunctions = new ArrayList<>();
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
		                      .append(isCreate?"[new]":"").append("|")
		                      .append(isThrow?"[throw]":"").append("|")
		    .append("parent:").append(parent==null?"nil":parent.text).append("|")
			.append("type:").append(type).append("|");
		return s.toString();
	}

	/**
	 * deduce type of parent based on child's type
	 * @param expressionList
	 * @param inferer
	 */
	private void deduceTheParentType(Inferer inferer) {
		if (this.type==null) return;
		if (this.parent==null) return;
		Expression parent = this.parent;
		if (parent.type != null)return;
		if (!parent.deriveTypeFromChild) return;
		//parent's type depends on first child's type
		if (parent.deduceTypeBasedId!=this.id) return;
		
		//if child is a built-in/external type, then parent must also a built-in/external type
		if (this.type.equals(Inferer.buildInType)) {
			parent.setType(Inferer.buildInType,Inferer.buildInType,inferer);
			return;
		}else if (this.type.equals(Inferer.externalType)){
			parent.setType(Inferer.externalType,Inferer.externalType,inferer);
			return;
		}
		
		/* if it is a logic expression, the return type/type is boolean. */
		if (parent.isLogic) {
			parent.setType(Inferer.buildInType,null,inferer);
		}
		/* if it is a.b, and we already get a's type, b's type could be identified easily  */
		else if (parent.isDot) {
			if (parent.isCall) {
				FunctionEntity func = this.getType().lookupFunctionInVisibleScope(parent.identifier);
				if (func!=null) {
					parent.setType(func.getType(), func,inferer);
					parent.setReferredEntity(func);
				}
			}else {
				VarEntity var = this.getType().lookupVarInVisibleScope(parent.identifier);
				if (var!=null) {
					parent.setType(var.getType(),var, inferer);
					parent.setReferredEntity(var);
				}else {
					FunctionEntity func = this.getType().lookupFunctionInVisibleScope(parent.identifier);
					if (func!=null) {
						parent.setType(func.getType(), func,inferer);
						parent.setReferredEntity(func);
					}
				}
			}
			if (parent.getType()==null) {
				parent.setType(inferer.inferTypeFromName(this.getType(), parent.identifier),null,inferer);
			}
		}
		/* if other situation, simple make the parent and child type same */
		else {
			parent.setType(type, null, inferer);
		}
		if (parent.referredEntity==null)
			parent.referredEntity = parent.type;
	}

	private void setReferredEntity(Entity referredEntity) {
		this.referredEntity = referredEntity;
	}

	public Entity getReferredEntity() {
		return referredEntity;
	}


	public void addDeducedTypeVar(VarEntity var) {
		this.deducedTypeVars.add(var);
	}

	public void addDeducedTypeFunction(FunctionEntity function) {
		this.deducedTypeFunctions.add(function);
	}
}