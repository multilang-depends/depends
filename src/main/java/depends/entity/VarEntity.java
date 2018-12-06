package depends.entity;

import depends.relations.Inferer;

public class VarEntity extends Entity {
	private String rawType;
	private TypeEntity type;
	public VarEntity(String simpleName,  String rawType, Entity parent, int id) {
		super(simpleName,  parent,id);
		this.rawType = rawType;
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
		Entity entity = inferer.resolveName(this, rawType, true);
		if (entity==null) return;
		type = entity.getType();
		if (type==null) {
			if (((ContainerEntity)getParent()).isGenericTypeParameter(rawType)) {
				type = Inferer.genericParameterType;
			}
		}
	}
}
