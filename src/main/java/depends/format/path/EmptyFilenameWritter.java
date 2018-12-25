package depends.format.path;

import depends.matrix.FilenameWritter;

public class EmptyFilenameWritter implements FilenameWritter {
	@Override
	public String reWrite(String originalPath) {
		return originalPath;
	}
}
