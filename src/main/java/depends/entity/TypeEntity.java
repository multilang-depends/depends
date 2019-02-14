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
		inheritedTypes.remove(this);
		implementedTypes= identiferToTypes(inferer,this.implementedIdentifiers);
		implementedTypes.remove(this);
		if (inheritedTypes.size()>0)
			inheritedType = inheritedTypes.iterator().next();
		super.inferLocalLevelEntities(inferer);
	}
	public void addImplements(String typeName) {
		if (typeName.equals(this.getRawName())) return;
		if (implementedIdentifiers.contains(typeName)) return;
		if (typeName.equals(this.rawName)) return;
		this.implementedIdentifiers.add(typeName);
	}
	public void addExtends(String typeName) {
		if (typeName.equals(this.getRawName())) return;
		if (inhertedTypeIdentifiers.contains(typeName)) return;
		if (typeName.equals(this.rawName)) return;
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
 	public FunctionEntity lookupFunctionLocally(String functionName) {
		FunctionEntity func = super.lookupFunctionLocally(functionName);
		if (func!=null) return func;
		for (TypeEntity inhertedType : getInheritedTypes()) {
			func = inhertedType.lookupFunctionLocally(functionName);
			if (func != null)
				break;
		}
		if (func != null)
			return func;
		for (TypeEntity implType : getImplementedTypes()) {
			func = implType.lookupFunctionLocally( functionName);
			if (func != null)
				break;
		}
		return func;
 	}
 	@Override
 	public VarEntity lookupVarLocally(String varName) {
 		VarEntity var = super.lookupVarLocally(varName);
		if (var!=null) return var;
		for (TypeEntity inhertedType : getInheritedTypes()) {
			var = inhertedType.lookupVarLocally(varName);
			if (var != null)
				break;
		}
		if (var != null)
			return var;
		for (TypeEntity implType : getImplementedTypes()) {
			var = implType.lookupVarLocally( varName);
			if (var != null)
				break;
		}
		return var;
	}
 	@Override
 	public TypeEntity getType() {
		return this;
	}
}
