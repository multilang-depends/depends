package depends.util;

import depends.extractor.AbstractLangWorker;

public class Configure {
    public Configure() {}


    private String  inputSrcPath;
    private String  includeSrcPath;
    private String  projectName ;

    private String  schemaVersion = "1.0";
	private AbstractLangWorker worker;


    public String getInputSrcPath() {
        return inputSrcPath;
    }

    public void setInputSrcPath(String inputSrcPath) {
        this.inputSrcPath = inputSrcPath;
    }

    public String getIncludePath() {
        return includeSrcPath;
    }

    public void setIncludePath(String includePath) {
        this.includeSrcPath = includePath;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getOutputJsonFile() {
        return projectName  + "_dep.json";
    }

    public String getOutputXmlFile() {
    	return projectName + "_dep.xml";
    }

    public String getSchemaVersion() {
        return schemaVersion;
    }

    public String getAttributeName() {
        return projectName + "-sdsm";
    }

	public String getOutputExcelFile() {
		return projectName + "_dep.xls";
	}
	
	public String getOutputDotFile() {
		return projectName + "_dep.dot";
	}
	
	public void setWorker(AbstractLangWorker worker) {
		this.worker = worker;
	}

	public AbstractLangWorker getWorker() {
		return worker;
	}


}

