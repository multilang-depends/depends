package depends.extractor.kotlin;

import depends.entity.repo.EntityRepo;
import depends.extractor.java.JavaHandlerContext;
import depends.relations.Inferer;

public class KotlinHandlerContext extends JavaHandlerContext {

	public KotlinHandlerContext(EntityRepo entityRepo, Inferer inferer) {
		super(entityRepo,inferer);
	}

}
