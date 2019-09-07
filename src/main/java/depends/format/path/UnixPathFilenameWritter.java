package depends.format.path;

public class UnixPathFilenameWritter implements FilenameWritter{
	@Override
	public String reWrite(String originalPath) {
		return originalPath.replaceAll("\\\\", "/");
	}
}
