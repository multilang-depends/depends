package depends.extractor.python;

import java.util.List;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.repo.EntityRepo;
import depends.importtypes.Import;
import depends.relations.ImportLookupStrategy;
import depends.relations.Inferer;

public class PythonImportLookupStrategy implements ImportLookupStrategy {

	@Override
	public Entity lookupImportedType(String name, FileEntity fileEntity, EntityRepo repo, Inferer inferer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Entity> getImportedRelationEntities(List<Import> importedNames, EntityRepo repo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Entity> getImportedTypes(List<Import> importedNames, EntityRepo repo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Entity> getImportedFiles(List<Import> importedNames, EntityRepo repo) {
		// TODO Auto-generated method stub
		return null;
	}

}
