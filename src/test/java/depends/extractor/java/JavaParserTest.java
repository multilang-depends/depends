package depends.extractor.java;

import depends.entity.repo.EntityRepo;
import depends.entity.repo.InMemoryEntityRepo;
import depends.extractor.AbstractLangProcessor;
import depends.extractor.ParserTest;
import depends.extractor.UnsolvedBindings;
import depends.relations.Inferer;
import multilang.depends.util.file.TemporaryFile;

import java.util.Set;

public abstract class JavaParserTest  extends ParserTest{
	protected  EntityRepo entityRepo ;
	protected Inferer inferer ;
	private AbstractLangProcessor langProcessor;

	public void init() {
		entityRepo = new InMemoryEntityRepo();
		inferer = new Inferer(entityRepo,new JavaImportLookupStrategy(entityRepo),new JavaBuiltInType());
    	TemporaryFile.reset();
		langProcessor = new JavaProcessor();
	}
	
	public JavaFileParser createParser(String src) {
		return new JavaFileParser(src,entityRepo, inferer);
	}

	public Set<UnsolvedBindings> resolveAllBindings() {
		return inferer.resolveAllBindings(false,langProcessor);
	}
}
