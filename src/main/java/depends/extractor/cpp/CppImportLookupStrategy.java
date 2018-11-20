package depends.extractor.cpp;

import java.util.HashSet;

import depends.entity.Entity;
import depends.entity.repo.EntityRepo;
import depends.entity.types.FileEntity;
import depends.extractor.ImportLookupStrategy;

public class CppImportLookupStrategy implements ImportLookupStrategy {

	@Override
	public Entity lookupImportedType(String name, FileEntity fileEntity, EntityRepo repo, boolean typeOnly) {
		HashSet<String> fileSet = new HashSet<>();
		foundIncludedFiles(fileSet, fileEntity,repo);
		for (String file:fileSet) {
			Entity importedItem = repo.getEntity(file);
			if (importedItem instanceof FileEntity) {
				FileEntity importedFile = (FileEntity) repo.getEntity(file);
				if (importedFile==null) continue;
				Entity entity = repo.lookupTypes(importedFile,name,typeOnly,false);
				if (entity!=null) return entity;
			}else {
				String importedString = fileEntity.getImport(name);
				if (importedString==null) continue;	
				return repo.getTypeEntityByFullName(importedString);
			}
		}		
		return null;
	}

	private void foundIncludedFiles(HashSet<String> fileSet, FileEntity fileEntity, EntityRepo repo) {
		for (String file:fileEntity.imports()) {
			if (fileSet.contains(file)) continue;
			fileSet.add(file);
			Entity f = repo.getEntity(file);
			if (f==null ) continue;
			if (!(f instanceof FileEntity)) continue;
			foundIncludedFiles(fileSet,(FileEntity)f,repo);
		}
	}

}
