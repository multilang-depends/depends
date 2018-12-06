package depends.entity.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import depends.entity.ContainerEntity;
import depends.entity.Entity;
import depends.entity.Inferer;
import depends.importtypes.ExactMatchImport;
import depends.importtypes.FileImport;
import depends.importtypes.Import;
import depends.importtypes.PackageWildCardImport;

public class FileEntity extends ContainerEntity {
	private List<Import> importedNames = new ArrayList<>();
	private boolean isInProjectScope = false;
	private List<Entity> importedRelationEntities = new ArrayList<>();
	private List<Entity> importedFiles = new ArrayList<>();
	private List<Entity> importedTypes = new ArrayList<>();

	public FileEntity(String fullName, int fileId, boolean isInProjectScope) {
		super(fullName, null, fileId);
		setQualifiedName(fullName);
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
			String name = imported.getQualifiedName();
			if (!name.startsWith("."))
				name = "." + name;
			if (imported.getQualifiedName().endsWith(lastName))
				return imported.getQualifiedName();
		}
		return null;
	}
	
	@Override
	public String getQualifiedName() {
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

}
