package depends.extractor;

import java.util.List;

import depends.entity.Entity;
import depends.entity.TypeInfer.InferData;
import depends.entity.repo.EntityRepo;
import depends.entity.types.FileEntity;
import depends.importtypes.Import;

public interface ImportLookupStrategy {
	InferData lookupImportedType(String name, FileEntity fileEntity, EntityRepo repo);
	List<Entity> getImportedRelationEntities(List<Import> importedNames, EntityRepo repo);
	List<Entity> getImportedTypes(List<Import> importedNames, EntityRepo repo);
	List<Entity> getImportedFiles(List<Import> importedNames, EntityRepo repo);
}
