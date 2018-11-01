package depends.entity.types;

import java.util.ArrayList;
import java.util.Collection;

import depends.entity.ContainerEntity;
import depends.entity.Entity;
import depends.entity.TypeInfer;

public class TypeEntity extends ContainerEntity{
	Collection<TypeEntity> inheritedTypes = new ArrayList<>();
	Collection<TypeEntity> implementedTypes = new ArrayList<>();
	Collection<String> inhertedTypeIdentifiers;
	Collection<String> implementedIdentifiers;
	TypeEntity inheritedType;

	public TypeEntity(String simpleName, Entity parent, Integer id) {
		super(simpleName,parent,id);
		inhertedTypeIdentifiers = new ArrayList<>();
		implementedIdentifiers = new ArrayList<>();
	}
	@Override
	public void inferLocalLevelTypes(TypeInfer typeInferer) {
		inheritedTypes= identiferToTypes(typeInferer,this.inhertedTypeIdentifiers);
		implementedTypes= identiferToTypes(typeInferer,this.implementedIdentifiers);
		if (inheritedTypes.size()>0)
			inheritedType = inheritedTypes.iterator().next();
		super.inferLocalLevelTypes(typeInferer);
	}
	public void addImplements(String typeName) {
		if (typeName.equals(this.getRawName())) return;
		if (implementedIdentifiers.contains(typeName)) return;
		this.implementedIdentifiers.add(typeName);
	}
	public void addExtends(String typeName) {
		if (typeName.equals(this.getRawName())) return;
		if (inhertedTypeIdentifiers.contains(typeName)) return;
		this.inhertedTypeIdentifiers.add(typeName);
	}
	public Collection<TypeEntity> getInheritedTypes() {
		return inheritedTypes;
	}
	
	public Collection<TypeEntity> getImplementedTypes() {
		return implementedTypes;
	}
	
 	public TypeEntity getInheritedType() {
		return inheritedType;
	}
}
