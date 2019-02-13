package depends.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import depends.relations.Inferer;

public class FunctionEntity extends ContainerEntity{
	private List<String> returnTypeIdentifiers = new ArrayList<>();
	Collection<VarEntity> parameters;
    Collection<String> throwTypesIdentifiers = new ArrayList<>(); 
	private Collection<TypeEntity> returnTypes = new ArrayList<>();
	private Collection<TypeEntity> throwTypes = new ArrayList<>();
    public FunctionEntity(String simpleName, Entity parent, Integer id, String returnType) {
		super(simpleName, parent,id);
		this.returnTypes = new ArrayList<>();
		returnTypeIdentifiers = new ArrayList<>();
		this.parameters = new ArrayList<>();
		throwTypesIdentifiers = new ArrayList<>();
		addReturnType(returnType);
	}
	public Collection<TypeEntity> getReturnTypes() {
		return returnTypes;
	}
	
	@Override
	public TypeEntity getType() {
		if (returnTypes.size()>0)
			return returnTypes.iterator().next();
		return null;
	}

	public void addReturnType(String returnType) {
		this.returnTypeIdentifiers.add(returnType);
	}
	
	public void addReturnType(TypeEntity returnType) {
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
			param.inferLocalLevelEntities(inferer);
		}
		if (returnTypes.size()<returnTypeIdentifiers.size())
			returnTypes = identiferToTypes(inferer,this.returnTypeIdentifiers);
		if (throwTypes.size()<throwTypesIdentifiers.size())
			throwTypes = identiferToTypes(inferer,this.throwTypesIdentifiers);
		super.inferLocalLevelEntities(inferer);
		if (this.returnTypes.size()==0 && this.getLastExpressionType()!=null) {
			this.returnTypes.add(this.getLastExpressionType());
		}
	}
	public Collection<VarEntity> getParameters() {
		return parameters;
	}
	public Collection<TypeEntity> getThrowTypes() {
		return throwTypes;
	}
	@Override
	public VarEntity lookupVarsInVisibleScope(String varName) {
		for (VarEntity param:parameters) {
			if (varName.equals(param.getRawName())) {
				return param;
			}
		}
		return super.lookupVarsInVisibleScope(varName);
	}
	public void addParameter(VarEntity var) {
		this.parameters.add(var);
	}
	@Override
	public String getDisplayName() {
		FileEntity f = (FileEntity) this.getAncestorOfType(FileEntity.class);
		return f.getRawName()+"("+getRawName()+")";
	}
}
