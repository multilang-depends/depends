package depends.extractor.cpp;

import java.util.ArrayList;

import depends.entity.repo.EntityRepo;
import depends.extractor.cpp.cdt.PreprocessorHandler;

public abstract class CppParserTest {
	protected EntityRepo repo = new EntityRepo();
    protected PreprocessorHandler preprocessorHandler;

	public void init() {
    	repo = new EntityRepo();
    	repo.setImportLookupStrategy(new CppImportLookupStrategy());
    	preprocessorHandler = new PreprocessorHandler(new ArrayList<>());
    }
}
