package depends.format.json;

public class DetailItem {

	private String src;
	private String dest;
	private String type;

	public DetailItem(String src, String dest, String type) {
		this.src = src;
		this.dest = dest;
		this.type = type;
	}

	public String getSrc() {
		return src;
	}

	public String getDest() {
		return dest;
	}

	public String getType() {
		return type;
	}

}
