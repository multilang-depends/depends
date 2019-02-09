package depends.format.path;

public class EmptyFilenameWritter implements FilenameWritter {
	@Override
	public String reWrite(String originalPath) {
		return originalPath;
	}
}
