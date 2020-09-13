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

package depends.extractor.golang;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.PackageEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.UnsolvedBindings;
import depends.importtypes.Import;
import depends.relations.ImportLookupStrategy;
import depends.relations.Inferer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GoImportLookupStrategy implements ImportLookupStrategy{
	@Override
	public Entity lookupImportedType(String name, FileEntity fileEntity, EntityRepo repo, Inferer inferer) {
		//Java Strategy
		String importedString = fileEntity.importedSuffixMatch(name);
		if (importedString==null) return null;	
		return repo.getEntity(importedString);
	}


	@Override
	public List<Entity> getImportedRelationEntities(List<Import> importedList, EntityRepo repo) {
		ArrayList<Entity> result = new ArrayList<>();
		for (Import importedItem:importedList) {
			Entity imported = repo.getEntity(importedItem.getContent());
			if (imported==null) continue;
			if (imported instanceof PackageEntity) { 
				//ignore wildcard import relation
			}else {
				result.add(imported);
			}
		}
		return result;
	}

	@Override
	public List<Entity> getImportedTypes(List<Import> importedList, EntityRepo repo, Set<UnsolvedBindings> unsolvedBindings) {
		ArrayList<Entity> result = new ArrayList<>();
		for (Import importedItem:importedList) {
			Entity imported = repo.getEntity(importedItem.getContent());
			if (imported==null) {
				unsolvedBindings.add(new UnsolvedBindings(importedItem.getContent(),null));
				continue;
			}
			if (imported instanceof PackageEntity) { 
				//expand import of package to all classes under the package due to we dis-courage the behavior
				for (Entity child:imported.getChildren()) {
					if (child instanceof FileEntity) {
						child.getChildren().forEach(item->result.add(item));
					}else {
						result.add(child);
					}
				}
			}else {
				result.add(imported);
			}
		}
		return result;
	}

	@Override
	public List<Entity> getImportedFiles(List<Import> importedList, EntityRepo repo) {
		return new ArrayList<Entity>();
	}


	@Override
	public boolean supportGlobalNameLookup() {
		return true;
	}

}
