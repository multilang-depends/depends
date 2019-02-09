package depends.relations;

import java.util.List;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.repo.EntityRepo;
import depends.importtypes.Import;

public interface ImportLookupStrategy {
	/**
	 * How to find the corresponding entity out of current scope
	 * 
	 * @param name - the entity name
	 * @param fileEntity - the current file
	 * @param repo - the whole entity repo, which could be used when necessary
	 * @param inferer - the inferer object, which could be used when necessary
	 * @return the founded entity, or null if not found.
	 */
	Entity lookupImportedType(String name, FileEntity fileEntity, EntityRepo repo, Inferer inferer);
	
	/**
	 * The lanaguage specific import relation computation. For example, 
	 * In C/CPP, it is calculated by the file name
	 * In Java, it is calculated by the imported types
	 * @param importedNames - the original name of the import relation
	 * @param repo - entity repo
	 * @return the corresponding entities related with importedNames
	 */
	List<Entity> getImportedRelationEntities(List<Import> importedNames, EntityRepo repo);
	
	/**
	 * The types been imported
	 * @param importedNames
	 * @param repo
	 * @return
	 */
	List<Entity> getImportedTypes(List<Import> importedNames, EntityRepo repo);
	
	/**
	 * The files been imported
	 * @param importedNames
	 * @param repo
	 * @return
	 */
	List<Entity> getImportedFiles(List<Import> importedNames, EntityRepo repo);
}
