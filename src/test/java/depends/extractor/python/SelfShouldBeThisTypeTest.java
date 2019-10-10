package depends.extractor.python;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.entity.FunctionEntity;
import depends.entity.TypeEntity;
import depends.entity.VarEntity;
import depends.extractor.FileParser;

public class SelfShouldBeThisTypeTest extends PythonParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	
	@Test
	public void test_deduce_type_of_return() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/self_should_be_this_type.py",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    FunctionEntity function = (FunctionEntity)(repo.getEntity(withPackageName(srcs[0],"A.foo")));
	    VarEntity var = function.lookupVarLocally("self");
	    TypeEntity type = var.getType();
	    assertTrue(type.getQualifiedName().equals(withPackageName(srcs[0],"A")));
	}
}

