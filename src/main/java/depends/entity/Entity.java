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

package depends.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


import depends.relations.Inferer;
import depends.relations.Relation;

/**
 * Entity is the root of all entities, including file, package, module, 
 * class, method/function etc.
 * Each entity has unique id, name,qualifiedName, parent, children
 * We also use entity to record relations 
 */
public abstract class Entity {
	
    Integer id=-1;
	String qualifiedName = null;
	GenericName rawName = GenericName.build("");
	Entity parent;
	private MultiDeclareEntities mutliDeclare = null;
	private Set<Entity> children;
    ArrayList<Relation> relations;
	private Entity actualReferTo = null;
	private boolean inScope = true;
	protected HashMap<String, Entity> visibleNames = new HashMap<>();
	public Entity() {};
    public Entity(GenericName rawName, Entity parent, Integer id) {
		this.qualifiedName = null;
		this.rawName = rawName;
		this.parent = parent;
		this.id = id;
		if (parent!=null)
			parent.addChild(this);
		deduceQualifiedName();
		visibleNames.put(rawName.getName(), this);
		visibleNames.put(qualifiedName, this);
	}

    private Set<Entity> children() {
    	if (children==null)
    		children = new HashSet<>();
		return children;
	}
	/**
     * Rule 1: if it start with '.' , then the name is equal to raw name
     * Rule 2: if parent not exists, the name is equal to raw name
     * Rule 3: if parent exists but no qualified name exists or empty, the name is equal to raw name
     * Rule 4: otherwise, qualified name = parent_qualfied_name + "."+rawName
     * Rule 5: make sure the qualified name do not start with '.'
     */
	private void deduceQualifiedName() {
		rawName = rawName.replace("::","." );
		if (this.rawName.startsWith(".")) {
			this.qualifiedName = this.rawName.uniqName().substring(1);
			return; //already qualified
		}
		if (parent==null) {
			this.qualifiedName = this.rawName.uniqName();
			return;
		}
		if (parent.getQualifiedName(true)==null) {
			this.qualifiedName = this.rawName.uniqName();
			return;
		}
		if (parent.getQualifiedName(true).isEmpty()) {
			this.qualifiedName = rawName.uniqName();
			return;
		}
		this.qualifiedName= parent.getQualifiedName(true)+"." + rawName.uniqName();
	}


	public GenericName getRawName() {
		return rawName;
	}

	public Integer getId() {
        return id;
    }

    public void addRelation(Relation relation) {
    	if (relations==null)
    		relations = new ArrayList<>();
    	if (relation.getEntity()==null) return;
        relations.add(relation);
    }

    public ArrayList<Relation> getRelations() {
    	if (relations==null)
    		return new ArrayList<>();
        return relations;
    }

    public void addChild(Entity child) {
    	children().add(child);
		visibleNames.put(child.getRawName().getName(), child);
		visibleNames.put(child.getQualifiedName(), child);
    }

	public Entity getParent() {
		return parent;
	}

	public void setParent(Entity parent) {
		this.parent = parent;
	}
	
	public Collection<Entity> getChildren() {
		if (children==null)
			return new HashSet<>();
		return children;
	}
	
	public void setQualifiedName(String qualifiedName) {
		this.qualifiedName = qualifiedName;
	}

	public void setRawName(GenericName rawName) {
		this.rawName = rawName;
		deduceQualifiedName();
	}
	
	public final String getQualifiedName() {
		return qualifiedName;
	}

	public String getQualifiedName(boolean overrideFileWithPackage) {
		return qualifiedName;
	}

	@Override
	public String toString() {
		return "Entity [id=" + id + ", qualifiedName=" + qualifiedName + ", rawName=" + rawName + "]";
	}

	/**
	 * Get ancestor of type.  
	 * @param classType
	 * @return null (if not exist) or the type
	 */
	public Entity getAncestorOfType(@SuppressWarnings("rawtypes") Class classType) {
		Entity fromEntity = this;
		while(fromEntity!=null) {
			if (fromEntity.getClass().equals(classType))
				return fromEntity;
			if (fromEntity.getParent()==null) return null;
			fromEntity = fromEntity.getParent();
		}
		return null;
	}

	/**
	 * Invoke inferer to resolve the entity type etc. 
	 * */
	public void inferEntities(Inferer inferer) {
		inferLocalLevelEntities(inferer);
		for (Entity child:this.getChildren()) {
			child.inferEntities(inferer);
		}
	}
	public abstract void inferLocalLevelEntities(Inferer inferer);
	
	public TypeEntity getType() {
		return null;
	}

	public String getDisplayName() {
		return getRawName().uniqName();
	}

	public MultiDeclareEntities getMutliDeclare() {
		return mutliDeclare;
	}

	public void setMutliDeclare(MultiDeclareEntities mutliDeclare) {
		this.mutliDeclare = mutliDeclare;
	}

	public Entity getActualReferTo() {
		if (this.actualReferTo ==null)
			return this;
		return actualReferTo;
	}
	
	public void setActualReferTo(Entity actualReferTo) {
		this.actualReferTo = actualReferTo;
	}

	public static void setParent(Entity child, Entity parent) {
		if (parent == null)
			return;
		if (child == null)
			return;
		if (parent.equals(child.getParent()))
			return;
		child.setParent(parent);
		parent.addChild(child);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public void setInScope(boolean value) {
		this.inScope  = value;
		children().forEach(child->child.setInScope(value));
	}
	
	public boolean inScope() {
		return inScope;
	}
	public Entity getByName(String name, HashSet<Entity> searched) {
		if (searched.contains(this)) return null;
		searched.add(this);
		return visibleNames.get(name);
	}
	
}
