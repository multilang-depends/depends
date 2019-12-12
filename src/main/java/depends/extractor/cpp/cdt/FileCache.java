package depends.extractor.cpp.cdt;

import java.util.HashMap;

import org.eclipse.cdt.core.index.IIndexFileLocation;
import org.eclipse.cdt.internal.core.parser.scanner.InternalFileContent;

public class FileCache {
	private HashMap<String, InternalFileContent> cache;
	private HashMap<IIndexFileLocation, InternalFileContent> cache2;
	private FileCache() {
		this.cache = new HashMap<>();
	}
	static FileCache inst = null;
	public static FileCache getInstance() {
		if (inst==null) inst = new FileCache();
		return inst;
	}
	public InternalFileContent get(String filePath) {
		InternalFileContent result = cache.get(filePath);
		return result;
	}
	public void put(String filePath, InternalFileContent c) {
		cache.put(filePath,c);
	}
	public InternalFileContent get(IIndexFileLocation ifl) {
		InternalFileContent result = cache2.get(ifl);
		return result;
	}
	public void put(IIndexFileLocation ifl, InternalFileContent c) {
		cache2.put(ifl,c);
	}

}
