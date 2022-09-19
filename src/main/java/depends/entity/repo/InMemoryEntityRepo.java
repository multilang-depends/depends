package depends.entity.repo;

import depends.entity.*;
import multilang.depends.util.file.FileUtil;

import java.util.*;
import java.util.Map.Entry;


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
		add(TypeEntity.buildInType);
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
	public void clear() {
		allEntieisByName.clear();
		allEntitiesById.clear();
		allFileEntitiesByOrder.clear();
	}

	@Override
	public FileEntity getFileEntity(String fileFullPath) {
		fileFullPath = FileUtil.uniqFilePath(fileFullPath);
		Entity entity = this.getEntity(fileFullPath);
		if (entity ==null) return null;
		if (entity instanceof FileEntity) return (FileEntity) entity;
		if (entity instanceof  MultiDeclareEntities){
			MultiDeclareEntities multiDeclare = (MultiDeclareEntities) entity;
			for (Entity theEntity: multiDeclare.getEntities()){
				if (theEntity instanceof FileEntity){
					return (FileEntity) theEntity;
				}
			}
		}
		return null;
	}

	@Override
	public void completeFile(String fileFullPath) {
		FileEntity fileEntity = getFileEntity(fileFullPath);
		// in case of parse error(throw exception), the file entity may not exists
		if (fileEntity!=null) {
			fileEntity.cacheAllExpressions();
			allFileEntitiesByOrder.add(fileEntity);
		}
	}
}
