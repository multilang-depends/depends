package depends.extractor.python;

import depends.deptypes.DependencyType;
import depends.entity.FunctionEntity;
import depends.extractor.python.union.PythonFileParser;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class PythonFuncCallTest extends PythonParserTest {
    @Before
    public void setUp() {
    	super.init();
    }

	
	@Test
	public void could_parse_func_call() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/func_call.py",
	    	    };
	    
	    for (String src:srcs) {
		    PythonFileParser parser = createParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
	    FunctionEntity func = (FunctionEntity) entityRepo.getEntity(withPackageName(srcs[0],"foo"));
	    this.assertContainsRelation(func, DependencyType.CALL, withPackageName(srcs[0],"bar"));
	}

	@Test
	public void could_parse_func_call_of_member() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/func_call.py",
	    	    };
	    
	    for (String src:srcs) {
		    PythonFileParser parser = createParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
	    FunctionEntity func = (FunctionEntity) entityRepo.getEntity(withPackageName(srcs[0],"baz"));
	    this.assertContainsRelation(func, DependencyType.CALL, withPackageName(srcs[0],"A.foo"));
	}
	
	@Test
	public void could_parse_func_call_of_multi_dots() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/func_call_multi_dots.py",
	    	    };
	    
	    for (String src:srcs) {
		    PythonFileParser parser = createParser();
		    parser.parse(src);
	    }

	    resolveAllBindings();
	    FunctionEntity func = (FunctionEntity) entityRepo.getEntity(withPackageName(srcs[0],"func_call_multi_dots","C.test"));
	    this.assertContainsRelation(func, DependencyType.CALL, withPackageName(srcs[0],"func_call_multi_dots","A.fooA"));
	    this.assertContainsRelation(func, DependencyType.CALL, withPackageName(srcs[0],"func_call_multi_dots","B.fooB"));
	    this.assertContainsRelation(func, DependencyType.CALL, withPackageName(srcs[0],"func_call_multi_dots","C.fooC"));
	}
}
