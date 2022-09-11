package depends.extractor.java;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;

public class JavaGenericTypeTest extends JavaParserTest{
	@Before
	public void setUp() {
		super.init();
	}
	
	@Test
	public void test_GenericTypeShouldBeIdentified() throws IOException {
        String src = "./src/test/resources/java-code-examples/GenericTypeTest.java";
        JavaFileParser parser = createParser();
        parser.parse(src);
        resolveAllBindings();
        this.assertContainsRelation(entityRepo.getEntity("x.GenericTypeTest.v"),
        		DependencyType.PARAMETER, "x.Parent2.Enum");
	}
	
	@Test
	public void test_GenericTypeEmbededShouldBeIdentified() throws IOException {
        String src = "./src/test/resources/java-code-examples/GenericTypeEmbededTest.java";
        JavaFileParser parser = createParser();
        parser.parse(src);
        resolveAllBindings();
        this.assertContainsRelation(entityRepo.getEntity("GenericTypeEmbededTest"),
        		DependencyType.CONTAIN, "MyHashMap");
        this.assertContainsRelation(entityRepo.getEntity("GenericTypeEmbededTest.data"),
        		DependencyType.PARAMETER, "MyList");
        this.assertContainsRelation(entityRepo.getEntity("GenericTypeEmbededTest.data"),
        		DependencyType.PARAMETER, "MyArray");
	}
}
