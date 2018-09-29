package depends.entity;

public class Relation {
	private String type;
	private Integer toId = -1;
	private String toFullName;
	public Relation(String type, String rhsFullName) {
		this.type = type;
		this.toFullName = rhsFullName;
	}
	public Relation(String type, Integer fileId) {
		this.type = type;
		this.toId = fileId;
	}
	
	public Relation(String type, Integer fileId,String rhsFullName) {
		this.type = type;
		this.toId = fileId;
		this.toFullName = rhsFullName;
	}
	
	public String getType() {
		return type;
	}
	public Integer getToId() {
		return toId;
	}
	public String getToFullName() {
		return toFullName;
	}
	public void refreshToId(int rhs) {
		this.toId  = rhs;
	}
	@Override
	public String toString() {
		return "Relation[" + type + "]-->" + toId + "(" + toFullName + ")";
	}
	
}
