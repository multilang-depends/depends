package depends.extractor.cpp.cdt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileIndex {

	HashMap<String,List<String>> includesMap = new HashMap<>();
	/**
	 * Whether the file already contains in the map;
	 * @param fileName
	 * @return
	 */
	public boolean contains(String fileName) {
		return includesMap.containsKey(fileName);
	}
	public String add(String filePath, String includedPath) {
		if (!contains(filePath)) {
			includesMap.put(filePath, new ArrayList<>());
		}
		String absolutePath = expandIncludedPath(filePath,includedPath);
		includesMap.get(filePath).add(absolutePath );
		return absolutePath;
	}
	
	private String expandIncludedPath(String filePath, String includedPath) {
		if (isAbsolutePath(includedPath))
			return includedPath;
		//CDT could resolve all pathes like relative path, etc. so we return directly
		return includedPath;
	}

	private boolean isAbsolutePath(String path) {
		//linux style absolute path
		if (path.startsWith("/")) return true;
		String regex = "^[A-Z]:\\.*";
		//windows style absolute path
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) return true;
		return false;
	}
	public List<String> get(String fileName) {
		return includesMap.get(fileName);
	}
}
