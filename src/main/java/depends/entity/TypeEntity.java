package depends.entity;

import java.util.ArrayList;
import java.util.Collection;

import depends.relations.Inferer;

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
	public void inferLocalLevelEntities(Inferer inferer) {
		inheritedTypes= identiferToTypes(inferer,this.inhertedTypeIdentifiers);
		implementedTypes= identiferToTypes(inferer,this.implementedIdentifiers);
		if (inheritedTypes.size()>0)
			inheritedType = inheritedTypes.iterator().next();
		super.inferLocalLevelEntities(inferer);
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
 	
 	@Override
 	protected FunctionEntity lookupFunctionLocally(String functionName) {
		FunctionEntity funcType = super.lookupFunctionLocally(functionName);
		if (funcType!=null) return funcType;
		for (TypeEntity inhertedType : getInheritedTypes()) {
			funcType = inhertedType.lookupFunctionLocally(functionName);
			if (funcType != null)
				break;
		}
		if (funcType != null)
			return funcType;
		for (TypeEntity implType : getImplementedTypes()) {
			funcType = implType.lookupFunctionLocally( functionName);
			if (funcType != null)
				break;
		}
		return funcType;
 	}
 	
 	@Override
 	public TypeEntity getType() {
		return this;
	}
}
