package depends.extractor.cpp;

import java.util.ArrayList;

import depends.entity.Inferer;
import depends.entity.repo.EntityRepo;
import depends.extractor.cpp.cdt.CdtCppFileParser;
import depends.extractor.cpp.cdt.PreprocessorHandler;

public abstract class CppParserTest {
	protected EntityRepo repo;
	protected Inferer inferer;
    protected PreprocessorHandler preprocessorHandler;

	public void init() {
    	repo = new EntityRepo();
    	inferer = new Inferer(repo,new CppImportLookupStrategy(),new CppBuiltInType());
    	preprocessorHandler = new PreprocessorHandler(new ArrayList<>());
    }
	
	public CppFileParser createParser(String src) {
		return new  CdtCppFileParser(src,repo, preprocessorHandler,inferer );
	}
}
