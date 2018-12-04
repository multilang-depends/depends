package depends.extractor.java;

import java.util.ArrayList;
import java.util.List;

import depends.entity.Entity;
import depends.entity.TypeInfer.InferData;
import depends.entity.repo.EntityRepo;
import depends.entity.types.FileEntity;
import depends.entity.types.PackageEntity;
import depends.extractor.ImportLookupStrategy;
import depends.importtypes.Import;

public class JavaImportLookupStrategy implements ImportLookupStrategy{
	@Override
	public InferData lookupImportedType(String name, FileEntity fileEntity, EntityRepo repo) {
		//Java Strategy
		String importedString = fileEntity.importedSuffixMatch(name);
		if (importedString==null) return null;	
		return repo.getTypeEntityByFullName(importedString);
	}


	@Override
	public List<Entity> getImportedRelationEntities(List<Import> importedList, EntityRepo repo) {
		ArrayList<Entity> result = new ArrayList<>();
		for (Import importedItem:importedList) {
			Entity imported = repo.getEntity(importedItem.getContent());
			if (imported==null) continue;
			if (imported instanceof PackageEntity) { 
				//expand import of package to all classes under the package due to we dis-courage the behavior
				for (Entity child:imported.getChildren()) {
					result.add(child);
				}
			}else {
				result.add(imported);
			}
		}
		return result;
	}

	@Override
	public List<Entity> getImportedTypes(List<Import> importedList, EntityRepo repo) {
		return getImportedRelationEntities(importedList,repo);
	}

	@Override
	public List<Entity> getImportedFiles(List<Import> importedList, EntityRepo repo) {
		return new ArrayList<Entity>();
	}



}
