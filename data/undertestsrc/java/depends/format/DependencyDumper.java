package depends.format;

import java.util.List;

import depends.format.detail.DetailTextFormatDependencyDumper;
import depends.format.dot.DotFormatDependencyDumper;
import depends.format.excel.ExcelFormatDependencyDumper;
import depends.format.json.JsonFormatDependencyDumper;
import depends.format.xml.XmlFormatDependencyDumper;
import depends.matrix.DependencyMatrix;
import edu.emory.mathcs.backport.java.util.Arrays;

public class DependencyDumper {

	private DependencyMatrix dependencyMatrix;
	private List<String> errors;

	public DependencyDumper(DependencyMatrix dependencies, List<String> errors) {
		this.dependencyMatrix = dependencies;
		this.errors = errors;
	}
	
	public void outputResult(String projectName, String outputDir, String[] outputFormat) {
        outputErrors();
        outputDeps(projectName,outputDir,outputFormat);
	}
	
	private final void outputDeps(String projectName, String outputDir, String[] outputFormat) {
		List<String> formatList = Arrays.asList(outputFormat);
		AbstractFormatDependencyDumper[] builders = new AbstractFormatDependencyDumper[] {
		 	new DetailTextFormatDependencyDumper(dependencyMatrix,projectName,outputDir),
		 	new XmlFormatDependencyDumper(dependencyMatrix,projectName,outputDir),
		 	new JsonFormatDependencyDumper(dependencyMatrix,projectName,outputDir),
		 	new ExcelFormatDependencyDumper(dependencyMatrix,projectName,outputDir),
		 	new DotFormatDependencyDumper(dependencyMatrix,projectName,outputDir),
		};
		for (AbstractFormatDependencyDumper builder:builders) {
			if (formatList.contains(builder.getFormatName())){
				builder.output();
			}
		}
    }
	
	private void outputErrors() {
		for (String e:errors) {
			System.err.println(e);
		}
	}
}
