package depends.extractor.python;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
import depends.entity.FunctionEntity;
import depends.entity.TypeEntity;
import depends.extractor.FileParser;
import depends.extractor.pom.PomFileParser;

public class PythonClassTest extends PythonParserTest {
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void should_parse_class() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/class.py",
	    	    };
	    
	    for (String src:srcs) {
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
        assertNotNull(repo.getEntity("Foo"));
	}
	
	@Test
	public void should_parse_method_of_class() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/class.py",
	    	    };
	    
	    for (String src:srcs) {
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    TypeEntity type = (TypeEntity)repo.getEntity("Foo");
        assertEquals(2,type.getFunctions().size());
	}
	
	@Test
	public void should_parse_baseclass_of_class() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/class.py",
	    	    };
	    
	    for (String src:srcs) {
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    TypeEntity type = (TypeEntity)repo.getEntity("Bar");
	    this.assertContainsRelation(type, DependencyType.INHERIT, "Foo");
	}


}
