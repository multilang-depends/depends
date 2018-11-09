package depends.extractor.cpp.cdt;

import depends.entity.repo.EntityRepo;

public abstract class CdtParserTest {
	EntityRepo repo = new EntityRepo();
    FileIndex fileIndex = new FileIndex();
    public void init() {
    	repo = new EntityRepo();
    	fileIndex = new FileIndex();
    }
}
