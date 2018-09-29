package depends.format.matrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.Relation;
import depends.entity.repo.EntityRepo;
import depends.entity.types.FileEntity;
import depends.entity.types.TypeEntity;

public class FileDependencyGeneratorTest {

	private static final String FILE1_NAME = "/tmp/file.java";
	private static final String FILE2_NAME = "/tmp/file2.java";
	private static final int file1Id = 0;
	private static final int typeId = 1;

	private static final int file2Id = 2;
	EntityRepo entityRepo = null;
	@Before
	public void setUp() throws Exception {
		entityRepo = new EntityRepo();

		//create a file 
		FileEntity f = new FileEntity(FILE1_NAME,file1Id);
		TypeEntity p = new TypeEntity("test.packagenamea.ClassA",  f,typeId);
	
		FileEntity theEntity = new FileEntity(FILE2_NAME,file2Id);
		Relation r = new Relation(DependencyType.RELATION_IMPORT, "test.packagenamea.ClassA");
		theEntity.addRelation(r);
		entityRepo.add(f);
		entityRepo.add(p);
		entityRepo.add(theEntity);
		
		entityRepo.resolveAllBindings();
	}

	@Test
	public void testBuildDependencies() {
		FileDependencyGenerator dependencyGenerator= new FileDependencyGenerator();
        DependencyMatrix dependencyMatrix = dependencyGenerator.build(entityRepo);
        Map<Integer, Map<Integer, Map<String, Integer>>> relations = dependencyMatrix.getRelations();
        
        assertEquals(2,dependencyMatrix.getNodes().size());
        assertEquals(1,relations.size());
        assertTrue(relations.keySet().contains(file2Id) && relations.keySet().size()==1);
        Map<Integer, Map<String, Integer>> r = relations.get(file2Id);
        assertTrue(r.keySet().contains(file1Id) && relations.keySet().size()==1);
        Map<String, Integer> v = r.get(file1Id);
        assertTrue(v.get(DependencyType.RELATION_IMPORT).intValue()==1);
	}
	
	@Test
	public void testReMapping() {
		FileDependencyGenerator dependencyGenerator= new FileDependencyGenerator();
        DependencyMatrix dependencyMatrix = dependencyGenerator.buildWithRemap(entityRepo);
        Map<Integer, Map<Integer, Map<String, Integer>>> relations = dependencyMatrix.getRelations();
        
        assertEquals(2,dependencyMatrix.getNodes().size());
        assertEquals(1,relations.size());
        
        int file2Id = 0, file1Id = 1;
        if (!dependencyMatrix.getNodes().get(0).equals(FILE2_NAME)) {
        	file2Id = 1;
        	file1Id = 0;
        }
        	
        assertTrue(relations.keySet().contains(file2Id) && relations.keySet().size()==1);
        Map<Integer, Map<String, Integer>> r = relations.get(file2Id);
        assertTrue(r.keySet().contains(file1Id) && relations.keySet().size()==1);
        Map<String, Integer> v = r.get(file1Id);
        assertTrue(v.get(DependencyType.RELATION_IMPORT).intValue()==1);
        
	}
	
	

}
