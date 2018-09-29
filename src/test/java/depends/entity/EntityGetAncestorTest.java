package depends.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import depends.entity.repo.EntityNotExistsException;
import depends.entity.repo.EntityRepo;
import depends.entity.repo.NoRequestedTypeOfAncestorExistsException;
import depends.entity.types.FileEntity;
import depends.entity.types.TypeEntity;
import depends.entity.types.VarEntity;


public class EntityGetAncestorTest {
	private int fileId = 0;
	private int typeId = 1;
	private int not_exist_id = 2;
	EntityRepo entityRepo = new EntityRepo();
	@Before
	public void setup() {
		//create a file 
		FileEntity f = new FileEntity("/tmp/file.java",fileId);
		TypeEntity p = new TypeEntity("test.packagenamea.ClassA",  f,typeId);
		entityRepo.add(f);
		entityRepo.add(p);
		entityRepo.resolveAllBindings();
	}
	@Test
	public void testGetAncestorOfType() throws EntityNotExistsException, NoRequestedTypeOfAncestorExistsException {
		int fileId = entityRepo.getAncestorOfType(typeId,FileEntity.class);
		assertEquals(0,fileId);
	}
	
	@Test
	public void testGetAncestorOfType_beself() throws EntityNotExistsException, NoRequestedTypeOfAncestorExistsException {
		int r = entityRepo.getAncestorOfType(typeId,TypeEntity.class);
		assertEquals(1,r);
	}
	
	@Test(expected=EntityNotExistsException.class)
	public void testGetAncestorOfType_not_exists_entity() throws EntityNotExistsException, NoRequestedTypeOfAncestorExistsException {
		int fileId = entityRepo.getAncestorOfType(not_exist_id,FileEntity.class);
		assertEquals(0,fileId);
	}
	
	
	@Test(expected=NoRequestedTypeOfAncestorExistsException.class)
	public void testGetAncestorOfType_not_exists_ancestor() throws EntityNotExistsException, NoRequestedTypeOfAncestorExistsException {
		int fileId = entityRepo.getAncestorOfType(typeId,VarEntity.class);
		assertEquals(0,fileId);
	}
}
