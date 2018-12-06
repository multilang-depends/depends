package depends.extractor.java;

import depends.entity.Entity;
import depends.entity.PackageEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.HandlerContext;

public class JavaHandlerContext extends HandlerContext {

	public JavaHandlerContext(EntityRepo entityRepo) {
		super(entityRepo);
	}

	public Entity foundNewPackage(String packageName) {
		Entity pkgEntity = entityRepo.getEntity(packageName);
		if (pkgEntity == null) {
			pkgEntity = new PackageEntity(packageName, idGenerator.generateId());
			entityRepo.add(pkgEntity);
		}
		entityRepo.setParent(currentFileEntity,pkgEntity);
		return pkgEntity;
	}
}
