package depends.extractor.ruby;

import depends.entity.repo.EntityRepo;
import depends.entity.repo.InMemoryEntityRepo;
import depends.extractor.*;
import depends.extractor.ruby.jruby.JRubyFileParser;
import depends.relations.Inferer;
import multilang.depends.util.file.TemporaryFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public abstract class RubyParserTest extends ParserTest implements ParserCreator{
	protected  EntityRepo entityRepo ;
	protected Inferer inferer ;
	private AbstractLangProcessor langProcessor;

	public void init() {
		entityRepo = new InMemoryEntityRepo();
		inferer = new Inferer(entityRepo,new RubyImportLookupStrategy(entityRepo),new RubyBuiltInType());
    	TemporaryFile.reset();
		langProcessor = new RubyProcessor();
	}
	
	public FileParser createFileParser(String src) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		return new JRubyFileParser(src,entityRepo, executor,new IncludedFileLocator(includePaths()), inferer, this);
	}

	private List<String> includePaths() {
		return new ArrayList<>();
	}

	public Set<UnsolvedBindings> resolveAllBindings() {
		return inferer.resolveAllBindings(false, langProcessor);
	}
}
