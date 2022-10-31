package depends.extractor.python;

import depends.entity.VarEntity;
import depends.extractor.python.union.PythonFileParser;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class ImportWithSameNameTest extends PythonParserTest {
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void same_name_in_different_file_should_be_treat_as_two_vars() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/import_with_same_name/pkg/a.py",
				"./src/test/resources/python-code-examples/import_with_same_name/b.py",
	    	    };
	    
	    for (String src:srcs) {
		    PythonFileParser parser = createParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
		VarEntity foo_a = (VarEntity) entityRepo.getEntity(withPackageName(srcs[0],"a","foo"));
		assertNotNull(foo_a);
		VarEntity foo_b = (VarEntity) entityRepo.getEntity(withPackageName(srcs[1],"b","foo"));
		assertNotNull(foo_b);
		assertFalse(foo_a.equals(foo_b));
	}
}
