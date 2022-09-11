package depends.extractor.java;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
import depends.extractor.FileParser;

public class JavaExpressionCreatorTest extends JavaParserTest{
	@Before
	public void setUp() {
		super.init();
	}
	
	@Test
	public void test_should_found_creator_with_full_path() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/java-code-examples/JavaExpressionCreator/A.java",
	    		"./src/test/resources/java-code-examples/JavaExpressionCreator/b/B.java",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
	    Entity entity = (entityRepo.getEntity("A.foo"));
	    this.assertContainsRelation(entity, DependencyType.CREATE, "b.B");
	}
	
	
	@Test
	public void test_should_found_creator_with_imported_class() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/java-code-examples/JavaExpressionCreator/X.java",
	    		"./src/test/resources/java-code-examples/JavaExpressionCreator/b/B.java",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
	    Entity entity = (entityRepo.getEntity("X.foo"));
	    this.assertContainsRelation(entity, DependencyType.CREATE, "b.B");
	}
	
	
	
}
