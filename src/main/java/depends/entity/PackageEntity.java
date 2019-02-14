package depends.entity;

public class PackageEntity extends TypeEntity {
	public PackageEntity(String rawName, Integer id) {
		super(rawName,  null,id);
		setQualifiedName(rawName); //in Java, package raw name = full name
	}

	public PackageEntity(String rawName, Entity currentFile, Integer id) {
		super(rawName,  currentFile,id);
	}
}
