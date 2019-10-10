package depends.format.plantuml;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import depends.format.AbstractFormatDependencyDumper;
import depends.matrix.core.DependencyMatrix;
import depends.matrix.core.DependencyPair;
import depends.matrix.core.DependencyValue;

public class BriefPlantUmlFormatDependencyDumper extends AbstractFormatDependencyDumper {
	@Override
	public String getFormatName() {
		return "briefplantuml";
	}
	
	public BriefPlantUmlFormatDependencyDumper(DependencyMatrix dependencyMatrix, String projectName, String outputDir) {
		super(dependencyMatrix,projectName,outputDir);
	}


	@Override
	public boolean output() {
		PrintWriter writer;
		try {
			writer = new PrintWriter(composeFilename()+".uml");
			ArrayList<String> files = matrix.getNodes();
			
			for (int i=0;i<files.size();i++) {
				String file = files.get(i);
				writer.println("class " + " "+file);
			}
			writer.println("@startuml");
	        Collection<DependencyPair> dependencyPairs = matrix.getDependencyPairs();
	        addRelations(writer,dependencyPairs); 
			writer.println("@enduml");
			writer.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void addRelations(PrintWriter writer, Collection<DependencyPair> dependencyPairs) {
		
		HashMap <String,HashMap<String,Integer>> relationMap = new HashMap<>();
		for (DependencyPair dependencyPair:dependencyPairs) {
            int src = dependencyPair.getFrom();
            int dst = dependencyPair.getTo();
            for (DependencyValue dep:dependencyPair.getDependencies()) {
            	String key = getNodeName(src)+"..>" + getNodeName(dst);
            	if (!relationMap.containsKey(key)){
            		relationMap.put(key, new HashMap<>());
            	}
            	HashMap<String, Integer> relationValues = relationMap.get(key);
            	
            	Integer value = 0;
            	if (!relationValues.containsKey(dep.getType())) {
            		relationValues.get(dep.getType());
            	}
            	relationValues.put(dep.getType(), value+=dep.getWeight());
            }
		}		
		
		for (String key:relationMap.keySet()) {
        	writer.println("\t"+key + " : " +  buildNotes(relationMap.get(key)) );
		}
	}


	private String buildNotes(HashMap<String, Integer> relations) {
		StringBuffer sb = new StringBuffer();
		for (String dep:relations.keySet()) {
			sb.append(dep.substring(0,3).toUpperCase()).append(relations.get(dep));
		}
		return sb.toString();
	}

	private String getNodeName(int src) {
		String result = matrix.getNodeName(src);
		result = result.replace("-", "_");
		return result;
	}
}
