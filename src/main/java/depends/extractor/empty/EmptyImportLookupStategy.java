package depends.extractor.empty;

import java.util.ArrayList;
import java.util.List;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.repo.EntityRepo;
import depends.importtypes.Import;
import depends.relations.ImportLookupStrategy;
import depends.relations.Inferer;

public class EmptyImportLookupStategy implements ImportLookupStrategy {

	@Override
	public Entity lookupImportedType(String name, FileEntity fileEntity, EntityRepo repo, Inferer inferer) {
		return null;
	}

	@Override
	public List<Entity> getImportedRelationEntities(List<Import> importedNames, EntityRepo repo) {
		return new ArrayList<Entity>();
	}

	@Override
	public List<Entity> getImportedTypes(List<Import> importedNames, EntityRepo repo) {
		return new ArrayList<Entity>();
	}

	@Override
	public List<Entity> getImportedFiles(List<Import> importedNames, EntityRepo repo) {
		return new ArrayList<Entity>();
	}

}
