package depends.extractor.pom;

import java.util.ArrayList;
import java.util.List;

import depends.entity.repo.EntityRepo;
import depends.extractor.ParserTest;
import depends.relations.Inferer;
import multilang.depends.util.file.TemporaryFile;

public abstract class MavenParserTest extends ParserTest{

	protected EntityRepo repo;
	private PomProcessor p;
	protected Inferer inferer;

	public void init() {
		List<String> includeDir = new ArrayList<>();
		includeDir.add("./src/test/resources/maven-code-examples/");
		includeDir.add("./src/test/resources/maven-code-examples/dependencyWithPropertiesOfParent");
		includeDir.add("./src/test/resources/maven-code-examples/dependencyWithoutVersion");
		
		this.p = new PomProcessor();
		p.includeDirs = includeDir.toArray(new String[] {});
		
		this.repo = p.getEntityRepo();
		this.inferer = p.inferer;
    	TemporaryFile.reset();

    }
	
	public PomFileParser createParser(String src) {
		return (PomFileParser) p.createFileParser(src);
	}
}
