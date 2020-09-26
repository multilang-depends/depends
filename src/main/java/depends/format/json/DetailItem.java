package depends.format.json;

import depends.matrix.core.LocationInfo;

public class DetailItem {

	private LocationInfo src;
	private LocationInfo dest;
	private String type;

	public DetailItem(LocationInfo src, LocationInfo dest, String type) {
		this.src = src;
		this.dest = dest;
		this.type = type;
	}

	public LocationInfo getSrc() {
		return src;
	}

	public LocationInfo getDest() {
		return dest;
	}

	public String getType() {
		return type;
	}

}
