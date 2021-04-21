package depends.extractor.python;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.UnsolvedBindings;
import depends.importtypes.FileImport;
import depends.importtypes.Import;
import depends.relations.ImportLookupStrategy;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PythonImportLookupStrategy extends ImportLookupStrategy {

	public PythonImportLookupStrategy(EntityRepo repo) {
		super(repo);
	}

	@Override
	public Entity lookupImportedType(String name, FileEntity fileEntity) {
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
	public Collection<Entity> getImportedRelationEntities(List<Import> importedNames) {
		Collection<Entity> files = getImportedFiles(importedNames);
		Collection<Entity> filescontainsTypes = this.getImportedTypes(importedNames, new HashSet<>()).stream().map(e->{
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
	public Collection<Entity> getImportedTypes(List<Import> importedNames, Set<UnsolvedBindings> unsolvedBindings) {
		Set<Entity> result = new HashSet<>();
		for (Import importedItem:importedNames) {
			if (importedItem instanceof NameAliasImport) {
				NameAliasImport nameAliasImport = (NameAliasImport)importedItem;
				Entity imported = nameAliasImport.getEntity();
				if (imported==null) {
					unsolvedBindings.add(new UnsolvedBindings(importedItem.getContent(),null));
					continue;
				}				
				result.add(imported);
			}
			if (importedItem instanceof FileImport) {
				FileImport fileImport = (FileImport)importedItem;
				Entity imported = (repo.getEntity(fileImport.getContent()));
				if (imported!=null) {
					result.add(imported);
				}
			}
		}
		return result;
	}

	@Override
	public Collection<Entity> getImportedFiles(List<Import> importedNames) {
		Set<Entity> files = new HashSet<>();
		Collection<Entity> entities = getImportedTypes(importedNames,new HashSet<>());
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
