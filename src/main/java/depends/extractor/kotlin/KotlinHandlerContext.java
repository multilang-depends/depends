package depends.extractor.kotlin;

import depends.entity.repo.EntityRepo;
import depends.extractor.java.JavaHandlerContext;
import depends.relations.IBindingResolver;

public class KotlinHandlerContext extends JavaHandlerContext {

	public KotlinHandlerContext(EntityRepo entityRepo, IBindingResolver bindingResolver) {
		super(entityRepo, bindingResolver);
	}

}
