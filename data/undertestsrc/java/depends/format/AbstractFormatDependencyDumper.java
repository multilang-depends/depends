package depends.format;

import java.io.File;

import depends.matrix.DependencyMatrix;

public abstract class AbstractFormatDependencyDumper {
	protected String name;
	protected DependencyMatrix matrix;
	protected String outputDir;

	public AbstractFormatDependencyDumper(DependencyMatrix matrix, String projectName, String outputDir) {
		this.matrix = matrix;
		this.name = projectName;
		this.outputDir = outputDir;
	}

	public abstract boolean output();
	public abstract String getFormatName();
	protected String composeFilename() {
		return outputDir+File.separator+name;
	}
}
