package depends.extractor.cpp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import depends.entity.Entity;
import depends.entity.TypeInfer.InferData;
import depends.entity.repo.EntityRepo;
import depends.entity.types.FileEntity;
import depends.entity.types.TypeEntity;
import depends.extractor.ImportLookupStrategy;
import depends.importtypes.FileImport;
import depends.importtypes.Import;

public class CppImportLookupStrategy implements ImportLookupStrategy {
	@Override
	public InferData lookupImportedType(String name, FileEntity fileEntity, EntityRepo repo) {
		String importedString = fileEntity.importedSuffixMatch(name);
		if (importedString!=null) {
			 InferData r = repo.getTypeEntityByFullName(importedString);
			if (r!=null) return r;
		}
		
		HashSet<String> fileSet = new HashSet<>();
		foundIncludedFiles(fileSet, fileEntity.getImportedFiles(),repo);
		
		for (String file:fileSet) {
			Entity importedItem = repo.getEntity(file);
			if (importedItem instanceof FileEntity) {
				FileEntity importedFile = (FileEntity) repo.getEntity(file);
				if (importedFile==null) continue;
				 InferData entity = repo.inferTypeWithoutImportSearch(importedFile,name);
				if (entity!=null) return entity;
				 List<Entity> namespaces = fileEntity.getImportedTypes();
				for (Entity ns:namespaces) {
					String nameWithPrefix = ns.getQualifiedName() + "." + name;
					entity = repo.inferTypeWithoutImportSearch(importedFile,nameWithPrefix);
					if (entity!=null) return entity;				
				}
			}
		}		
		return null;
	}

	private void foundIncludedFiles(HashSet<String> fileSet, List<Entity> importedFiles, EntityRepo repo) {
		for (Entity file:importedFiles) {
			if (file==null ) continue;
			if (!(file instanceof FileEntity)) continue;
			if (fileSet.contains(file.getRawName())) continue;
			fileSet.add(file.getRawName());
			foundIncludedFiles(fileSet,((FileEntity)file).getImportedFiles(),repo);
		}
	}
	
	
	@Override
	public List<Entity> getImportedRelationEntities(List<Import> importedList, EntityRepo repo) {
		ArrayList<Entity> result = new ArrayList<>();
		for (Import importedItem:importedList) {
			if (importedItem instanceof FileImport) {
				Entity imported = repo.getEntity(importedItem.getContent());
				if (imported==null) continue;
				result.add(imported);
			}
		}
		return result;
	}

	@Override
	public List<Entity> getImportedTypes(List<Import> importedList, EntityRepo repo) {
		ArrayList<Entity> result = new ArrayList<>();
		for (Import importedItem:importedList) {
			if (!(importedItem instanceof FileImport)) {
				Entity imported = repo.getEntity(importedItem.getContent());
				if (imported==null) continue;
				result.add(imported);
			}
		}
		return result;
	}

	@Override
	public List<Entity> getImportedFiles(List<Import> importedList, EntityRepo repo) {
		return getImportedRelationEntities(importedList,repo);
	}

}
