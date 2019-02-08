package depends.extractor;

import static org.junit.Assert.fail;

import depends.entity.Entity;
import depends.relations.Relation;

public abstract class ParserTest {
	protected void assertContainsRelation(Entity inEntity, String dependencyType, String dependedEntityFullName) {
		Relation relation = null;
		for (Relation r:inEntity.getRelations()) {
			if (r.getType().equals(dependencyType)) {
				relation = r;
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
}
