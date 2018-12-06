package depends.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import depends.extractor.java.JavaHandlerContext;
import depends.extractor.java.JavaParserTest;
import depends.importtypes.ExactMatchImport;
import depends.importtypes.PackageWildCardImport;
import depends.matrix.DependencyMatrix;
import depends.matrix.FileDependencyGenerator;

public class EntityRepoExpendsImportsOfPackageTest extends JavaParserTest  {

	private static final String packageName = "packagex";
	@Before
	public void setUp() {
		super.init();
	}
	@Test
	public void testExpendsPackageImports() {
		JavaHandlerContext visitor = new JavaHandlerContext(entityRepo);
		visitor.startFile("/tmp/fileA.java");
		visitor.foundNewPackage(packageName);
		visitor.startFile("/tmp/fileB.java");
		visitor.foundNewPackage(packageName);
		
		visitor.startFile("/tmp/thefile.java");
		visitor.foundNewImport(new PackageWildCardImport(packageName));
		
		inferer.resolveAllBindings();
		
		FileDependencyGenerator dependencyGenerator= new FileDependencyGenerator();
        DependencyMatrix dependencyMatrix = dependencyGenerator.build(entityRepo);
//        dependencyMatrix = dependencyGenerator.build(entityRepo);
	}
	
	@Test
	public void testNormalImportNoNeedExpansion() {
		JavaHandlerContext context = new JavaHandlerContext(entityRepo);
		context.startFile("/tmp/fileA.java");
		context.foundNewPackage(packageName);
		context.foundNewType("ClassA");;

		context.startFile("/tmp/fileB.java");
		context.foundNewPackage(packageName);
		
		context.startFile("/tmp/thefile.java");
		context.foundNewImport(new ExactMatchImport(packageName+".ClassA"));
		
		inferer.resolveAllBindings();
		
		FileDependencyGenerator dependencyGenerator= new FileDependencyGenerator();
        DependencyMatrix dependencyMatrix = dependencyGenerator.build(entityRepo);
        assertEquals(1, dependencyMatrix.relationCount().intValue());
	}

}
