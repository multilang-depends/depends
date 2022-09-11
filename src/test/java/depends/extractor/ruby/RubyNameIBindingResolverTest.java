package depends.extractor.ruby;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.FunctionEntity;
import depends.extractor.FileParser;

public class RubyNameIBindingResolverTest extends RubyParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	
	@Test
	public void test_infer_function_in_multiple_module() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/ruby_name_infer.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
	    FunctionEntity function = (FunctionEntity)(entityRepo.getEntity("M1.test"));
	    this.assertContainsRelation(function, DependencyType.CALL, "M1.f1");
	    this.assertContainsRelation(function, DependencyType.CALL, "M1.f2");
	}
	
	@Test
	public void test_infer_function_should_distinguish_global_and_local() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/ruby_name_infer_with_range.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
	    FunctionEntity function = (FunctionEntity)(entityRepo.getEntity("M2.M1.test2"));
	    this.assertContainsRelation(function, DependencyType.CALL, "M2.M1.f1");
	    function = (FunctionEntity)(entityRepo.getEntity("M2.M1.test1"));
	    this.assertContainsRelation(function, DependencyType.CALL, "M1.f1");

	}
}

