package depends.entity;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.Relation;
import depends.entity.repo.EntityRepo;
import depends.entity.types.FileEntity;
import depends.entity.types.PackageEntity;
import depends.entity.types.TypeEntity;
import depends.extractor.java.JavaHandler;
import depends.format.matrix.DependencyMatrix;
import depends.format.matrix.FileDependencyGenerator;

public class EntityRepoExpendsImportsOfPackageTest {

	private static final String packageName = "packagex";

	@Test
	public void testExpendsPackageImports() {
		EntityRepo entityRepo = new EntityRepo();

		JavaHandler visitor = new JavaHandler(entityRepo);
		visitor.startFile("/tmp/fileA.java");
		visitor.foundPackageDeclaration(packageName);
		visitor.startFile("/tmp/fileB.java");
		visitor.foundPackageDeclaration(packageName);
		
		visitor.startFile("/tmp/thefile.java");
		visitor.foundImport(packageName);
		
		entityRepo.resolveAllBindings();
        entityRepo.expendsPackageImports();
		
		FileDependencyGenerator dependencyGenerator= new FileDependencyGenerator();
        DependencyMatrix dependencyMatrix = dependencyGenerator.build(entityRepo);
//        dependencyMatrix = dependencyGenerator.build(entityRepo);
        assertEquals(2, dependencyMatrix.relationCount().intValue());
	}
	
	@Test
	public void testNormalImportNoNeedExpansion() {
		EntityRepo entityRepo = new EntityRepo();

		JavaHandler visitor = new JavaHandler(entityRepo);
		visitor.startFile("/tmp/fileA.java");
		visitor.foundPackageDeclaration(packageName);
		visitor.foundClassOrInterface("ClassA");;

		visitor.startFile("/tmp/fileB.java");
		visitor.foundPackageDeclaration(packageName);
		
		visitor.startFile("/tmp/thefile.java");
		visitor.foundImport(packageName+".ClassA");
		
		entityRepo.resolveAllBindings();
		
		FileDependencyGenerator dependencyGenerator= new FileDependencyGenerator();
        DependencyMatrix dependencyMatrix = dependencyGenerator.build(entityRepo);
        assertEquals(1, dependencyMatrix.relationCount().intValue());
	}

}
