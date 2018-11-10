package depends.extractor.cpp;

import depends.entity.repo.EntityRepo;
import depends.extractor.cpp.cdt.FileIndex;

public abstract class CppParserTest {
	protected EntityRepo repo = new EntityRepo();
    protected FileIndex fileIndex = new FileIndex();
    public void init() {
    	repo = new EntityRepo();
    	fileIndex = new FileIndex();
    }
}
