package depends.extractor;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
import depends.entity.repo.EntityRepo;

public abstract class GenericHandler {

	private EntityRepo entityRepo;
	private HandlerContext context;

	public GenericHandler(EntityRepo entityRepo) {
		this.entityRepo = entityRepo;
		this.context = new HandlerContext(entityRepo);
	}
	public HandlerContext context() {
		return context;
	}
	
	public void addEntity(Entity entity) {
		entityRepo.add(entity);
	}
	
	public void addRelation(String type, String relation) {
		Entity latestContainer = context().latestValidContainer();
		entityRepo.addRelation(latestContainer.getId(),type,relation);
	}
	
	public void addVars(String type, String var) {
		entityRepo.addRelation(context().lastContainer().getId(), type, DependencyType.RELATION_DEFINE);
	}
}
