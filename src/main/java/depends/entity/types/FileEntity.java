package depends.entity.types;

import java.util.HashMap;

import depends.entity.Entity;

public class FileEntity extends Entity{
	HashMap<String,String> importedNames = new HashMap<>();
	public FileEntity(String fullName, int fileId) {
		super(fullName, -1,fileId);
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
}
