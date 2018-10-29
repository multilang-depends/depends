package depends.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import depends.entity.repo.EntityRepo;
import depends.extractor.HandlerContext;
import depends.format.matrix.DependencyMatrix;
import depends.format.matrix.FileDependencyGenerator;

public class EntityRepoExpendsImportsOfPackageTest {

	private static final String packageName = "packagex";

	@Test
	public void testExpendsPackageImports() {
		EntityRepo entityRepo = new EntityRepo();

		HandlerContext visitor = new HandlerContext(entityRepo);
		visitor.startFile("/tmp/fileA.java");
		visitor.foundNewPackage(packageName);
		visitor.startFile("/tmp/fileB.java");
		visitor.foundNewPackage(packageName);
		
		visitor.startFile("/tmp/thefile.java");
		visitor.foundNewImport(packageName,false);
		
		entityRepo.resolveAllBindings();
		
		FileDependencyGenerator dependencyGenerator= new FileDependencyGenerator();
        DependencyMatrix dependencyMatrix = dependencyGenerator.build(entityRepo);
//        dependencyMatrix = dependencyGenerator.build(entityRepo);
        assertEquals(2, dependencyMatrix.relationCount().intValue());
	}
	
	@Test
	public void testNormalImportNoNeedExpansion() {
		EntityRepo entityRepo = new EntityRepo();

		HandlerContext context = new HandlerContext(entityRepo);
		context.startFile("/tmp/fileA.java");
		context.foundNewPackage(packageName);
		context.foundNewType("ClassA");;

		context.startFile("/tmp/fileB.java");
		context.foundNewPackage(packageName);
		
		context.startFile("/tmp/thefile.java");
		context.foundNewImport(packageName+".ClassA",false);
		
		entityRepo.resolveAllBindings();
		
		FileDependencyGenerator dependencyGenerator= new FileDependencyGenerator();
        DependencyMatrix dependencyMatrix = dependencyGenerator.build(entityRepo);
        assertEquals(1, dependencyMatrix.relationCount().intValue());
	}

}
