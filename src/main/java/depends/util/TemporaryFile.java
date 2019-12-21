package depends.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import depends.util.FileTraversal.IFileVisitor;

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
	
	public String macroPath(Integer fileId) {
		return tempDirWithPrefix.toAbsolutePath().toFile() + File.separator + fileId + ".macros";
	}
	
	public void delete() {
		if (tempDirWithPrefix==null) return;
		IFileVisitor visitor = new IFileVisitor() {
			@Override
			public void visit(File file) {
				try {
					Files.deleteIfExists(file.toPath());
				} catch (IOException e) {
				}
			}
		};
		FileTraversal t = new FileTraversal(visitor,true,true);
		t.travers(tempDirWithPrefix.toAbsolutePath().toFile());
		try {
			Files.deleteIfExists(tempDirWithPrefix);
		} catch (IOException e) {
		}
	}

}
