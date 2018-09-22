package depends.entity;

import static org.junit.Assert.*;

import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.Relation;
import depends.entity.repo.EntityRepo;
import depends.entity.types.FileEntity;
import depends.entity.types.PackageEntity;
import depends.entity.types.TypeEntity;

public class EntityResolveBindingsTest {

	@Test
	public void testResolveBindings() {
		EntityRepo entityRepo = new EntityRepo();

		//create a file 
		FileEntity f = new FileEntity("/tmp/file.java",0);
		TypeEntity p = new TypeEntity("test.packagenamea.ClassA",  0,1);
		//build a entity which have a relation to p
		FileEntity theEntity = new FileEntity("/tmp/file2.java", 2); //we do not care file id and parent id
		Relation r = new Relation(DependencyType.RELATION_IMPORT, "test.packagenamea.ClassA");
		theEntity.addRelation(r);
		
		entityRepo.add(f);
		entityRepo.add(p);
		entityRepo.add(theEntity);
		
		entityRepo.resolveAllBindings();
		
		assertEquals(1,theEntity.getRelations().get(0).getToId().intValue());
	}

}
