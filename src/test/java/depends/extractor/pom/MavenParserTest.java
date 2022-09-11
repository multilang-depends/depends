package depends.extractor.pom;

import depends.extractor.ParserTest;

import java.util.ArrayList;
import java.util.List;

public abstract class MavenParserTest extends ParserTest{

	public void init() {
		List<String> includeDir = new ArrayList<>();
		includeDir.add("./src/test/resources/maven-code-examples/");
		includeDir.add("./src/test/resources/maven-code-examples/dependencyWithPropertiesOfParent");
		includeDir.add("./src/test/resources/maven-code-examples/dependencyWithoutVersion");
		this.langProcessor = new PomProcessor();
		langProcessor.includeDirs = includeDir.toArray(new String[] {});
		super.init();
    }
	
	public PomFileParser createParser() {
		return (PomFileParser) langProcessor.createFileParser();
	}

}
