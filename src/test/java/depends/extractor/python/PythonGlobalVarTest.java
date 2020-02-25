package depends.extractor.python;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
import depends.extractor.python.union.PythonFileParser;

public class PythonGlobalVarTest extends PythonParserTest {
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void should_detect_global_reference() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/global_test/file1.py",
	    		"./src/test/resources/python-code-examples/global_test/file2.py",
	    	    };
	    
	    for (String src:srcs) {
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings(true,null);
        Entity foo = repo.getEntity(withPackageName(srcs[0],"foo"));
        this.assertContainsRelation(foo, DependencyType.IMPLLINK, "global_var");
	}
	

}
