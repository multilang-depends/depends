package depends.extractor.ruby;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.FunctionEntity;
import depends.extractor.FileParser;

public class RubyVCallTest extends RubyParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	
	@Test
	public void test_vcall() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/vcall.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    FunctionEntity function = (FunctionEntity)(entityRepo.getEntity("called_from"));
	    this.assertContainsRelation(function, DependencyType.CALL, "called");
	}
	
	@Test
	public void test_continuous_call() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/continuous_func_call.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    FunctionEntity function = (FunctionEntity)(entityRepo.getEntity("test"));
	    this.assertContainsRelation(function, DependencyType.CALL, "foo");
	    this.assertContainsRelation(function, DependencyType.CALL, "bar");
	}
	
	
	
}

