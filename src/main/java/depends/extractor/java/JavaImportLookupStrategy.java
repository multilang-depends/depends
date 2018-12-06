package depends.extractor.java;

import java.util.ArrayList;
import java.util.List;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.PackageEntity;
import depends.entity.repo.EntityRepo;
import depends.importtypes.Import;
import depends.relations.ImportLookupStrategy;
import depends.relations.Inferer;

public class JavaImportLookupStrategy implements ImportLookupStrategy{
	@Override
	public Entity lookupImportedType(String name, FileEntity fileEntity, EntityRepo repo, Inferer inferer) {
		//Java Strategy
		String importedString = fileEntity.importedSuffixMatch(name);
		if (importedString==null) return null;	
		return repo.getEntity(importedString);
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
