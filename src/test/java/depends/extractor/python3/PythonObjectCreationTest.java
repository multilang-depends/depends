package depends.extractor.python3;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.FunctionEntity;
import depends.extractor.python.py3.Python3FileParser;

public class PythonObjectCreationTest extends Python3ParserTest {
    @Before
    public void setUp() {
    	super.init();
    }

	
	@Test
	public void could_parse_object_creation() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/object_creation.py",
	    	    };
	    
	    for (String src:srcs) {
		    Python3FileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    FunctionEntity func = (FunctionEntity)repo.getEntity(withPackageName(srcs[0],"foo"));
	    this.assertContainsRelation(func, DependencyType.CREATE, withPackageName(srcs[0],"Bar"));
	}

}
