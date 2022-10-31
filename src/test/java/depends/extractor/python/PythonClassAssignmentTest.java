package depends.extractor.python;

import depends.entity.Entity;
import depends.extractor.python.union.PythonFileParser;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class PythonClassAssignmentTest extends PythonParserTest {
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void class_assignment_should_be_treat_as_alias() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/class_assignment.py",
	    	    };
	    
	    for (String src:srcs) {
		    PythonFileParser parser = createParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
		Entity classB = entityRepo.getEntity(withPackageName(srcs[0],"class_assignment","ClassB"));
        assertNotNull(classB);
	}
}
