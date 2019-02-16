package depends.extractor.pom;

import depends.importtypes.Import;

public class PomParent extends Import {

	public PomParent(String content) {
		super(content);
	}

	public String groupId;
	public String artifactId;
	public String version;

}
