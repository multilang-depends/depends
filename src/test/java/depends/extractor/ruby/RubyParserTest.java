package depends.extractor.ruby;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import depends.entity.repo.EntityRepo;
import depends.relations.Inferer;

public abstract class RubyParserTest {
	protected  EntityRepo entityRepo ;
	protected Inferer inferer ;

	public void init() {
		entityRepo = new EntityRepo();
		inferer = new Inferer(entityRepo,new RubyImportLookupStrategy(),new RubyBuiltInType());
	}
	
	public RubyFileParser createParser(String src) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		return new RubyFileParser(src,entityRepo, executor,new IncludedFileLocator(includePaths()), inferer);
	}

	private List<String> includePaths() {
		return new ArrayList<>();
	}
}
