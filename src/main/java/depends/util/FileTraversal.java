package depends.util;

import java.io.File;
import java.util.ArrayList;

/**
 * Recursively visit every file in the given root path using the 
 * extended IFileVisitor
 *
 */
public class FileTraversal {
	/**
	 * The visitor interface 
	 * Detail operation should be implemented here
	 */
	public interface IFileVisitor {
		void visit(File file);
	}
	
	IFileVisitor visitor;
	private ArrayList<String> extensionFilters = new ArrayList<>();
	public FileTraversal(IFileVisitor visitor){
		this.visitor = visitor;
	}

	public void travers(String path) {
		File dir = new File(path);
		travers(dir);
	}

	public void travers(File root) {
		File[] files = root.listFiles();

		if (files == null)
			return;
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				travers(files[i]);
			} else {
				File f = files[i];
				invokeVisitor(f);
			}
		}		
	}

	private void invokeVisitor(File f) {
		if (extensionFilters.size()==0) {
			visitor.visit(f);
		}else {
			for (String ext:extensionFilters) {
				if (f.getAbsolutePath().toLowerCase().endsWith(ext.toLowerCase())) {
					visitor.visit(f);
				}
			}
		}
	}

	public FileTraversal extensionFilter(String ext) {
		this.extensionFilters.add(ext.toLowerCase());
		return this;
	}

	public void extensionFilter(String[] fileSuffixes) {
		for (String fileSuffix:fileSuffixes){
			extensionFilter(fileSuffix);
		}
	}
}