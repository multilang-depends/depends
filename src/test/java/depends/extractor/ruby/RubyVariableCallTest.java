package depends.extractor.ruby;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.FunctionEntity;
import depends.extractor.FileParser;

public class RubyVariableCallTest extends RubyParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	
	@Test
	public void test_variable_call_should_be_resoved_in_case_of_new() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/variable_call.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    FunctionEntity function = (FunctionEntity)(entityRepo.getEntity("test"));
	    this.assertContainsRelation(function, DependencyType.CALL, "Class.function");
	}
	
}

