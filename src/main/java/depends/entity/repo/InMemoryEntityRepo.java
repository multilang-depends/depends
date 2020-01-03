package depends.entity.repo;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.GenericName;
import depends.entity.MultiDeclareEntities;


public class InMemoryEntityRepo extends SimpleIdGenerator implements EntityRepo {

	public class EntityＭapIterator implements Iterator<Entity>{

		private Iterator<Entry<Integer, Entity>> entryIterator;

		public EntityＭapIterator(Set<Entry<Integer, Entity>> entries) {
			this.entryIterator = entries.iterator();
		}
		@Override
		public boolean hasNext() {
			return entryIterator.hasNext();
		}

		@Override
		public Entity next() {
			return entryIterator.next().getValue();
		}
		
	}
	
	private Map<String, Entity> allEntieisByName;
	private Map<Integer, Entity> allEntitiesById;
	private List<Entity> allFileEntitiesByOrder;

	public InMemoryEntityRepo() {
		allEntieisByName = new TreeMap<>();
		allEntitiesById = new TreeMap<>();
		allFileEntitiesByOrder = new LinkedList<>();
	}

	@Override
	public Entity getEntity(String entityName) {
		return allEntieisByName.get(entityName);
	}

	@Override
	public Entity getEntity(Integer entityId) {
		return allEntitiesById.get(entityId);
	}

	@Override
	public void add(Entity entity) {
		allEntitiesById.put(entity.getId(), entity);
		String name = entity.getRawName().uniqName();
		if (entity.getQualifiedName() != null && !(entity.getQualifiedName().isEmpty())) {
			name = entity.getQualifiedName();
		}
		if (allEntieisByName.containsKey(name)) {
			Entity existedEntity = allEntieisByName.get(name);
			if (existedEntity instanceof MultiDeclareEntities) {
				((MultiDeclareEntities) existedEntity).add(entity);
			} else {
				MultiDeclareEntities eMultiDeclare = new MultiDeclareEntities(existedEntity, this.generateId());
				eMultiDeclare.add(entity);
				allEntieisByName.put(name, eMultiDeclare);
			}
		} else {
			allEntieisByName.put(name, entity);
		}
		if (entity.getParent() != null)
			Entity.setParent(entity, entity.getParent());
	}

	@Override
	public Iterator<Entity> entityIterator() {
		return new EntityＭapIterator(allEntitiesById.entrySet());
	}

	
	@Override
	public void update(Entity entity) {
	}

	@Override
	public Entity getEntity(GenericName rawName) {
		return this.getEntity(rawName.uniqName());
	}

	@Override
	public Collection<Entity> getFileEntities() {
		return allFileEntitiesByOrder;
	}

	@Override
	public Iterator<Entity> sortedFileIterator() {
		return allFileEntitiesByOrder.iterator();
	}

	@Override
	public void addFile(FileEntity fileEntity) {
		allFileEntitiesByOrder.add(fileEntity);
	}

}
