package depends.entity.types;

import java.util.Collection;

import depends.entity.Entity;
import depends.entity.TypeInfer;
import depends.entity.TypeInfer.InferData;

public class TypeAliasEntity extends TypeEntity{
	TypeEntity originType = new EmptyTypeEntity();
	String originTypeName;
	public TypeAliasEntity(String simpleName, Entity parent, Integer id, String originTypeName) {
		super(simpleName, parent, id);
		this.originTypeName = originTypeName;
	}
	@Override
	public void inferLocalLevelTypes(TypeInfer typeInferer) {
		InferData r = typeInferer.inferType(this, originTypeName);
		TypeEntity type = null;
		if (r!=null) 
			type = r.type;
		if (type!=null)
			originType = type;
		if (type == this) {
			System.err.println("cannot typedef as self");
		}
		originType.inferLocalLevelTypes(typeInferer);
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
