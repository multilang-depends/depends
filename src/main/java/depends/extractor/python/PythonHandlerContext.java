package depends.extractor.python;

import depends.entity.AliasEntity;
import depends.entity.Entity;
import depends.entity.GenericName;
import depends.entity.PackageEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.HandlerContext;
import depends.relations.Inferer;

public class PythonHandlerContext extends HandlerContext {

	public PythonHandlerContext(EntityRepo entityRepo, Inferer inferer) {
		super(entityRepo, inferer);
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
		if (parent.getRawName().getName().endsWith("__init__.py")) {
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

}
