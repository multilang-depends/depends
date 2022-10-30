package depends.extractor.python;

import depends.entity.*;
import depends.entity.repo.EntityRepo;
import depends.extractor.HandlerContext;
import depends.relations.IBindingResolver;

public class PythonHandlerContext extends HandlerContext {

	public PythonHandlerContext(EntityRepo entityRepo, IBindingResolver bindingResolver) {
		super(entityRepo, bindingResolver);
	}

	@Override
	public void addToRepo(Entity entity) {
		super.addToRepo(entity);
		postProcessingOfInit(entity);
	}

	private void postProcessingOfInit(Entity entity) {
		Entity parent = entity.getParent();
		if (parent == null)
			return;
		if (parent.getRawName().getName().endsWith(PythonBuiltInType.PACKAGE_PY_NAME)) {
			Entity packageEntity = parent.getAncestorOfType(PackageEntity.class);
			if (packageEntity == null)
				return;
			packageEntity.addChild(entity);
		}
	}

	@Override
	public AliasEntity foundNewAlias(String aliasName, String originalName) {
		AliasEntity alias = super.foundNewAlias(aliasName, originalName);
		if (alias != null) {
			alias.setDeepResolve(true);
		}
		return alias;
	}

	@Override
	public AliasEntity foundNewAlias(GenericName aliasName, Entity referToEntity) {
		AliasEntity alias = super.foundNewAlias(aliasName, referToEntity);
		if (alias != null) {
			alias.setDeepResolve(true);
		}
		return alias;
	}

	public FileEntity startFile(String fileName) {
		return super.startFile(true, fileName);
	}
}
