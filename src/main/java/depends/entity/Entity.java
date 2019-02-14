package depends.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import depends.relations.Inferer;
import depends.relations.Relation;

/**
 * Entity is the root of all entities, including file, package, module, 
 * class, method/function etc.
 * Each entity has unique id, name,qualifiedName, parent, children
 * We also use entity to record relations 
 */
public abstract class Entity {
	int id=-1;
	String qualifiedName = null;
	String rawName = "";
	Entity parent;
	List<Entity> children = new ArrayList<>();
    ArrayList<Relation> relations = new ArrayList<>();

	
    public Entity(String rawName, Entity parent, Integer id) {
		this.qualifiedName = null;
		this.rawName = rawName;
		this.parent = parent;
		this.id = id;
		if (parent!=null)
			parent.children.add(this);
		deduceQualifiedName();
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
			this.qualifiedName = this.rawName.substring(1);
			return; //already qualified
		}
		if (parent==null) {
			this.qualifiedName = this.rawName;
			return;
		}
		if (parent.getQualifiedName(true)==null) {
			this.qualifiedName = this.rawName;
			return;
		}
		if (parent.getQualifiedName(true).isEmpty()) {
			this.qualifiedName = rawName;
			return;
		}
		this.qualifiedName= parent.getQualifiedName(true)+"." + rawName;
	}


	public String getRawName() {
		return rawName;
	}

	public int getId() {
        return id;
    }

    public void addRelation(Relation relation) {
        relations.add(relation);
    }

    public ArrayList<Relation> getRelations() {
        return relations;
    }

    public void addChild(Entity child) {
        children.add(child);
    }

	public Entity getParent() {
		return parent;
	}

	public void setParent(Entity parent) {
		this.parent = parent;
	}
	
	public Collection<Entity> getChildren() {
		return children;
	}
	
	public void setQualifiedName(String qualifiedName) {
		this.qualifiedName = qualifiedName;
	}

	public void setRawName(String rawName) {
		this.rawName = rawName;
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
		for (Entity child:children) {
			child.inferEntities(inferer);
		}
	}
	public abstract void inferLocalLevelEntities(Inferer inferer);
	
	public TypeEntity getType() {
		return null;
	}

	public String getDisplayName() {
		return getRawName();
	}
}
