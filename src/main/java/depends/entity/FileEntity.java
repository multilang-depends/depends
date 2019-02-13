package depends.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import depends.importtypes.Import;
import depends.relations.Inferer;

public class FileEntity extends ContainerEntity {
	private List<Import> importedNames = new ArrayList<>();
	private boolean isInProjectScope = false;
	private List<Entity> importedRelationEntities = new ArrayList<>();
	private List<Entity> importedFiles = new ArrayList<>();
	private List<Entity> importedTypes = new ArrayList<>();
	private List<TypeEntity> declaredTypes = new ArrayList<>();
	private ImportedFileCollector importedFileCollector = null;
	public FileEntity(String fullName, int fileId, boolean isInProjectScope) {
		super(fullName, null, fileId);
		setQualifiedName(fullName);
		this.isInProjectScope = isInProjectScope;
	}

	public FileEntity(String fullName, int fileId) {
		this(fullName, fileId, true);
	}

	public void addImport(Import imported) {
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
		this.importedTypes = inferer.getImportedTypes(importedNames);
		this.importedFiles = inferer.getImportedFiles(importedNames);
		super.inferLocalLevelEntities(inferer);
	}

	public boolean isInProjectScope() {
		return isInProjectScope;
	}

	public void setInProjectScope(boolean isInProjectScope) {
		this.isInProjectScope = isInProjectScope;
	}

	public List<Entity> getImportedRelationEntities() {
		return importedRelationEntities;
	}

	public List<Entity> getImportedFiles() {
		return importedFiles;
	}

	public List<Entity> getImportedTypes() {
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

}
