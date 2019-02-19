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
