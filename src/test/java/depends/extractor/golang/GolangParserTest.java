package depends.extractor.golang;

import depends.entity.repo.EntityRepo;
import depends.entity.repo.InMemoryEntityRepo;
import depends.extractor.ParserTest;
import depends.relations.Inferer;
import multilang.depends.util.file.TemporaryFile;

public abstract class GolangParserTest extends ParserTest{
	protected  EntityRepo entityRepo ;
	protected Inferer inferer ;

	public void init() {
		entityRepo = new InMemoryEntityRepo();
		inferer = new Inferer(entityRepo,new GoImportLookupStrategy(entityRepo),new GoBuiltInType(),false);
    	TemporaryFile.reset();
	}
	
	public GoFileParser createParser(String src) {
		return new GoFileParser(src,entityRepo, inferer);
	}
}
