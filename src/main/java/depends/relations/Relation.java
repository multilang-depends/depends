package depends.relations;

import depends.entity.Entity;

/**
 * Dependency relation object
 */
public class Relation {
	private String type;
	private Entity toEntity;
	
	public Relation(String type, Entity toEntity) {
		this.toEntity = toEntity;
		this.type = type;
	}
	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Relation[" + type + "]-->" + toEntity.getId() + "(" + toEntity.getQualifiedName() + ")";
	}
	public Entity getEntity() {
		return toEntity;
	}
	
}
