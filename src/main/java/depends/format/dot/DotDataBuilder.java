package depends.format.dot;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import depends.format.matrix.DependencyMatrix;

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
			Map<Integer, Map<Integer, Map<String, Integer>>> finalRes = matrix.getRelations();
	        addRelations(writer,finalRes); 
			writer.println("}");
			writer.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void addRelations(PrintWriter writer, Map<Integer, Map<Integer, Map<String, Integer>>> finalRes) {
		for (Map.Entry<Integer, Map<Integer, Map<String, Integer>>> entry1 : finalRes.entrySet()) {
            int src = entry1.getKey();

            Map<Integer, Map<String, Integer>> values1 = entry1.getValue();
            for (Map.Entry<Integer, Map<String, Integer>> entry2: values1.entrySet()) {
                int dst = entry2.getKey();
        		writer.println("\t"+src + " -> " + dst + ";");
            }
        }
	}
}
