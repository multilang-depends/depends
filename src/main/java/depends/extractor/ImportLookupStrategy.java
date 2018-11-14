package depends.extractor;

import depends.entity.Entity;
import depends.entity.repo.EntityRepo;
import depends.entity.types.FileEntity;

public interface ImportLookupStrategy {
	Entity lookupImportedType(String name, FileEntity fileEntity, EntityRepo repo, boolean typeOnly);
}
