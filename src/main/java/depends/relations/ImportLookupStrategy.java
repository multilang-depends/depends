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

package depends.relations;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.UnsolvedBindings;
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
	Collection<Entity> getImportedRelationEntities(List<Import> importedNames, EntityRepo repo);
	
	/**
	 * The types been imported
	 * @param importedNames
	 * @param repo
	 * @return
	 */
	Collection<Entity> getImportedTypes(List<Import> importedNames, EntityRepo repo, Set<UnsolvedBindings> unsolvedSymbols);
	
	/**
	 * The files been imported
	 * @param importedNames
	 * @param repo
	 * @return
	 */
	Collection<Entity> getImportedFiles(List<Import> importedNames, EntityRepo repo);

	/** Whether support global name lookup
	 * for java, it should be true;
	 * for most of langs, it should be false;
	 */
	boolean supportGlobalNameLookup();

}
