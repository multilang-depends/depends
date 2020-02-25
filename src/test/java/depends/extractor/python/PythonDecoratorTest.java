package depends.extractor.python;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.FunctionEntity;
import depends.extractor.python.union.PythonFileParser;

public class PythonDecoratorTest extends PythonParserTest {
    @Before
    public void setUp() {
    	super.init();
    }

	
	@Test
	public void could_parse_decorated() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/decorated.py",
	    	    };
	    
	    for (String src:srcs) {
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    FunctionEntity func = (FunctionEntity)repo.getEntity(withPackageName(srcs[0],"foo"));
		this.assertContainsRelation(func, DependencyType.ANNOTATION	, withPackageName(srcs[0],"our_decorator"));
	    assertEquals(1,func.getResolvedAnnotations().size());
	}

}
