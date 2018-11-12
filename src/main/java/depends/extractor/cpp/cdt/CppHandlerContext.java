package depends.extractor.cpp.cdt;

import depends.entity.Entity;
import depends.entity.repo.EntityRepo;
import depends.entity.types.PackageEntity;
import depends.extractor.HandlerContext;

public class CppHandlerContext extends HandlerContext {

	public CppHandlerContext(EntityRepo entityRepo) {
		super(entityRepo);
	}

	public Entity foundNamespace(String nampespaceName) {
		Entity pkgEntity = entityRepo.getEntity(nampespaceName);
		if (pkgEntity == null) {
			pkgEntity = new PackageEntity(nampespaceName, idGenerator.generateId());
			entityRepo.add(pkgEntity);
		}
		entityStack.push(pkgEntity);
		return pkgEntity;
	}

}
