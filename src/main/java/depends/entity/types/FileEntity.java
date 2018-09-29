package depends.entity.types;

import java.util.Collection;
import java.util.HashMap;

import depends.entity.ContainerEntity;

public class FileEntity extends ContainerEntity{
	HashMap<String,String> importedNames = new HashMap<>();
	public FileEntity(String fullName, int fileId) {
		super(fullName, null,fileId);
		setQualifiedName(fullName);
	}
	public void addImport(String importedTypeOrPackage) {
		String lastName = importedTypeOrPackage;
        if (lastName.indexOf(".") > 0)
        	lastName = lastName.substring(lastName.lastIndexOf(".")+1);
        importedNames.put(lastName, importedTypeOrPackage);
	}
	public String getImport(String lastName) {
		return importedNames.get(lastName);
	}
	@Override
	public String getQualifiedName() {
		if (this.getParent()==null)
			return "";
		if (this.getParent() instanceof PackageEntity)
			return this.getParent().getQualifiedName();
		else
			return super.getQualifiedName();
	}
	public Collection<String> imports() {
		return importedNames.values();
	}
}
