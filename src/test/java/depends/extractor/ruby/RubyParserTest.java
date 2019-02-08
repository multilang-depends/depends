package depends.extractor.ruby;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
import depends.entity.repo.EntityRepo;
import depends.extractor.FileParser;
import depends.extractor.ParserCreator;
import depends.extractor.ruby.jruby.JRubyFileParser;
import depends.relations.Inferer;
import depends.relations.Relation;
public abstract class RubyParserTest implements ParserCreator{
	protected  EntityRepo entityRepo ;
	protected Inferer inferer ;

	public void init() {
		entityRepo = new EntityRepo();
		inferer = new Inferer(entityRepo,new RubyImportLookupStrategy(),new RubyBuiltInType());
	}
	
	public FileParser createFileParser(String src) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		return new JRubyFileParser(src,entityRepo, executor,new IncludedFileLocator(includePaths()), inferer, this);
	}

	private List<String> includePaths() {
		return new ArrayList<>();
	}
	
	protected void assertContainsRelation(Entity inEntity, String dependencyType, String dependedEntityFullName) {
		Relation relation = null;
		for (Relation r:inEntity.getRelations()) {
			if (r.getType().equals(dependencyType)) {
				relation = r;
				if (r.getEntity().getQualifiedName().equals(dependedEntityFullName))
					return;
			}
		}
		if (relation==null) {
			fail("cannot found relation type of "+ dependencyType);
		}else {
			fail("cannot found relation type of " + dependencyType + " to entity " + dependedEntityFullName);
		}
	}
}
