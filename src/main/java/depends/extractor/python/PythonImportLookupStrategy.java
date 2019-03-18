package depends.extractor.python;

import java.util.ArrayList;
import java.util.List;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.repo.EntityRepo;
import depends.importtypes.Import;
import depends.relations.ImportLookupStrategy;
import depends.relations.Inferer;

public class PythonImportLookupStrategy implements ImportLookupStrategy {
	public PythonImportLookupStrategy() {
	}

	@Override
	public Entity lookupImportedType(String name, FileEntity fileEntity, EntityRepo repo, Inferer inferer) {
		List<Import> importedNames = fileEntity.getImportedNames();
		for (Import importedItem:importedNames) {
			if (importedItem instanceof NameAliasImport) {
				NameAliasImport nameAliasImport = (NameAliasImport)importedItem;
				if (name.equals(nameAliasImport.getAlias())) {
					return nameAliasImport.getEntity();
				}
			}
		}
		return null;
	}

	@Override
	public List<Entity> getImportedRelationEntities(List<Import> importedNames, EntityRepo repo) {
		ArrayList<Entity> result = new ArrayList<>();
		for (Import importedItem:importedNames) {
			if (importedItem instanceof NameAliasImport) {
				NameAliasImport nameAliasImport = (NameAliasImport)importedItem;
				Entity imported = nameAliasImport.getEntity();
				if (imported==null) continue;
				result.add(imported);
			}
		}
		return result;
	}

	@Override
	public List<Entity> getImportedTypes(List<Import> importedNames, EntityRepo repo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Entity> getImportedFiles(List<Import> importedNames, EntityRepo repo) {
		return getImportedRelationEntities(importedNames,repo);
	}
}
