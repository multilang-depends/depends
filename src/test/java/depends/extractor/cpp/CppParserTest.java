package depends.extractor.cpp;

import java.util.ArrayList;

import depends.entity.repo.EntityRepo;
import depends.entity.repo.InMemoryEntityRepo;

import depends.extractor.ParserTest;
import depends.extractor.cpp.cdt.CdtCppFileParser;
import depends.extractor.cpp.cdt.PreprocessorHandler;
import depends.relations.Inferer;
import depends.util.TemporaryFile;

public abstract class CppParserTest extends ParserTest{
	protected EntityRepo repo;
	protected Inferer inferer;
    protected PreprocessorHandler preprocessorHandler;

	public void init() {
    	repo = new InMemoryEntityRepo();
    	inferer = new Inferer(repo,new CppImportLookupStrategy(),new CppBuiltInType(),false);
    	preprocessorHandler = new PreprocessorHandler(new ArrayList<>());
    	TemporaryFile.reset();
    }
	
	public CppFileParser createParser(String src) {
		return new  CdtCppFileParser(src,repo, preprocessorHandler,inferer );
	}
}
