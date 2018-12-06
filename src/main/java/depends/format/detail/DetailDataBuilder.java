package depends.format.detail;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import depends.matrix.DependencyMatrix;
import depends.matrix.DependencyPair;
import depends.matrix.DependencyValue;

public class DetailDataBuilder {
	private DependencyMatrix matrix;
	ArrayList<String> files;

	public DetailDataBuilder(DependencyMatrix matrix) {
		this.matrix = matrix;
	}

	public boolean output(String outputDotFile) {
		PrintWriter writer;
		try {
			files = matrix.getNodes();
			writer = new PrintWriter(outputDotFile);
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
