package depends.extractor.ruby;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.entity.FunctionEntity;
import depends.extractor.FileParser;

public class RubyVCallTest extends RubyParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	
	@Test
	public void test_parameter_should_be_created() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/vcall.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    FunctionEntity function = (FunctionEntity)(entityRepo.getEntity("called_from"));
	    assertEquals(1,function.getRelations().size());
	}
	
}

