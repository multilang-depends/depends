package depends.extractor.python;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.FunctionEntity;
import depends.extractor.python.union.PythonFileParser;

public class PythonObjectThrowReturn extends PythonParserTest {
    @Before
    public void setUp() {
    	super.init();
    }

	
	@Test
	public void could_parse_throws() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/throw_return.py",
	    	    };
	    
	    for (String src:srcs) {
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    FunctionEntity func = (FunctionEntity)repo.getEntity(withPackageName(srcs[0],"t1"));
	    this.assertContainsRelation(func, DependencyType.THROW, withPackageName(srcs[0],"Bar"));
	}
	
	
	@Test
	public void could_parse_return() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/throw_return.py",
	    	    };
	    
	    for (String src:srcs) {
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    FunctionEntity func = (FunctionEntity)repo.getEntity(withPackageName(srcs[0],"t2"));
	    this.assertContainsRelation(func, DependencyType.RETURN, withPackageName(srcs[0],"Bar"));
	}


}
