package depends.extractor.ruby;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.entity.FunctionEntity;
import depends.extractor.FileParser;

public class RubyReturnTypeDedudceTest extends RubyParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	
	@Test
	public void test_deduce_type_of_return() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/deducetype_return.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    FunctionEntity function = (FunctionEntity)(entityRepo.getEntity("Class.test"));
	    this.assertContainReturnType(function,"Class");

	    function = (FunctionEntity)(entityRepo.getEntity("Class.implicitReturn"));
	    this.assertContainReturnType(function,"Class1");
	}
}

