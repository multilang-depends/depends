package depends.extractor;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;

import depends.entity.ContainerEntity;
import depends.entity.Entity;
import depends.entity.FunctionEntity;
import depends.entity.VarEntity;
import depends.relations.Relation;

public abstract class ParserTest {
	protected void assertNotContainsRelation(Entity inEntity, String dependencyType, String dependedEntityFullName) {
		for (Relation r:inEntity.getRelations()) {
			if (r.getType().equals(dependencyType)) {
				if (r.getEntity().getQualifiedName().equals(dependedEntityFullName)) {
					fail("found unexpected relation: type = " + dependencyType + " to entity " + dependedEntityFullName);
				}
			}
		}
	}
	
	protected void assertContainsRelation(Entity inEntity, String dependencyType, String dependedEntityFullName) {
		Relation relation = null;
		for (Relation r:inEntity.getRelations()) {
			if (r.getType().equals(dependencyType)) {
				relation = r;
				if (r.getEntity()==null) continue;
				if (r.getEntity().getQualifiedName().equals(dependedEntityFullName))
					return;
			}
		}
		if (relation==null) {
			fail("cannot found relation type of "+ dependencyType);
		}else {
			fail("cannot found relation type of " + dependencyType + " to entity " + dependedEntityFullName);
		}
	}
	
	protected void assertContainsVarWithRawName(Entity entity, String name) {
	    ContainerEntity container = (ContainerEntity)entity;
		ArrayList<VarEntity> vars = container.getVars();
	    for (VarEntity var:vars) {
	    	if (var.getRawName().uniqName().equals(name)) {
	    		return;
	    	}
	    }
	    fail("cannot found var with rawname " + name);
	}
	
	protected void assertContainsParametersWithRawName(FunctionEntity function, String name) {
		Collection<VarEntity> vars = function.getParameters();
	    for (VarEntity var:vars) {
	    	if (var.getRawName().uniqName().equals(name)) {
	    		return;
	    	}
	    }
	    fail("cannot found parameter with rawname " + name);		
	}

	protected void assertContainReturnType(FunctionEntity function, String name) {
		Collection<Entity> types = function.getReturnTypes();
	    for (Entity type:types) {
	    	if (type.getRawName().uniqName().equals(name)) {
	    		return;
	    	}
	    }
	    fail("cannot found return type with rawname " + name);			
	}
}
