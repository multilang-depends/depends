package depends.extractor.python;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.ruby.IncludedFileLocator;
import depends.importtypes.Import;
import depends.relations.ImportLookupStrategy;
import depends.relations.Inferer;
import depends.util.FileUtil;

public class PythonImportLookupStrategy implements ImportLookupStrategy {

	private IncludedFileLocator includeFileLocator;

	public PythonImportLookupStrategy() {
	}

	@Override
	public Entity lookupImportedType(String name, FileEntity fileEntity, EntityRepo repo, Inferer inferer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Entity> getImportedRelationEntities(List<Import> importedNames, EntityRepo repo) {
		ArrayList<Entity> result = new ArrayList<>();
		for (Import importedItem:importedNames) {
			if (importedItem instanceof NameAliasImport) {
				String importedName = importedItem.getContent();
				importedName = importedName.replace(".", File.separator);
				String fullName = includeFileLocator.uniqFileName(null, importedName);
				if (fullName==null) {
					fullName = includeFileLocator.uniqFileName(null, importedName+".py");
				}
				if (fullName!=null) {
					Entity imported = repo.getEntity(fullName);
					if (imported==null) continue;
					result.add(imported);
				}
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

	public void setLocator(IncludedFileLocator includeFileLocator) {
		this.includeFileLocator = includeFileLocator;
	}

}
