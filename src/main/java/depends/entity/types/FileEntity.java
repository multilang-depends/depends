package depends.entity.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import depends.entity.ContainerEntity;
import depends.entity.Entity;
import depends.entity.TypeInfer;

public class FileEntity extends ContainerEntity{
	private List<String> importedNames = new ArrayList<>();
	private ArrayList<Entity> resolvedImportedEntities = new ArrayList<>();

	public List<Entity> getResolvedImportedEntities() {
		return resolvedImportedEntities;
	}
	public FileEntity(String fullName, int fileId) {
		super(fullName, null,fileId);
		setQualifiedName(fullName);
	}
	
	/**
	 * 
	 * @param importedName could be className, package Name  in JAVA
	 *                     could be file in C/C++
	 */
	public void addImport(String importedName, boolean useFileAsImportedKey) {
        importedNames.add(importedName);
	}
	public String getImport(String lastName) {
		for(String importName:importedNames) {
			if (importName.endsWith(lastName))
				return importName;
		}
		return null;
	}
	@Override
	public String getQualifiedName() {
		if (this.getParent()==null){
				return "";
		}
		if (this.getParent() instanceof PackageEntity)
			return this.getParent().getQualifiedName();
		else
			return super.getQualifiedName();
	}
	public Collection<String> imports() {
		return importedNames;
	}
	@Override
	public void inferLocalLevelTypes(TypeInfer typeInferer) {
		for (String importedName:importedNames) {
			if (typeInferer.isBuiltInTypePrefix(importedName)) continue;
			List<Entity> importedEntities = typeInferer.resolveImportEntity(importedName);
			this.resolvedImportedEntities.addAll(importedEntities);
		}		
		super.inferLocalLevelTypes(typeInferer);
	}
}
