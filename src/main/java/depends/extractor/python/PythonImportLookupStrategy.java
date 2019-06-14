package depends.extractor.python;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

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
		Collection<Entity> files = getImportedFiles(importedNames,repo);
		Collection<Entity> filescontainsTypes = 	this.getImportedTypes(importedNames, repo).stream().map(e->{
			return e.getAncestorOfType(FileEntity.class);
		}).filter(new Predicate<Entity>() {

			@Override
			public boolean test(Entity t) {
				return t!=null;
			}
		}).collect(Collectors.toSet());
		
		
		return CollectionUtils.union(files, filescontainsTypes);
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
	
	@Override
	public boolean supportGlobalNameLookup() {
		return false;
	}
}
