package depends.entity.repo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
import depends.entity.Relation;
import depends.entity.types.PackageEntity;

public class EntityRepo {
	public HashMap<String, Entity> allEntieisByName = new HashMap<>();
	public HashMap<Integer, Entity> allEntieisById = new HashMap<>();
	private int nextAvaliableIndex;
	
	
	public EntityRepo() {
		nextAvaliableIndex = 0;
	}
	public Entity getEntity(String entityName) {
		return allEntieisByName.get(entityName);
	}
	public Entity getEntity(Integer entityId) {
		return allEntieisById.get(entityId);
	}
	
	public void add(Entity entity) {
		if (!entity.getFullName().isEmpty())
			allEntieisByName.put(entity.getFullName(),entity);
		allEntieisById.put(entity.getId(), entity);
	}
	
	public void updateEntityNameIndex(String oldName, String newName, Entity entity) {
		if (oldName.isEmpty()) {
			allEntieisByName.put(newName, entity);
			return;
		}
		if (entity==null) return;
		allEntieisByName.remove(oldName);
		allEntieisByName.put(newName, entity);
	}
	
	public Collection<Entity> getEntities() {
		return allEntieisById.values();
	}
	public Integer getCurrentIndex() {
		return nextAvaliableIndex++;
	}

	public int getAncestorOfType(int entityId, @SuppressWarnings("rawtypes") Class classType) throws EntityNotExistsException, NoRequestedTypeOfAncestorExistsException {
		Entity e = this.getEntity(entityId);
		if (e==null) throw new EntityNotExistsException(entityId);
		while (!e.getClass().equals(classType)) {
			e = this.getEntity(e.getParentId());
			if (e==null) break;
		}
		if (e==null) throw new NoRequestedTypeOfAncestorExistsException(entityId,classType);
		return e.getId();
	}
	
	public Set<String> resolveAllBindings() {
    	HashSet<String> unsolved = new HashSet<>();
        for (Entity entity:allEntieisByName.values()) {
        	Set<String> u = entity.resolveBinding(this);
			unsolved.addAll(u);
        }
		return unsolved;		

	}

	/**
	 * note: import package.* means that it depends on all files of the package. it is not a suggested practices;
	 * so we expends the import relations to all files under the package
	 */
	public void expendsPackageImports() {
        for (Entity entity:allEntieisByName.values()) {
        	ArrayList<Relation> expendedRelations = new ArrayList<>();
        	for (Relation r:entity.getRelations()) {
        		if (!r.getType().equals(DependencyType.RELATION_IMPORT))continue;
        		if (r.getToId()<0) continue;
        		if (!(this.getEntity(r.getToId()) instanceof PackageEntity)) continue;
        		Entity pkg = this.getEntity(r.getToId());
        		for (Integer fileId: pkg.getChildrenIds()) {
        			expendedRelations.add(new Relation(DependencyType.RELATION_IMPORT,fileId));
        		}
        	}
        	entity.addRelations(expendedRelations);
        }
	}
	
	public void addRelation(int theEntityId, String entityFullName, String relationType) {
	        getEntity(theEntityId).addRelation(new Relation(relationType,entityFullName));
	}


}
