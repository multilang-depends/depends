package depends.extractor.python;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	public Collection<Entity> getImportedRelationEntities(List<Import> importedNames, EntityRepo repo) {
		return getImportedFiles(importedNames,repo);
	}

	@Override
	public Collection<Entity> getImportedTypes(List<Import> importedNames, EntityRepo repo) {
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
	public Collection<Entity> getImportedFiles(List<Import> importedNames, EntityRepo repo) {
		Set<Entity> files = new HashSet<>();
		Collection<Entity> entities = getImportedTypes(importedNames,repo);
		for (Entity entity:entities) {
			if (entity instanceof FileEntity)
				files.add(entity);
			else
				files.add(entity.getAncestorOfType(FileEntity.class));
		}
		return files;
	}
}
