package depends.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import depends.entity.repo.EntityRepo;
import depends.entity.types.VarEntity;

public class Entity {
    protected String fullName = "";
	protected int id=-1;
    protected int parentId=-1;
    
    public Entity(String fullName, int parentId, Integer id) {
		this.setFullName(fullName);
		this.setParentId(parentId);
		this.setId(id);
		vars = new ArrayList<>();
	}

	public void setFileId(int fileId) {
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


    protected ArrayList<Integer> childrenIds = new ArrayList<>();
    protected ArrayList<Relation> relations = new ArrayList<>();
    protected ArrayList<VarEntity> vars;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
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

    public void addChildId(Integer id) {
        childrenIds.add(id);
    }

    public void addChildrenIds(ArrayList<Integer> ids) {
        childrenIds.addAll(ids);
    }

    public ArrayList<Integer> getChildrenIds() {
        return childrenIds;
    }

    @Override
    public String toString() {
        String str = "";
        str += "\n(";
        str += ("name:" + fullName + ',');
        str += ("id:" + id + ',');
        str += ("parentId:" + parentId + ",");
        str += ("childrenIds:" + childrenIds + ",");
        str += ("relations:" + relations);
        str += ")\n";
        return str;
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

	public void addVar(VarEntity var) {
		this.vars.add(var);
	}

	public ArrayList<VarEntity> getVars() {
		return this.vars;
	}
}
