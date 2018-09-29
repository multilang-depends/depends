package depends.extractor;

import java.util.HashMap;

import depends.deptypes.DependencyType;
import depends.entity.ContainerEntity;
import depends.entity.Entity;
import depends.entity.Expression;
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
	
	public void commitAllExpressionUsage(ContainerEntity entity) {
		HashMap<Integer, Expression> data = entity.expressions();
		for (Integer item : data.keySet()) {
			Expression value = data.get(item);
			if (value.isSet) {
				addRelation(value.returnType, DependencyType.RELATION_SET);
			}
		}
		data.clear();
	}
}
