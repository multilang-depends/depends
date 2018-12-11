package depends.format.dot;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import depends.format.AbstractFormatDependencyDumper;
import depends.matrix.DependencyMatrix;
import depends.matrix.DependencyPair;

public class DotFormatDependencyDumper extends  AbstractFormatDependencyDumper{
	@Override
	public String getFormatName() {
		return "dot";
	}
	public DotFormatDependencyDumper(DependencyMatrix dependencyMatrix, String projectName, String outputDir) {
		super(dependencyMatrix,projectName,outputDir);
	}
	@Override
	public boolean output() {
		PrintWriter writer;
		try {
			writer = new PrintWriter(composeFilename()+".dot");
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
