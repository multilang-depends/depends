package depends.extractor.python;

import depends.extractor.python.union.PythonFileParser;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ImportWithSameNameTest extends PythonParserTest {
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void should_parse_class() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/import_with_same_name/pkg/a.py",
				"./src/test/resources/python-code-examples/import_with_same_name/b.py",
	    	    };
	    
	    for (String src:srcs) {
		    PythonFileParser parser = createParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
	    System.out.println("he");
		//this.assertContainsRelation(type, DependencyType.INHERIT, withPackageName(srcs[0],"Foo"));
	}
}
