package depends.entity.types;

import depends.entity.Entity;

public class VarEntity extends Entity {

	private String type;

	public VarEntity(String fullName,  String type, int parentId, int id) {
		super(fullName,  parentId,id);
		this.type = type;
	}

	public String getType() {
		return type;
	}
    
}
