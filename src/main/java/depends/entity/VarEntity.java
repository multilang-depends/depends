package depends.entity;

import java.util.ArrayList;
import java.util.List;

import depends.relations.Inferer;

public class VarEntity extends ContainerEntity {
	private String rawType;
	private TypeEntity type;
	private List<FunctionCall> functionCalls;
	private List<TypeEntity> candidateTypes;
	
	public VarEntity(String simpleName,  String rawType, Entity parent, int id) {
		super(simpleName,  parent,id);
		this.rawType = rawType;
		functionCalls = new ArrayList<>();
		candidateTypes = new ArrayList<>();
	}

	public void setRawType(String rawType) {
		this.rawType =rawType;
	}
	
	public String getRawType() {
		return rawType;
	}

	@Override
	public TypeEntity getType() {
		return type;
	}

	public void setType(TypeEntity type) {
		this.type = type;
	}

	@Override
	public void inferLocalLevelEntities(Inferer inferer) {
		super.inferLocalLevelEntities(inferer);
		Entity entity = inferer.resolveName(this, rawType, true);
		if (entity==null) return;
		type = entity.getType();
		if (type==null) {
			if (((ContainerEntity)getParent()).isGenericTypeParameter(rawType)) {
				type = Inferer.genericParameterType;
			}
		}
	}

	public List<FunctionCall> getCalledFunctions() {
		return functionCalls;
	}

	public void addFunctionCall(String fname) {
		this.functionCalls.add(new FunctionCall(fname));
	}

	public void fillCandidateTypes(Inferer inferer) {
		if (type!=null) return ; //it is a strong type lang, do not need deduce candidate types
		if (functionCalls.size()==0) return; //no information avaliable for type deduction
		this.candidateTypes = inferer.calculateCandidateTypes(this,this.functionCalls);
	}

	public List<TypeEntity> getCandidateTypes() {
		return candidateTypes;
	}
}
