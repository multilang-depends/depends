package depends.matrix.core;


public class DependencyDetail {
	private LocationInfo from;
	private LocationInfo to;

	public DependencyDetail(LocationInfo from, LocationInfo to) {
		this.from = from;
		this.to = to;
	}
	@Override
	public String toString() {
		return from + "->" + to;
	}

	public LocationInfo getSrc() {
		return from;
	}

	public LocationInfo getDest() {
		return to;
	}
}
