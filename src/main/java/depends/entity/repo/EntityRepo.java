/*
MIT License

Copyright (c) 2018-2019 Gang ZHANG

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

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
		if (parent==null) return;
		if (child==null) return;
		if (parent.equals(child.getParent())) return;
		child.setParent(parent);
		parent.addChild(child);
	}
}
