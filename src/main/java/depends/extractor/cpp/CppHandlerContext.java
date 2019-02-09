package depends.extractor.cpp;

import depends.entity.Entity;
import depends.entity.PackageEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.HandlerContext;

public class CppHandlerContext extends HandlerContext {

	public CppHandlerContext(EntityRepo entityRepo) {
		super(entityRepo);
	}

	public Entity foundNamespace(String nampespaceName) {
		PackageEntity pkgEntity = new PackageEntity(nampespaceName, currentFile(),idGenerator.generateId());
		entityRepo.add(pkgEntity);
		entityStack.push(pkgEntity);
		return pkgEntity;
	}
}
