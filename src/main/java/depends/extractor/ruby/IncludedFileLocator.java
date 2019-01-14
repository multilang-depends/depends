package depends.extractor.ruby;

import java.io.File;
import java.util.List;

import depends.util.FileUtil;

public class IncludedFileLocator {
	private List<String> includesPath;
	public IncludedFileLocator(List<String> includedPath) {
		this.includesPath = includedPath;
	}
	public String uniqFileName(String startLocation, String importedFilename) {
		if (FileUtil.existFile(importedFilename)) return FileUtil.uniqFilePath(importedFilename);
		String dirPath = FileUtil.getLocatedDir(startLocation);
		String path = dirPath + File.separator + importedFilename;
		if (FileUtil.existFile(path)) return FileUtil.uniqFilePath(path);
		for (String includePath:includesPath) {
			path = includePath + File.separator + importedFilename;
			if (FileUtil.existFile(path)) return FileUtil.uniqFilePath(path);
		}
		return null;
	}
}
