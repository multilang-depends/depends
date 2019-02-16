package depends.format.path;

import org.apache.commons.io.FilenameUtils;

public class DotPathFilenameWritter implements FilenameWritter {
	@Override
	public String reWrite(String originalPath) {
		String ext = FilenameUtils.getExtension(originalPath);
		String path = replaceExt(originalPath,ext);
		path =  path.replace('/', '.');
		path =  path.replace('\\', '.');
		return path;
	}

	private String replaceExt(String path, String ext) {
		if (ext==null) return path;
		if (ext.length()==0) return path;
		if (!path.endsWith(ext)) return path;
		path = path.substring(0,path.length()-ext.length()-1) + "_" + ext;
		return path;
	}
}
