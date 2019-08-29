package depends.extractor.pom;

import java.util.ArrayList;
import java.util.List;

import depends.entity.repo.EntityRepo;
import depends.relations.Inferer;
import depends.util.TemporaryFile;

public abstract class MavenParserTest {

	protected EntityRepo repo;
	private PomProcessor p;
	protected Inferer inferer;

	public void init() {
		List<String> includeDir = new ArrayList<>();
		includeDir.add("./src/test/resources/maven-code-examples/");
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
