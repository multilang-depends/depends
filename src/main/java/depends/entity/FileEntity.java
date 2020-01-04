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

package depends.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import depends.importtypes.Import;
import depends.relations.Inferer;

public class FileEntity extends TypeEntity {
	private List<Import> importedNames = new ArrayList<>();
	private boolean isInProjectScope = false;
	private Collection<Entity> importedRelationEntities = new ArrayList<>();
	private Collection<Entity> importedFiles = new ArrayList<>();
	private Collection<Entity> importedTypes = new ArrayList<>();
	private List<TypeEntity> declaredTypes = new ArrayList<>();
	private ImportedFileCollector importedFileCollector = null;
	public FileEntity() {}

	public FileEntity(String fullName, int fileId, boolean isInProjectScope) {
		super(GenericName.build(fullName), null, fileId);
		setQualifiedName(fullName);
		this.isInProjectScope = isInProjectScope;
	}

	public FileEntity(String fullName, int fileId) {
		this(fullName, fileId, true);
	}

	public void addImport(Import imported) {
		if (!importedNames.contains(imported))
			importedNames.add(imported);
	}
	
	/**
	 * To match the imported name by suffix
	 * for example:
	 *    import a.b.ClassX;
	 * the b.ClassX, ClassX , a.b.classX should be matched
	 * @param lastName
	 * @return
	 */
	public String importedSuffixMatch(String lastName) {
		if (!lastName.startsWith("."))
			lastName = "." + lastName;
		for (Entity imported : this.importedTypes) {
			String name = imported.getQualifiedName(true);
			if (!name.startsWith("."))
				name = "." + name;
			if (imported.getQualifiedName(true).endsWith(lastName))
				return imported.getQualifiedName(true);
		}
		return null;
	}
	

	@Override
	public String getQualifiedName(boolean overrideFileWithPackage) {
		if (!overrideFileWithPackage) {
			return super.getQualifiedName();
		}
		if (this.getParent() == null) {
			return "";
		}
		if (this.getParent() instanceof PackageEntity)
			return this.getParent().getQualifiedName();
		else
			return super.getQualifiedName();
	}

	@Override
	public void inferLocalLevelEntities(Inferer inferer) {
		this.importedRelationEntities = inferer.getImportedRelationEntities(importedNames);
		this.importedTypes = inferer.getImportedTypes(importedNames,this);
		this.importedFiles = inferer.getImportedFiles(importedNames);

		super.inferLocalLevelEntities(inferer);
	}

	public boolean isInProjectScope() {
		return isInProjectScope;
	}

	public void setInProjectScope(boolean isInProjectScope) {
		this.isInProjectScope = isInProjectScope;
	}

	public Collection<Entity> getImportedRelationEntities() {
		return importedRelationEntities;
	}

	public Collection<Entity> getImportedFiles() {
		return importedFiles;
	}

	public Collection<Entity> getImportedTypes() {
		return importedTypes;
	}

	public List<TypeEntity> getDeclaredTypes() {
		return this.declaredTypes;
	}

	public void addType(TypeEntity currentTypeEntity) {
		this.declaredTypes.add(currentTypeEntity);
	}

	public Set<FileEntity> getImportedFilesInAllLevel() {
		if (importedFileCollector==null)
			importedFileCollector = new ImportedFileCollector(this);

		return importedFileCollector.getFiles();
	}

	public List<Import> getImportedNames() {
		return importedNames;
	}

	public void cacheAllExpressions() {
		this.cacheChildExpressions();
	}


	@Override
	public Entity getByName(String name, HashSet<Entity> searched) {
		Entity entity = super.getByName(name, searched);
		if (entity!=null) return entity;
		for (TypeEntity type:getDeclaredTypes()) {
			if (type.getRawName().getName().equals(name)||
				suffixMatch(name,type.getQualifiedName())) {
				return type;
			}
		}
		return null;
	}
	
	private boolean suffixMatch(String name, String qualifiedName) {
		if (qualifiedName.contains(".")) {
			if (!name.startsWith(".")) name = "." +name;
			return qualifiedName.endsWith(name);
		}
		else {
			return qualifiedName.equals(name);
		}
	}

}
