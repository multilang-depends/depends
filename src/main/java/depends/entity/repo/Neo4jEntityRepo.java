package depends.entity.repo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.transaction.Transaction;

import depends.entity.ContainerEntity;
import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.FunctionEntity;
import depends.entity.MultiDeclareEntities;
import depends.persistent.neo4j.executor.Neo4jSessionFactory;
public class Neo4jEntityRepo extends SimpleIdGenerator implements EntityRepo {

	class EntityDesc{
		EntityDesc(Class type,Integer id){
			this.type = type;
			this.id = id;
		}
		Class type;
		Integer id;
	}
	private HashMap<String, EntityDesc> allEntieisByName;
	private HashMap<Integer, EntityDesc> allEntitiesById;
	private Session session;
	private Transaction transaction;
	
	public Neo4jEntityRepo() {
		allEntieisByName = new HashMap<>();
		allEntitiesById = new HashMap<>();
		session = Neo4jSessionFactory.getInstance().getNeo4jSession();
		//session.purgeDatabase();
	}

	@Override
	public Entity getEntity(String entityName) {
		if (!allEntieisByName.containsKey(entityName)) return null;
		return (Entity)(session.load(allEntieisByName.get(entityName).type, allEntieisByName.get(entityName).id));
	}

	@Override
	public Entity getEntity(Integer entityId) {
		if (!allEntitiesById.containsKey(entityId)) return null;
		Object object = session.load(allEntitiesById.get(entityId).type, allEntitiesById.get(entityId).id);
		return (Entity)object;
	}

	@Override
	public void add(Entity entity) {
		//if (entity instanceof FileEntity) {
			if (transaction!=null) {
				transaction.commit();
				session.clear();
			}
			transaction = session.beginTransaction();
		//}
		if (entity.getParent() != null)
			Entity.setParent(entity, entity.getParent());
		long t1 = System.currentTimeMillis();
		session.save(entity);
		long t2 = System.currentTimeMillis();
		System.out.println(t2-t1);
		if (entity instanceof ContainerEntity) {
			ContainerEntity c = (ContainerEntity)entity;
			c.expressionList().forEach(e->session.save(e));
			c.getVars().forEach(e->session.save(e));
		}
		
		if (entity instanceof FunctionEntity) {
			FunctionEntity c = (FunctionEntity)entity;
			c.getParameters().forEach(e->session.save(e));
		}
		String name = computeName(entity);
		if (allEntieisByName.containsKey(name)) {
			Entity existedEntity = getEntity(name);
			if (existedEntity instanceof MultiDeclareEntities) {
				((MultiDeclareEntities) existedEntity).add(entity);
				session.save(existedEntity);
				EntityDesc desc = new EntityDesc(entity.getClass(),entity.getId());
				allEntitiesById.put(entity.getId(), desc);
				allEntieisByName.put(name, desc);
			} else {
				MultiDeclareEntities eMultiDeclare = new MultiDeclareEntities(existedEntity, this.generateId());
				allEntitiesById.put(eMultiDeclare.getId(), new EntityDesc(MultiDeclareEntities.class,eMultiDeclare.getId()));
				allEntieisByName.put(name, new EntityDesc(MultiDeclareEntities.class,eMultiDeclare.getId()));
				eMultiDeclare.add(entity);
				session.save(eMultiDeclare);
				EntityDesc desc = new EntityDesc(entity.getClass(),entity.getId());
				allEntitiesById.put(entity.getId(), desc);
			}
		} else {
			EntityDesc desc = new EntityDesc(entity.getClass(),entity.getId());
			allEntitiesById.put(entity.getId(), desc);
			allEntieisByName.put(name, desc);
		}
	}

	private String computeName(Entity entity) {
		String name = entity.getRawName();
		if (entity.getQualifiedName() != null && !(entity.getQualifiedName().isEmpty())) {
			name = entity.getQualifiedName();
		}
		return name;
	}

	class Itr implements Iterator<Entity> {
		private Iterator<Map<String, Object>> itr;

		Itr(Iterator<Map<String, Object>> iterator){
			this.itr = iterator;
		}

		@Override
		public boolean hasNext() {
			return itr.hasNext();
		}

		@Override
		public Entity next() {
			Collection<Object> values = itr.next().values();
			for (Object v:values) {
				return (Entity)v;
			}
			return null;
		}
    }
	
	class Interator implements Iterator<Entity> {
		private Iterator<EntityDesc> itr;

		Interator(Iterator<EntityDesc> iterator){
			this.itr = iterator;
		}

		@Override
		public boolean hasNext() {
			return itr.hasNext();
		}

		@Override
		public Entity next() {
			EntityDesc t = itr.next();
			if (t.type.equals(MultiDeclareEntities.class)) {
				System.out.print("aa");
			}
			Entity v = getEntity(t.id);
			if (v==null) {
				System.out.print("aa");
			}
			return v;
		}
    }
	
	@Override
	public Iterator<Entity> getEntities() {
    	Result result = session.query("MATCH (n) RETURN n", new HashMap<>());
    	Iterable<Map<String, Object>> v = result.queryResults();
    	return new Interator(allEntitiesById.values().iterator());
	}

	@Override
	public void update(Entity entity) {
		session.save(entity);
	}
}
