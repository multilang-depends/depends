package depends.format.path;

import org.apache.commons.io.FilenameUtils;

public class DotPathFilenameWritter implements FilenameWritter {
	@Override
	public String reWrite(String originalPath) {
		String[] segments = originalPath.split("[/\\\\]");
		StringBuilder sb = new StringBuilder();
		for(String segment:segments) {
			if (sb.length()>0)
				sb.append(".");
			sb.append(segment.replace(".", "_"));
		}
		if (sb.toString().startsWith("_")) {
			return sb.toString().substring(1);
		}
		return sb.toString();
	}

	private String replaceExt(String path, String ext) {
		if (ext==null) return path;
		if (ext.length()==0) return path;
		if (!path.endsWith(ext)) return path;
		path = path.substring(0,path.length()-ext.length()-1) + "_" + ext;
		return path;
	}
	
	public String reWriteMethod2(String originalPath) {
		String ext = FilenameUtils.getExtension(originalPath);
		String path = replaceExt(originalPath,ext);
		path =  path.replace('/', '.');
		path =  path.replace('\\', '.');
		return path;
	}
}
