package depends.extractor.cpp;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;

public class TypeDefTest extends CppParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void test_ref_parameter() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/TypeDefTest.cpp",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = createParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
        this.assertContainsRelation(entityRepo.getEntity("foo"), DependencyType.PARAMETER, "MyInt");
	}
			
}