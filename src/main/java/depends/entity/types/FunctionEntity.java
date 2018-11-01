package depends.entity.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import depends.entity.ContainerEntity;
import depends.entity.Entity;
import depends.entity.TypeInfer;

public class FunctionEntity extends ContainerEntity{
	private List<String> returnTypeIdentifiers;
	Collection<VarEntity> parameters;
    Collection<String> throwTypesIdentifiers; 
	private Collection<TypeEntity> returnTypes;
	private TypeEntity returnType;
	private Collection<TypeEntity> throwTypes;
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
	
	public TypeEntity getReturnType() {
		return returnType;
	}

	public void addReturnType(String returnType) {
		this.returnTypeIdentifiers.add(returnType);
	}
	public void addThrowTypes(List<String> throwedType) {
		throwTypesIdentifiers.addAll(throwedType);
	}
	
	@Override
	public void inferLocalLevelTypes(TypeInfer typeInferer) {
		for (VarEntity param:parameters) {
			param.inferLocalLevelTypes(typeInferer);
		}
		returnTypes= identiferToTypes(typeInferer,this.returnTypeIdentifiers);
		if (returnTypes.size()>0)
			returnType = returnTypes.iterator().next();
		throwTypes= identiferToTypes(typeInferer,this.throwTypesIdentifiers);
		super.inferLocalLevelTypes(typeInferer);
	}
	public Collection<VarEntity> getParameters() {
		return parameters;
	}
	public Collection<TypeEntity> getThrowTypes() {
		return throwTypes;
	}
	@Override
	public VarEntity resolveVarBindings(String varName) {
		for (VarEntity param:parameters) {
			if (varName.equals(param.getRawName())) {
				return param;
			}
		}
		return super.resolveVarBindings(varName);
	}
	public void addParameter(VarEntity var) {
		this.parameters.add(var);
	}
	public void setReturnType(TypeEntity returnType) {
		this.returnType = returnType;
	}

}
