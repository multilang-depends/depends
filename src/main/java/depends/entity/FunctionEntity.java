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
import java.util.List;

import depends.relations.Inferer;

public class FunctionEntity extends ContainerEntity{
	private List<String> returnTypeIdentifiers = new ArrayList<>();
	Collection<VarEntity> parameters;
    Collection<String> throwTypesIdentifiers = new ArrayList<>(); 
	private Collection<Entity> returnTypes = new ArrayList<>();
	private Collection<Entity> throwTypes = new ArrayList<>();
	public FunctionEntity() {
		this.parameters = new ArrayList<>();
	}
    public FunctionEntity(String simpleName, Entity parent, Integer id, String returnType) {
		super(simpleName, parent,id);
		this.returnTypes = new ArrayList<>();
		returnTypeIdentifiers = new ArrayList<>();
		this.parameters = new ArrayList<>();
		throwTypesIdentifiers = new ArrayList<>();
		addReturnType(returnType);
	}
	public Collection<Entity> getReturnTypes() {
		return returnTypes;
	}
	
	@Override
	public TypeEntity getType() {
		if (returnTypes.size()>0){
			Object type = returnTypes.iterator().next();
			if (type instanceof TypeEntity)
				return (TypeEntity)type;
		}
		return null;
	}

	public void addReturnType(String returnType) {
		if (returnType==null) return;
		this.returnTypeIdentifiers.add(returnType);
	}
	
	public void addReturnType(TypeEntity returnType) {
		if (returnType==null) return;
		if (!this.returnTypeIdentifiers.contains(returnType.rawName)){
			this.returnTypeIdentifiers.add(returnType.rawName);
			this.returnTypes.add(returnType);
		}
	}

	public void addThrowTypes(List<String> throwedType) {
		throwTypesIdentifiers.addAll(throwedType);
	}
	
	@Override
	public void inferLocalLevelEntities(Inferer inferer) {
		for (VarEntity param:parameters) {
			param.fillCandidateTypes(inferer);
			param.inferLocalLevelEntities(inferer);
		}
		if (returnTypes.size()<returnTypeIdentifiers.size())
			returnTypes = identiferToEntities(inferer,this.returnTypeIdentifiers);
		if (throwTypes.size()<throwTypesIdentifiers.size())
			throwTypes = identiferToEntities(inferer,this.throwTypesIdentifiers);
		super.inferLocalLevelEntities(inferer);
		if (this.returnTypes.size()==0 && this.getLastExpressionType()!=null) {
			this.returnTypes.add(this.getLastExpressionType());
		}
	}
	public Collection<VarEntity> getParameters() {
		return parameters;
	}
	public Collection<Entity> getThrowTypes() {
		return throwTypes;
	}
	@Override
	public VarEntity lookupVarInVisibleScope(String varName) {
		for (VarEntity param:parameters) {
			if (varName.equals(param.getRawName())) {
				return param;
			}
		}
		return super.lookupVarInVisibleScope(varName);
	}
	public void addParameter(VarEntity var) {
		this.parameters.add(var);
	}
	@Override
	public String getDisplayName() {
		FileEntity f = (FileEntity) this.getAncestorOfType(FileEntity.class);
		return f.getRawName()+"("+this.getQualifiedName()+")";
	}
	@Override
	public VarEntity lookupVarLocally(String varName) {
		for (VarEntity var:this.parameters) {
			if (var.getRawName().equals(varName))
				return var;
		}
		return super.lookupVarLocally(varName);
	}
}
