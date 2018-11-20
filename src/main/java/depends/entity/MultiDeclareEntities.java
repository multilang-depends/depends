package depends.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MultiDeclareEntities extends ContainerEntity {
	List<ContainerEntity> entities = new ArrayList<>();
	public MultiDeclareEntities(Entity entity, int id ) {
		super(entity.getRawName(), entity.getParent(), id);
		if (entity instanceof ContainerEntity)
			entities.add((ContainerEntity)entity);
	}

	@Override
	public void inferLocalLevelTypes(TypeInfer typeInferer) {
		for (Entity entity:entities) {
			entity.inferLocalLevelTypes(typeInferer);
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
}
