package depends.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import depends.entity.repo.EntityRepo;

public abstract class Entity {
	int id=-1;
	String qualifiedName = null;
	String rawName = "";
	Entity parent;
	ArrayList<Entity> children = new ArrayList<>();
    ArrayList<Relation> relations = new ArrayList<>();

	
    public Entity(String rawName, Entity parent, Integer id) {
		this.qualifiedName = null;
		this.rawName = rawName;
		this.parent = parent;
		this.setId(id);
		deduceQualifiedName();
	}

	private void deduceQualifiedName() {
		if (this.rawName.contains(".")) {
			this.qualifiedName = this.rawName;
			return; //already qualified
		}
		if (parent==null) {
			this.qualifiedName = this.rawName;
			return;
		}
		if (parent.getQualifiedName()==null) {
			this.qualifiedName = this.rawName;
			return;
		}
		if (parent.getQualifiedName().isEmpty()) {
			this.qualifiedName = rawName;
			return;
		}
		this.qualifiedName= parent.getQualifiedName()+"." + rawName;

	}


	public String getRawName() {
		return rawName;
	}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addRelation(Relation relation) {
        relations.add(relation);
    }

    public void addRelations(ArrayList<Relation> relations) {
        this.relations.addAll(relations);
    }

    public ArrayList<Relation> getRelations() {
        return relations;
    }

    public void addChild(Entity child) {
        children.add(child);
    }

	public Set<String> resolveBinding(EntityRepo registry) {
		Set<String> unsolved = new HashSet<>();
		for (Relation relation:this.relations) {
			if (relation.getToId()<0) {
				String fullName = relation.getToFullName();
				Entity rhs = registry.getEntity(fullName);
				if (rhs!=null) {
					relation.refreshToId(rhs.getId());
				}else {
					unsolved.add(fullName);
				}
			}
		}
		return unsolved;
	}

	public Entity getParent() {
		return parent;
	}

	public void setParent(Entity parent) {
		this.parent = parent;
	}
	
	public List<Entity> getChildren() {
		return children;
	}
	
	public void setQualifiedName(String qualifiedName) {
		this.qualifiedName = qualifiedName;
	}

	public void setRawName(String rawName) {
		this.rawName = rawName;
	}
	
	public String getQualifiedName() {
		return qualifiedName;
	}

	public void inferTypes(TypeInfer typeInferer) {
		inferLocalLevelTypes(typeInferer);
		for (Entity child:children) {
			child.inferTypes(typeInferer);
		}
	}
	public abstract void inferLocalLevelTypes(TypeInfer typeInferer);

	@Override
	public String toString() {
		return "Entity [id=" + id + ", qualifiedName=" + qualifiedName + ", rawName=" + rawName + "]";
	}

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
	
}
