package depends.extractor.cpp;

import depends.entity.Inferer;
import depends.entity.repo.EntityRepo;
import depends.extractor.BuiltInType;

public abstract class CppFileParser implements depends.extractor.FileParser {
	protected String fileFullPath;
	protected EntityRepo entityRepo;
	public CppFileParser(String fileFullPath, EntityRepo entityRepo, Inferer inferer) {
		this.fileFullPath = fileFullPath;
		this.entityRepo = entityRepo;
	}
	
}
