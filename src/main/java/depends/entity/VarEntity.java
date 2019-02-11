package depends.entity;

import java.util.ArrayList;
import java.util.List;

import depends.relations.Inferer;

public class VarEntity extends ContainerEntity {
	private String rawType;
	private TypeEntity type;
	private List<FunctionCall> functionCalls;
	
	public VarEntity(String simpleName,  String rawType, Entity parent, int id) {
		super(simpleName,  parent,id);
		this.rawType = rawType;
		functionCalls = new ArrayList<>();
	}

	public String getRawType() {
		return rawType;
	}

	@Override
	public TypeEntity getType() {
		return type;
	}

	public void setType(TypeEntity type) {
		this.type = type;
	}

	@Override
	public void inferLocalLevelEntities(Inferer inferer) {
		super.inferLocalLevelEntities(inferer);
		Entity entity = inferer.resolveName(this, rawType, true);
		if (entity==null) return;
		type = entity.getType();
		if (type==null) {
			if (((ContainerEntity)getParent()).isGenericTypeParameter(rawType)) {
				type = Inferer.genericParameterType;
			}
		}
	}

	public List<FunctionCall> getCalledFunctions() {
		return functionCalls;
	}

	public void addFunctionCall(String fname) {
		this.functionCalls.add(new FunctionCall(fname));
	}
}
