package depends.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import depends.relations.Inferer;

/**
 * MultiDeclareEntity is a special container, which is used as a wrapper
 * of multi-declaration. for example, 
 * in C++, a function could be declared in different place with the same signature.
 */
public class MultiDeclareEntities extends ContainerEntity {
	List<ContainerEntity> entities = new ArrayList<>();
	public MultiDeclareEntities(Entity entity, int id ) {
		super(entity.getRawName(), entity.getParent(), id);
		if (entity instanceof ContainerEntity)
			entities.add((ContainerEntity)entity);
	}

	@Override
	public void inferLocalLevelEntities(Inferer inferer) {
		for (Entity entity:entities) {
			entity.inferLocalLevelEntities(inferer);
		}
	}

	public void add(Entity entity) {
		if (entity instanceof ContainerEntity)
			entities.add((ContainerEntity)entity);
	}

	public List<ContainerEntity> getEntities() {
		return entities;
	}

	@Override
	public Collection<Entity> getChildren() {
		List<Entity> children = new ArrayList<>();
		for (Entity entity:entities) {
			children.addAll(entity.getChildren());
		}
		return children;
	}

	@Override
	public TypeEntity getType() {
		for (Entity entity:entities) {
			if(entity.getType()!=null);
				return entity.getType();
		}
		return null;
	}
}
