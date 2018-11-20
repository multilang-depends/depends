package depends.importtypes;

public abstract class Import {
	private String content;
	public String getContent() {
		return content;
	}
	public Import(String content) {
		this.content = content;
	}
}
