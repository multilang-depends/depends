package depends.extractor.java;

import depends.entity.repo.EntityRepo;
import depends.entity.repo.InMemoryEntityRepo;
import depends.extractor.ParserTest;
import depends.relations.Inferer;
import multilang.depends.util.file.TemporaryFile;

public abstract class JavaParserTest  extends ParserTest{
	protected  EntityRepo entityRepo ;
	protected Inferer inferer ;

	public void init() {
		entityRepo = new InMemoryEntityRepo();
		inferer = new Inferer(entityRepo,new JavaImportLookupStrategy(),new JavaBuiltInType(),false);
    	TemporaryFile.reset();
	}
	
	public JavaFileParser createParser(String src) {
		return new JavaFileParser(src,entityRepo, inferer);
	}
}
