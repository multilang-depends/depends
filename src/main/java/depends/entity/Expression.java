/*
MIT License

Copyright (c) 2018-2019 Gang ZHANG

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package depends.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import depends.entity.repo.EntityRepo;
import depends.relations.Inferer;

/**
 * Expression 
 */
public class Expression implements Serializable{
	private static final long serialVersionUID = 1L;

	public Integer id;
	private String text;                // for debug purpose
	private GenericName rawType;        // the raw type name
	private GenericName identifier;     // the varName, or method name, etc.
	private boolean isSet = false;       // is a set relation from right to leftHand
	private boolean isDot = false;       // is a dot expression, will decuce variable tfype left to right
	private boolean isCall = false;
	private boolean isLogic = false;
	private boolean isCreate = false;
	private boolean isCast = false;
	private boolean isThrow = false;
	private boolean isStatement = false; //statement is only used for return type calcuation in some langs such as ruby
    									//they will not be treat as real expressions in case of relation calculation
	private boolean deriveTypeFromChild = true;
	private Integer deduceTypeBasedId; //by default, parent expression type determined by most left child

	private Integer parentId = -1; 
	private transient Expression parent;
	
	private transient List<VarEntity> deducedTypeVars = new ArrayList<>();
	private List<Integer> deducedTypeVarsId = new ArrayList<>();

	private transient List<FunctionEntity> deducedTypeFunctions= new ArrayList<>();
	private List<Integer> deducedTypeFunctionsId = new ArrayList<>();
	
	private Integer referredEntityId;
	private transient Entity referredEntity;
	
	private transient TypeEntity type; // the type we care - for relation calculation. 
    //for leaf, it equals to referredEntity.getType. otherwise, depends on child's type strategy
	
	/*
	 * */
	
	public Expression() {
		deducedTypeVars = new ArrayList<>();
		deducedTypeFunctions = new ArrayList<>();
	}
	
	public Expression(Integer id) {
		this.id = id;
		deducedTypeVars = new ArrayList<>();
		deducedTypeFunctions = new ArrayList<>();
	}

	public void reload(EntityRepo repo, ArrayList<Expression> expressionList) {
		this.deducedTypeFunctions = new ArrayList<>();
		this.deducedTypeVars = new ArrayList<>();
		
		//recover parent relation
		if (parentId!=-1) {
			for (Expression expr:expressionList) {
				if (expr.id==parentId) {
					parent = expr;
					break;
				}
			}
		}
		
		//recover deducedTypeFunctionsId
		if (deducedTypeFunctionsId!=null) {
			for (Integer funcId:this.deducedTypeFunctionsId) {
				this.deducedTypeFunctions.add((FunctionEntity) repo.getEntity(funcId));
			}
		}
		
		//recover deducedTypeVars
		if (deducedTypeVarsId!=null) {
			for (Integer varId:this.deducedTypeVarsId) {
				this.deducedTypeVars.add((VarEntity) repo.getEntity(varId));
			}
		}
		
		//referer referredEntity -- TODO:maybe not require
		if (this.referredEntityId!=null && this.referredEntity==null)
			this.referredEntity = repo.getEntity(this.referredEntityId);
	}
	
	/**
	 * Set type of the expression
	 * @param type
	 * @param referredEntity
	 * @param inferer
	 */
	public void setType(TypeEntity type, Entity referredEntity, Inferer inferer) {
		if (this.getReferredEntity()==null && referredEntity!=null) {
			this.setReferredEntity(referredEntity);
		}

		boolean changedType = false;
		if (this.type==null && type!=null) {
			this.type = type;
			for (VarEntity var:deducedTypeVars) {
				if (var!=null) {
					var.setType(this.type);
				}
			}
			for (FunctionEntity func:deducedTypeFunctions) {
				if (func!=null) {
					func.addReturnType(this.type);
				}
			}
			changedType = true;
		}
		if (this.referredEntity==null)
			this.setReferredEntity(this.type);

		if (changedType)
			deduceTheParentType(inferer);
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
		}
		
		/* if it is a logic expression, the return type/type is boolean. */
		if (parent.isLogic) {
			parent.setType(Inferer.buildInType,null,inferer);
		}
		/* if it is a.b, and we already get a's type, b's type could be identified easily  */
		else if (parent.isDot) {
			if (parent.isCall()) {
				List<Entity> funcs = this.getType().lookupFunctionInVisibleScope(parent.identifier);
				if (funcs!=null) {
					Entity func = funcs.get(0);
					if (funcs.size()>1) {
						MultiDeclareEntities m = new MultiDeclareEntities(func, inferer.getRepo().generateId());
						inferer.getRepo().add(m);
						for (int i=1;i<funcs.size();i++) {
							m.add(funcs.get(i));
						}
						parent.setType(func.getType(), m,inferer);
						parent.setReferredEntity(m);
					}else {
						parent.setType(func.getType(), func,inferer);
						parent.setReferredEntity(func);
					}
				}
			}else {
				Entity var = this.getType().lookupVarInVisibleScope(parent.identifier);
				if (var!=null) {
					parent.setType(var.getType(),var, inferer);
					parent.setReferredEntity(var);
				}else {
					List<Entity> funcs = this.getType().lookupFunctionInVisibleScope(parent.identifier);
					if (funcs!=null) {
						Entity func = funcs.get(0);
						if (funcs.size()>1) {
							MultiDeclareEntities m = new MultiDeclareEntities(func, -1);
							for (int i=1;i<funcs.size();i++) {
								m.add(funcs.get(i));
							}
							parent.setType(func.getType(), m,inferer);
							parent.setReferredEntity(m);
						}else {
							parent.setType(func.getType(), func,inferer);
							parent.setReferredEntity(func);
						}
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
		if (parent.getReferredEntity()==null)
			parent.setReferredEntity(parent.type);
	}

	private void setReferredEntity(Entity referredEntity) {
		this.referredEntity = referredEntity;
		if (this.referredEntity!=null)
			this.referredEntityId = referredEntity.getId();
	}

	public void addDeducedTypeVar(VarEntity var) {
		this.deducedTypeVars.add(var);
		this.deducedTypeVarsId.add(var.getId());
	}

	public void addDeducedTypeFunction(FunctionEntity function) {
		this.deducedTypeFunctions.add(function);
		this.deducedTypeFunctionsId.add(function.id);
	}

	public void setParent(Expression parent) {
		this.parent = parent;
		if (parent!=null)
			this.parentId = parent.id;
		if (parent!=null) {
			if (parent.deduceTypeBasedId==null) 
				parent.deduceTypeBasedId = id;
			if (parent.isSet) {
				parent.deduceTypeBasedId = id;
			}
		}
	}


	public GenericName getIdentifier() {
		return this.identifier;
	}

	public GenericName getRawType() {
		return this.rawType;
	}

	public void setIdentifier(String name) {
		if (!validName(name)){
			return;
		}
		this.identifier = GenericName.build(name);
	}

	public void setIdentifier(GenericName name) {
		if (name==null) return;
		if (!validName(name.getName())){
			return;
		}
		this.identifier = name;
	}

	public void setRawType(GenericName name) {
		if (name==null) return;
		if (!validName(name.getName())){
			return;
		}
		this.rawType = name;
		
	}

	public void setRawType(String name) {
		if (name==null) return;
		if (!validName(name)){
			return;
		}
		this.rawType = GenericName.build(name);
	}

	public Expression getParent() {
		return this.parent;
	}

	public void setText(String text) {
		this.text = text;
	}
	

	public boolean isCall() {
		return isCall;
	}

	public boolean isSet() {
		return isSet;
	}

	public void setSet(boolean isSet) {
		this.isSet = isSet;
	}

	public boolean isDot() {
		return isDot;
	}

	public void setDot(boolean isDot) {
		this.isDot = isDot;
	}

	public boolean isLogic() {
		return isLogic;
	}

	public void setLogic(boolean isLogic) {
		this.isLogic = isLogic;
	}

	public boolean isCreate() {
		return isCreate;
	}

	public void setCreate(boolean isCreate) {
		this.isCreate = isCreate;
	}

	public boolean isCast() {
		return isCast;
	}

	public void setCast(boolean isCast) {
		this.isCast = isCast;
	}

	public boolean isThrow() {
		return isThrow;
	}

	public void setThrow(boolean isThrow) {
		this.isThrow = isThrow;
	}

	public boolean isStatement() {
		return isStatement;
	}

	public void setStatement(boolean isStatement) {
		this.isStatement = isStatement;
	}

	public void setCall(boolean isCall) {
		this.isCall = isCall;
	}

	public void disableDriveTypeFromChild() {
		deriveTypeFromChild = false	;	
	}
	
	public Entity getReferredEntity() {
		return referredEntity;
	}

	public TypeEntity getType() {
		return type;
	}


	private boolean validName(String name) {
		if (name==null) return false;
		if (name.toLowerCase().equals("<literal>")) return true;
		if (name.toLowerCase().equals("<built-in>")) return true;
		return true;
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
		                      .append(isCall?"[call]":"")
		                      .append(isCreate?"[new]":"")
		                      .append(isThrow?"[throw]":"").append("|")
		    .append("parent:").append(parent==null?"nil":parent.text).append("|")
			.append("type:").append(type).append("|");
		return s.toString();
	}
}