package depends.extractor.pom;

import depends.entity.repo.EntityRepo;
import depends.extractor.ParserTest;
import depends.extractor.UnsolvedBindings;
import depends.relations.Inferer;
import multilang.depends.util.file.TemporaryFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class MavenParserTest extends ParserTest{

	protected EntityRepo repo;
	private PomProcessor langProcessor;
	protected Inferer inferer;

	public void init() {
		List<String> includeDir = new ArrayList<>();
		includeDir.add("./src/test/resources/maven-code-examples/");
		includeDir.add("./src/test/resources/maven-code-examples/dependencyWithPropertiesOfParent");
		includeDir.add("./src/test/resources/maven-code-examples/dependencyWithoutVersion");
		
		this.langProcessor = new PomProcessor();
		langProcessor.includeDirs = includeDir.toArray(new String[] {});
		
		this.repo = langProcessor.getEntityRepo();
		this.inferer = langProcessor.inferer;
    	TemporaryFile.reset();

    }
	
	public PomFileParser createParser(String src) {
		return (PomFileParser) langProcessor.createFileParser(src);
	}

	public Set<UnsolvedBindings> resolveAllBindings() {
		return inferer.resolveAllBindings(false, langProcessor);
	}
}
