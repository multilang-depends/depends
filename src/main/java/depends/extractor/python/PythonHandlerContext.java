package depends.extractor.python;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.FunctionEntity;
import depends.entity.GenericName;
import depends.entity.TypeEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.HandlerContext;
import depends.relations.Inferer;

public class PythonHandlerContext extends HandlerContext {

	public PythonHandlerContext(EntityRepo entityRepo,Inferer inferer) {
		super(entityRepo,inferer);
	}

	@Override
	public Entity latestValidContainer() {
		Entity validContainer = super.latestValidContainer();
		if (validContainer==null) return null;
		if (validContainer instanceof FileEntity &&
				validContainer.getRawName().getName().endsWith("__init__.py")) {
			return validContainer.getParent();
		}
		return validContainer;
	}


}
