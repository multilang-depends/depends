package depends.extractor.python;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.FunctionEntity;
import depends.extractor.python.union.PythonFileParser;

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
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    FunctionEntity func = (FunctionEntity)repo.getEntity(withPackageName(srcs[0],"foo"));
	    this.assertContainsRelation(func, DependencyType.CALL, withPackageName(srcs[0],"bar"));
	}

	@Test
	public void could_parse_func_call_of_member() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/func_call.py",
	    	    };
	    
	    for (String src:srcs) {
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    FunctionEntity func = (FunctionEntity)repo.getEntity(withPackageName(srcs[0],"baz"));
	    this.assertContainsRelation(func, DependencyType.CALL, withPackageName(srcs[0],"A.foo"));
	}
	
	@Test
	public void could_parse_func_call_of_multi_dots() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/func_call_multi_dots.py",
	    	    };
	    
	    for (String src:srcs) {
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }

	    inferer.resolveAllBindings();
	    FunctionEntity func = (FunctionEntity)repo.getEntity(withPackageName(srcs[0],"C.test"));
	    this.assertContainsRelation(func, DependencyType.CALL, withPackageName(srcs[0],"A.fooA"));
	    this.assertContainsRelation(func, DependencyType.CALL, withPackageName(srcs[0],"B.fooB"));
	    this.assertContainsRelation(func, DependencyType.CALL, withPackageName(srcs[0],"C.fooC"));
	}
}
