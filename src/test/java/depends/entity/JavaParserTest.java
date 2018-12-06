package depends.entity;

import depends.entity.repo.EntityRepo;
import depends.extractor.java.JavaBuiltInType;
import depends.extractor.java.JavaFileParser;
import depends.extractor.java.JavaImportLookupStrategy;

public class JavaParserTest {
	protected  EntityRepo entityRepo ;
	protected Inferer inferer ;

	public void init() {
		entityRepo = new EntityRepo();
		inferer = new Inferer(entityRepo,new JavaImportLookupStrategy(),new JavaBuiltInType());
	}
	
	public JavaFileParser createParser(String src) {
		return new JavaFileParser(src,entityRepo, inferer);
	}
}
