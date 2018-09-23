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
	
	public void addImportRelation(String importedTypeOrPackage) {
		entityRepo.addRelation(context().currentFile().getId(),importedTypeOrPackage,DependencyType.RELATION_IMPORT);
	}

	public void addInheritRelation(String superClassName) {
		entityRepo.addRelation(context().currentType().getId(), superClassName, DependencyType.RELATION_INHERIT);
	}

	public void addImplementRelation(String interfaceName) {
		entityRepo.addRelation(context().currentType().getId(), interfaceName, DependencyType.RELATION_IMPLEMENT);		
	}
	
	public void addParameterRelation(String parameterTypeName) {
		entityRepo.addRelation(context().currentFunction().getId(), parameterTypeName, DependencyType.RELATION_PARAMETER);
	}
	
	public void addReturnRelation(String returnTypeName) {
		entityRepo.addRelation(context().currentFunction().getId(), returnTypeName, DependencyType.RELATION_RETURN);
	}
	
	public void addSetRelation(String type) {
		Entity currentFunction = context().currentFunction();
		if (currentFunction!=null)
			entityRepo.addRelation(currentFunction.getId(), type, DependencyType.RELATION_SET);
		else
			entityRepo.addRelation(context().currentType().getId(), type, DependencyType.RELATION_SET);
	}
	
	public void addVars(String type, String var) {
		entityRepo.addRelation(context().lastContainer().getId(), type, DependencyType.RELATION_DEFINE);
	}
}
