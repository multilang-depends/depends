package depends.extractor.java;

import depends.entity.Entity;
import depends.entity.repo.EntityRepo;
import depends.entity.types.FileEntity;
import depends.extractor.ImportLookupStrategy;

public class JavaImportLookupStrategy implements ImportLookupStrategy{
	@Override
	public Entity lookupImportedType(String name, FileEntity fileEntity, EntityRepo repo, boolean typeOnly) {
		//Java Strategy
		String importedString = fileEntity.getImport(name);
		if (importedString==null) return null;	
		return repo.getTypeEntityByFullName(importedString);
	}

}
