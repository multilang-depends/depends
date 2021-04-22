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

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.UnsolvedBindings;
import depends.importtypes.Import;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public abstract class ImportLookupStrategy {
	/**
	 * How to find the corresponding entity out of current scope
	 * 
	 * @param name - the entity name
	 * @param fileEntity - the current file
	 * @return the founded entity, or null if not found.
	 */
	public abstract Entity lookupImportedType(String name, FileEntity fileEntity);
	
	/**
	 * The lanaguage specific import relation computation. For example, 
	 * In C/CPP, it is calculated by the file name
	 * In Java, it is calculated by the imported types
	 * @param importedNames - the original name of the import relation
	 * @return the corresponding entities related with importedNames
	 */
	public abstract Collection<Entity> getImportedRelationEntities(List<Import> importedNames);
	
	/**
	 * The types been imported
	 * @param importedNames
	 * @return
	 */
	public abstract Collection<Entity> getImportedTypes(List<Import> importedNames,Set<UnsolvedBindings> unsolvedSymbols);
	
	/**
	 * The files been imported
	 * @param importedNames
	 * @return
	 */
	public abstract Collection<Entity> getImportedFiles(List<Import> importedNames);

	/** Whether support global name lookup
	 * for java, it should be true;
	 * for most of langs, it should be false;
	 */
	public abstract boolean supportGlobalNameLookup();

	public void setBindingResolver(IBindingResolver bindingResolver){
		this.bindingResolver = bindingResolver;
	}
	public  ImportLookupStrategy(EntityRepo repo){
		this.repo = repo;
	}
	protected EntityRepo repo;
	protected IBindingResolver bindingResolver;
}
