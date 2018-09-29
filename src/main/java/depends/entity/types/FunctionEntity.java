package depends.entity.types;

import depends.entity.ContainerEntity;

public class FunctionEntity extends ContainerEntity{
	private String returnType;
	private String shortName;
	public String getShortName() {
		return shortName;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public FunctionEntity(String fullName, int parentId, Integer id, String resultType, String shortName) {
		super(fullName, parentId,id);
		this.returnType = resultType;
		this.shortName = shortName;
	}

}
