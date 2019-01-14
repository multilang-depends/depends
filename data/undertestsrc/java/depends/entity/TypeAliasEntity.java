package depends.entity;

import java.util.Collection;

import depends.relations.Inferer;

public class TypeAliasEntity extends TypeEntity{
	TypeEntity originType = new EmptyTypeEntity();
	String originTypeName;
	public TypeAliasEntity(String simpleName, Entity parent, Integer id, String originTypeName) {
		super(simpleName, parent, id);
		this.originTypeName = originTypeName;
	}
	@Override
	public void inferLocalLevelEntities(Inferer inferer) {
		Entity entity = inferer.resolveName(this, originTypeName, true);
		TypeEntity type = null;
		if (entity!=null) 
			type = entity.getType();
		if (type!=null)
			originType = type;
		if (type == this) {
			System.err.println("cannot typedef as self");
		}
		originType.inferLocalLevelEntities(inferer);
	}
	@Override
	public Collection<TypeEntity> getInheritedTypes() {
		return originType.getInheritedTypes();
	}
	@Override
	public Collection<TypeEntity> getImplementedTypes() {
		return originType.getImplementedTypes();
	}
	@Override
	public TypeEntity getInheritedType() {
		return originType.getInheritedType();
	}
	public TypeEntity getOriginType() {
		return originType;
	}
	
}
