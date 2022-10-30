package depends.extractor.python;

import depends.deptypes.DependencyType;
import depends.entity.FunctionEntity;
import depends.extractor.FileParser;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ShouldGetRelationInSamePackageTest extends PythonParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	
	@Test
	public void test_relations_of_same_package() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/relations_of_same_package/a.py",
	    		"./src/test/resources/python-code-examples/relations_of_same_package/b.py"
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
	    FunctionEntity function = (FunctionEntity)(entityRepo.getEntity(withPackageName(srcs[0],"b","bar")));
	    this.assertContainsRelation(function, DependencyType.CALL, withPackageName(srcs[0],"a","foo"));
	}
}

