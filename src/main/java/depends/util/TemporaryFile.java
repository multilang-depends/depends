package depends.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TemporaryFile {
	Path tempDirWithPrefix;
	private static TemporaryFile _inst = null;
	public static void reset() {
		_inst=null;
	}
	public static TemporaryFile getInstance() {
		if (_inst==null)
			_inst = new TemporaryFile();
		return _inst;
	}
	
	private TemporaryFile() {
		try {
			tempDirWithPrefix = Files.createTempDirectory("depends.tmp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String exprPath(Integer id) {
		return tempDirWithPrefix.toAbsolutePath().toFile() + File.separator + id + ".expr";
	}
}
