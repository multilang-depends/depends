package depends.extractor.ruby;

import java.util.ArrayList;
import java.util.List;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.repo.EntityRepo;
import depends.importtypes.FileImport;
import depends.importtypes.Import;
import depends.relations.ImportLookupStrategy;
import depends.relations.Inferer;

public class RubyImportLookupStrategy implements ImportLookupStrategy {

	@Override
	public Entity lookupImportedType(String name, FileEntity fileEntity, EntityRepo repo, Inferer inferer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Entity> getImportedRelationEntities(List<Import> importedList, EntityRepo repo) {
		ArrayList<Entity> result = new ArrayList<>();
		for (Import importedItem:importedList) {
			if (importedItem instanceof FileImport) {
				Entity imported = repo.getEntity(importedItem.getContent());
				if (imported==null) continue;
				result.add(imported);
			}
		}
		return result;
	}

	@Override
	public List<Entity> getImportedTypes(List<Import> importedNames, EntityRepo repo) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public List<Entity> getImportedFiles(List<Import> importedList, EntityRepo repo) {
		return getImportedRelationEntities(importedList,repo);
	}

}
