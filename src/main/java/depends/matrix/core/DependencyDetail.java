package depends.matrix.core;

public class DependencyDetail {

	private String src;
	private String dest;

	public DependencyDetail(String src, String dest) {
		this.src = src;
		this.dest = dest;
	}
	public String getSrc() {
		return src;
	}

	public String getDest() {
		return dest;
	}
	@Override
	public String toString() {
		return src + "->" + dest;
	}

	
}
