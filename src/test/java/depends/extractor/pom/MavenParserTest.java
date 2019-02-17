package depends.extractor.pom;

import java.util.ArrayList;

import depends.entity.repo.EntityRepo;
import depends.extractor.empty.EmptyBuiltInType;
import depends.extractor.empty.EmptyImportLookupStategy;
import depends.extractor.pom.PomFileParser;
import depends.relations.Inferer;

public abstract class MavenParserTest {
	protected EntityRepo repo;
	protected Inferer inferer;

	public void init() {
    	repo = new EntityRepo();
    	inferer = new Inferer(repo,new EmptyImportLookupStategy(),new EmptyBuiltInType(),false);
    }
	
	public PomFileParser createParser(String src) {
		return new  PomFileParser(src,repo,new ArrayList<>(),null);
	}
}
