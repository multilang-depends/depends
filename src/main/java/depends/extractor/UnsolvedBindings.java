package depends.extractor;

import depends.entity.Entity;

public class UnsolvedBindings {

	private String rawName;
	private Entity fromEntity;

	public UnsolvedBindings(String rawName, Entity fromEntity) {
		this.rawName = rawName;
		this.fromEntity = fromEntity;
	}

	public String getRawName() {
		return rawName;
	}

	public Entity getFromEntity() {
		return fromEntity;
	}

	public String getSourceDisplay() {
		if (fromEntity==null) return "<not known>";
		return fromEntity.getQualifiedName();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fromEntity == null) ? 0 : fromEntity.hashCode());
		result = prime * result + ((rawName == null) ? 0 : rawName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UnsolvedBindings other = (UnsolvedBindings) obj;
		if (fromEntity == null) {
			if (other.fromEntity != null)
				return false;
		} else if (!fromEntity.equals(other.fromEntity))
			return false;
		if (rawName == null) {
			if (other.rawName != null)
				return false;
		} else if (!rawName.equals(other.rawName))
			return false;
		return true;
	}

	public void setFromEntity(Entity fromEntity) {
		this.fromEntity = fromEntity;
	}
	

}
