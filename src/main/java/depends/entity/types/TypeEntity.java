package depends.entity.types;

import java.util.ArrayList;
import java.util.Collection;

import depends.entity.ContainerEntity;
import depends.entity.Entity;
import depends.entity.TypeInfer;

public class TypeEntity extends ContainerEntity{
	Collection<TypeEntity> inheritedTypes;
	Collection<TypeEntity> implementedTypes;
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
		this.implementedIdentifiers.add(typeName);
	}
	public void addExtends(String typeName) {
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
