package depends.matrix;

public class LeadingNameStripper implements ILeadingNameStrippper {
	String leadingSrcPath;
	public LeadingNameStripper(String leadingSrcPath) {
		this.leadingSrcPath = leadingSrcPath;
	}
	@Override
	public String stripFilename(String path) {
		if (path.startsWith(leadingSrcPath))
			path = "."+path.substring(leadingSrcPath.length());
		return path;
	}
}
