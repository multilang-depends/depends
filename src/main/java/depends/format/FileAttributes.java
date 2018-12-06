package depends.format;

public class FileAttributes {
	private String  schemaVersion = "1.0";
	private String attributeName;
    public FileAttributes( String projectName) {
    	 this.attributeName = projectName + "-sdsm";
    }

    public String getSchemaVersion() {
        return schemaVersion;
    }

    public String getAttributeName() {
        return this.attributeName;
    }
}

