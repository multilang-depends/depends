package depends.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import depends.util.FileTraversal.IFileVisitor;

public class FolderCollector implements IFileVisitor {
	List<String> folders;
	FileTraversal fileTransversal ;
	public FolderCollector() {
		fileTransversal = new FileTraversal(this,true,false);
	}
	
	public List<String> getFolders(String path){
		folders = new ArrayList<>();
		folders.add(path);
		fileTransversal.travers(path);
		return folders;
	}
	@Override
	public void visit(File file) {
		if (file.isDirectory())
			try {
				folders.add(file.getCanonicalPath());
			} catch (IOException e) {
				e.printStackTrace(); //should never happen
			}
	}

}
