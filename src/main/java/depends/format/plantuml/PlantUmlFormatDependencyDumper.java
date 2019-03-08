package depends.format.plantuml;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import depends.deptypes.DependencyType;
import depends.format.AbstractFormatDependencyDumper;
import depends.matrix.core.DependencyMatrix;
import depends.matrix.core.DependencyPair;
import depends.matrix.core.DependencyValue;

public class PlantUmlFormatDependencyDumper extends AbstractFormatDependencyDumper {
	@Override
	public String getFormatName() {
		return "plantuml";
	}
	
	public PlantUmlFormatDependencyDumper(DependencyMatrix dependencyMatrix, String projectName, String outputDir) {
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
		for (DependencyPair dependencyPair:dependencyPairs) {
            int src = dependencyPair.getFrom();
            int dst = dependencyPair.getTo();
            Set<String> relations = new HashSet<>();
            for (DependencyValue dep:dependencyPair.getDependencies()) {
            	relations.add("\t"+ getNodeName(src) + " " + getRelationSymbol(dep.getType()) +" " + getNodeName(dst) + "");
            }
            for (String relation:relations) {
            	writer.println(relation);
            }
		}		
	}

	private String getRelationSymbol(String type) {
		if (type.equals(DependencyType.IMPLEMENT)) {
			return "..|>";
		}else if (type.equals(DependencyType.INHERIT)) {
			return "--|>";
		}else if (type.equals(DependencyType.CONTAIN)) {
			return "*-->";
		}else if (type.equals(DependencyType.MIXIN)) {
			return "o-->";
		}
		return "..>";
	}

	private String getNodeName(int src) {
		String result = matrix.getNodeName(src);
		result = result.replace("-", "_");
		return result;
	}
}
