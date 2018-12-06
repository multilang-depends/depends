package depends.extractor;

import java.util.List;

import depends.entity.Entity;
import depends.entity.Inferer;
import depends.entity.repo.EntityRepo;
import depends.entity.types.FileEntity;
import depends.importtypes.Import;

public interface ImportLookupStrategy {
	Entity lookupImportedType(String name, FileEntity fileEntity, EntityRepo repo, Inferer inferer);
	List<Entity> getImportedRelationEntities(List<Import> importedNames, EntityRepo repo);
	List<Entity> getImportedTypes(List<Import> importedNames, EntityRepo repo);
	List<Entity> getImportedFiles(List<Import> importedNames, EntityRepo repo);
}
