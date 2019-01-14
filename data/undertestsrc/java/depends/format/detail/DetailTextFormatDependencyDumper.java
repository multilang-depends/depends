package depends.format.detail;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import depends.format.AbstractFormatDependencyDumper;
import depends.matrix.DependencyMatrix;
import depends.matrix.DependencyPair;
import depends.matrix.DependencyValue;

public class DetailTextFormatDependencyDumper extends AbstractFormatDependencyDumper{
	ArrayList<String> files;
	@Override
	public String getFormatName() {
		return "detail";
	}
	public DetailTextFormatDependencyDumper(DependencyMatrix matrix, String name, String outputDir) {
		super(matrix,name,outputDir);
	}
	@Override
	public boolean output() {
		PrintWriter writer;
		try {
			files = matrix.getNodes();
			writer = new PrintWriter(composeFilename() +".txt");
	        Collection<DependencyPair> dependencyPairs = matrix.getDependencyPairs();
	        addRelations(writer,dependencyPairs); 
			writer.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void addRelations(PrintWriter writer, Collection<DependencyPair> dependencyPairs) {
		for (DependencyPair dependencyPair:dependencyPairs) {
            int src = dependencyPair.getFrom();
            int dst = dependencyPair.getTo();
        	writer.println("======="+files.get(src) + " -> " + files.get(dst) + "=========");
        	for (DependencyValue dependency:dependencyPair.getDependencies()) {
        	writer.println(dependency.getDetails());
        	}
        }		
	}
}
