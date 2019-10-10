package depends.extractor.python;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.FunctionEntity;
import depends.extractor.FileParser;

public class ShouldGetRelationInSamePackageTest extends PythonParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	
	@Test
	public void test_relations_of_sampe_package() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/relations_of_sampe_package/a.py",
	    		"./src/test/resources/python-code-examples/relations_of_sampe_package/b.py"
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    FunctionEntity function = (FunctionEntity)(repo.getEntity(withPackageName(srcs[0],"bar")));
	    this.assertContainsRelation(function, DependencyType.CALL, withPackageName(srcs[0],"foo"));
	}
}

