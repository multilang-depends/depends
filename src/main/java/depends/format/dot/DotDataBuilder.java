package depends.format.dot;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import depends.matrix.DependencyMatrix;
import depends.matrix.DependencyPair;

public class DotDataBuilder {
	private DependencyMatrix matrix;

	public DotDataBuilder(DependencyMatrix matrix) {
		this.matrix = matrix;
	}

	public boolean output(String outputDotFile) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(outputDotFile);
			ArrayList<String> files = matrix.getNodes();
			
			for (int i=0;i<files.size();i++) {
				String file = files.get(i);
				writer.println("// "+i + ":"+file);
			}
			writer.println("digraph");
			writer.println("{");
	        Collection<DependencyPair> dependencyPairs = matrix.getDependencyPairs();

	        addRelations(writer,dependencyPairs); 
			writer.println("}");
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
        		writer.println("\t"+src + " -> " + dst + ";");
        }		
	}
}
