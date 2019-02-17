package depends.extractor.pom;

import java.io.File;
import java.util.List;

import depends.util.FileUtil;

public class PomLocator {

	private List<String> includePaths;
	private PomParent pomParent;

	public PomLocator(List<String> includePaths, PomParent pomParent) {
		this.includePaths = includePaths;
		this.pomParent = pomParent;
	}

	public String getLocation() {
		StringBuilder sb = new StringBuilder();
		sb.append(pomParent.groupId.replace(".", File.separator));
		sb.append(File.separator);
		sb.append(pomParent.artifactId);
		sb.append(File.separator);
		sb.append(pomParent.version);
		sb.append(File.separator);
		sb.append(pomParent.artifactId);
		sb.append("-");
		sb.append(pomParent.version);
		sb.append(".pom");
		System.out.println(sb.toString());
		for (String includePath:includePaths) {
			String path = includePath+File.separator+sb.toString();
			if (FileUtil.existFile(path)) {
				return FileUtil.uniqFilePath(path);
			}
		}
		return null;
	}

}
