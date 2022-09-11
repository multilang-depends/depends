package depends.extractor.ruby;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.FunctionEntity;
import depends.extractor.FileParser;

public class RubyClassMethodcallTest extends RubyParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	
	@Test
	public void test_class_method_call() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/class_method_call.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
	    FunctionEntity function = (FunctionEntity)(entityRepo.getEntity("called_from"));
	    this.assertContainsRelation(function, DependencyType.CALL, "Foo1.bar");
	    this.assertContainsRelation(function, DependencyType.CALL, "Foo2.bar");
	    this.assertContainsRelation(function, DependencyType.CALL, "Foo3.bar");
	}
	
}

