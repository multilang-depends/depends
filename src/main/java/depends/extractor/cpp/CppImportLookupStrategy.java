package depends.extractor.cpp;

import depends.entity.Entity;
import depends.entity.repo.EntityRepo;
import depends.entity.types.FileEntity;
import depends.extractor.ImportLookupStrategy;

public class CppImportLookupStrategy implements ImportLookupStrategy {

	@Override
	public Entity lookupImportedType(String name, FileEntity fileEntity, EntityRepo repo, boolean typeOnly) {
		for (String file:fileEntity.imports()) {
			FileEntity importedFile = (FileEntity) repo.getEntity(file);
			if (importedFile==null) continue;
			Entity entity = repo.lookupTypes(importedFile,name,typeOnly);
			if (entity!=null) return entity;
		}		
		return null;
	}

}
