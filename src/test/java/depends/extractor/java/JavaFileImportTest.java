package depends.extractor.java;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.TypeEntity;
import depends.extractor.FileParser;

public class JavaFileImportTest extends JavaParserTest{
	@Before
	public void setUp() {
		super.init();
	}
	
	@Test
	public void test_wildcard_import_should_be_lookedup() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/java-code-examples/JavaFileImportExample/a/Importing.java",
	    		"./src/test/resources/java-code-examples/JavaFileImportExample/b/B.java",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
	    TypeEntity type = (TypeEntity)(entityRepo.getEntity("a.Importing"));
	    this.assertContainsRelation(type, DependencyType.INHERIT, "b.B");
	}
	
	
}
