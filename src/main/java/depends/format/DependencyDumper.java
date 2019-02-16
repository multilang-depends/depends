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

	public DependencyDumper(DependencyMatrix dependencies) {
		this.dependencyMatrix = dependencies;
	}
	
	public void outputResult(String projectName, String outputDir, String[] outputFormat) {
        outputDeps(projectName,outputDir,outputFormat);
	}
	
	private final void outputDeps(String projectName, String outputDir, String[] outputFormat) {
		@SuppressWarnings("unchecked")
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
	
}
