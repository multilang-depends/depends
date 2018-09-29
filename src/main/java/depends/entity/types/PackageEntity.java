package depends.entity.types;

import depends.entity.ContainerEntity;

public class PackageEntity extends ContainerEntity {
	public PackageEntity(String rawName, Integer id) {
		super(rawName,  null,id);
		setQualifiedName(rawName); //in Java, package raw name = full name
	}
}
