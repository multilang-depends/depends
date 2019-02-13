package depends.entity.repo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import depends.entity.Entity;
import depends.entity.MultiDeclareEntities;

public class EntityRepo extends IdGenerator{
	private HashMap<String, Entity> allEntieisByName = new HashMap<>();
	private HashMap<Integer, Entity> allEntitiesById = new HashMap<>();
	private List<Entity> allEntitiesByOrder = new ArrayList<>();
	public static final String GLOBAL_SCOPE_NAME = "::GLOBAL::";

	public EntityRepo() {
	}
	
	public Entity getEntity(String entityName) {
		return allEntieisByName.get(entityName);
	}
	
	public Entity getEntity(Integer entityId) {
		return allEntitiesById.get(entityId);
	}
	
	public void add(Entity entity) {
		allEntitiesByOrder.add(entity);
		allEntitiesById.put(entity.getId(), entity);
		String name = entity.getRawName();
		if (entity.getQualifiedName()!=null && !(entity.getQualifiedName().isEmpty()) ) {
			name = entity.getQualifiedName();
		}
		if (allEntieisByName.containsKey(name)) {
			Entity existedEntity = allEntieisByName.get(name);
			if (existedEntity instanceof MultiDeclareEntities) {
				((MultiDeclareEntities)existedEntity).add(entity);
			}else {
				MultiDeclareEntities eMultiDeclare = new MultiDeclareEntities(existedEntity,this.generateId());
				eMultiDeclare.add(entity);
				allEntieisByName.put(name, eMultiDeclare);
			}
		}else {
			allEntieisByName.put(name, entity);
		}
		if (entity.getParent()!=null)
			this.setParent(entity, entity.getParent());
	}
		
	public Collection<Entity> getEntities() {
		return allEntitiesByOrder;
	}
	
	public void setParent(Entity child, Entity parent) {
		child.setParent(parent);
		parent.addChild(child);
	}
}
