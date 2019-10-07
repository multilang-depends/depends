package depends.extractor.cpp;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;

public class GenericTypeTest extends CppParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void test_templateSpecializationOfStruct() throws IOException {
	    String src = "./src/test/resources/cpp-code-examples/GenericTypes.cpp";
	    CppFileParser parser = createParser(src);
        parser.parse();
        inferer.resolveAllBindings();
        assertNotNull(repo.getEntity("hash"));
	}

    @Test
	public void test_genericTypesVarDefinition() throws IOException {
	    String src = "./src/test/resources/cpp-code-examples/GenericTypes.cpp";
	    CppFileParser parser = createParser(src);
        parser.parse();
        inferer.resolveAllBindings();
        this.assertContainsRelation(repo.getEntity("xStack"), DependencyType.PARAMETER, "X");
	}
	
    @Test
	public void test_extendGenericTypes() throws IOException {
	    String src = "./src/test/resources/cpp-code-examples/GenericTypes.cpp";
	    CppFileParser parser = createParser(src);
        parser.parse();
        inferer.resolveAllBindings();
        this.assertContainsRelation(repo.getEntity("XStack"), DependencyType.INHERIT, "Stack");
	}
	
	
	

}
