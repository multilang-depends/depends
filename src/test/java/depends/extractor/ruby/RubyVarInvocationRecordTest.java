package depends.extractor.ruby;

import depends.entity.ContainerEntity;
import depends.entity.VarEntity;
import depends.extractor.FileParser;
import multilang.depends.util.file.FileUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class RubyVarInvocationRecordTest extends RubyParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	
	@Test
	public void test_singleton_method_should_created_in_var() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/var_invocation_record.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser();
		    parser.parse(src);
	    }
	    ContainerEntity file = (ContainerEntity)(entityRepo.getEntity(FileUtil.uniqFilePath(srcs[0])));
	    VarEntity var = file.lookupVarLocally("var");
	    assertEquals(1,var.getCalledFunctions().size());
	}
}


//class Class
//def foo
//end
//end
//
//var = Class.new
//var.foo

