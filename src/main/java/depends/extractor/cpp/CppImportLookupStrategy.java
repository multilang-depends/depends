/*
MIT License

Copyright (c) 2018-2019 Gang ZHANG

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package depends.extractor.cpp;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.GenericName;
import depends.entity.repo.EntityRepo;
import depends.extractor.UnsolvedBindings;
import depends.importtypes.FileImport;
import depends.importtypes.Import;
import depends.relations.ImportLookupStrategy;

import java.util.*;

public class CppImportLookupStrategy extends ImportLookupStrategy {


	public CppImportLookupStrategy(EntityRepo repo){
		super(repo);
	}
	@Override
	public Entity lookupImportedType(String name, FileEntity fileEntity) {
		String importedString = fileEntity.importedSuffixMatch(name);
		if (importedString!=null) {
			Entity r = repo.getEntity(importedString);
			if (r!=null) return r;
		}
		
		HashSet<Integer> fileSet = getIncludedFiles(fileEntity);
		
		for (Integer file:fileSet) {
			Entity importedItem = repo.getEntity(file);
			if (importedItem instanceof FileEntity) {
				FileEntity importedFile = (FileEntity) repo.getEntity(file);
				if (importedFile==null) continue;
				 Entity entity = bindingResolver.resolveName(importedFile,GenericName.build(name), false);
				if (entity!=null) return entity;
				Collection<Entity> namespaces = fileEntity.getImportedTypes();
				for (Entity ns:namespaces) {
					String nameWithPrefix = ns.getQualifiedName() + "." + name;
					entity = bindingResolver.resolveName(importedFile,GenericName.build(nameWithPrefix), false);
					if (entity!=null) return entity;				
				}
			}	
		}		
		return null;
	}

	private Map<Integer, HashSet<Integer> > includedFiles  = new HashMap<>();
	private  HashSet<Integer> getIncludedFiles(FileEntity fileEntity) {

		if (includedFiles.containsKey(fileEntity.getId())) {
				return includedFiles.get(fileEntity.getId());
		}
		HashSet<Integer> fileSet = new HashSet<>();
		foundIncludedFiles(fileSet, fileEntity.getImportedFiles());
		includedFiles.put(fileEntity.getId(), fileSet);
		return fileSet;
	}

	private void foundIncludedFiles(HashSet<Integer> fileSet, Collection<Entity> importedFiles) {
		for (Entity file:importedFiles) {
			if (file==null ) continue;
			if (!(file instanceof FileEntity)) continue;
			if (fileSet.contains(file.getId())) continue;
			fileSet.add(file.getId());
			foundIncludedFiles(fileSet,((FileEntity)file).getImportedFiles());
		}
	}
	
	
	@Override
	public List<Entity> getImportedRelationEntities(List<Import> importedList) {
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
	public List<Entity> getImportedTypes(List<Import> importedList,  Set<UnsolvedBindings> unsolvedBindings) {
		ArrayList<Entity> result = new ArrayList<>();
		for (Import importedItem:importedList) {
			if (!(importedItem instanceof FileImport)) {
				Entity imported = repo.getEntity(importedItem.getContent());
				if (imported==null) {
					unsolvedBindings.add(new UnsolvedBindings(importedItem.getContent(),null));
					continue;
				}
				result.add(imported);
			}
		}
		return result;
	}

	@Override
	public List<Entity> getImportedFiles(List<Import> importedList) {
		return getImportedRelationEntities(importedList);
	}
	
	@Override
	public boolean supportGlobalNameLookup() {
		return false;
	}
}
