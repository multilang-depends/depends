package depends.extractor.python;

import depends.entity.repo.EntityRepo;
import depends.extractor.HandlerContext;
import depends.relations.Inferer;

public class PythonHandlerContext extends HandlerContext {

	public PythonHandlerContext(EntityRepo entityRepo,Inferer inferer) {
		super(entityRepo,inferer);
	}

}
