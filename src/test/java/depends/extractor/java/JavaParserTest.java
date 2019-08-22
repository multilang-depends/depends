package depends.extractor.java;

import depends.entity.repo.EntityRepo;
import depends.entity.repo.InMemoryEntityRepo;
import depends.entity.repo.Neo4jEntityRepo;
import depends.extractor.ParserTest;
import depends.extractor.java.JavaBuiltInType;
import depends.extractor.java.JavaFileParser;
import depends.extractor.java.JavaImportLookupStrategy;
import depends.relations.Inferer;

public abstract class JavaParserTest  extends ParserTest{
	protected  EntityRepo entityRepo ;
	protected Inferer inferer ;

	public void init() {
		entityRepo = new InMemoryEntityRepo();
		inferer = new Inferer(entityRepo,new JavaImportLookupStrategy(),new JavaBuiltInType(),false);
	}
	
	public JavaFileParser createParser(String src) {
		return new JavaFileParser(src,entityRepo, inferer);
	}
}
