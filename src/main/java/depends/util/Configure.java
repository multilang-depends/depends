package depends.util;

import depends.extractor.AbstractLangWorker;

public class Configure {
    private Configure() {}

    private static Configure configure = new Configure();
    public static Configure getConfigureInstance() {
        return configure;
    }

    private String  inputSrcPath;
    private String  usageSrcPath;
    private String  analyzedProjectName ;

    private String  schemaVersion = "1.0";
	private AbstractLangWorker worker;


    public String getInputSrcPath() {
        return inputSrcPath;
    }

    public void setInputSrcPath(String inputSrcPath) {
        this.inputSrcPath = inputSrcPath;
    }

    public String getUsageSrcPath() {
        return usageSrcPath;
    }

    public void setUsageSrcPath(String usageSrcPath) {
        this.usageSrcPath = usageSrcPath;
    }

    public String getAnalyzedProjectName() {
        return analyzedProjectName;
    }

    public void setAnalyzedProjectName(String analyzedProjectName) {
        this.analyzedProjectName = analyzedProjectName;
    }

    public String getOutputJsonFile() {
        return analyzedProjectName  + "_dep.json";
    }

    public String getOutputXmlFile() {
    	return analyzedProjectName + "_dep.xml";
    }

    public String getSchemaVersion() {
        return schemaVersion;
    }

    public String getAttributeName() {
        return analyzedProjectName + "-sdsm";
    }

	public void setWorker(AbstractLangWorker worker) {
		this.worker = worker;
	}

	public AbstractLangWorker getWorker() {
		return worker;
	}

	public String getOutputExcelFile() {
		return analyzedProjectName + "_dep.xls";
	}
}

