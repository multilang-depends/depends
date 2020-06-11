package depends.extractor.ruby;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.entity.ContainerEntity;
import depends.entity.VarEntity;
import depends.extractor.FileParser;
import multilang.depends.util.file.FileUtil;

public class RubySingletonMethodTest extends RubyParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	
	@Test
	public void test_singleton_method_should_created_in_var() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/singleton_method.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser(src);
		    parser.parse();
	    }
	    ContainerEntity file = (ContainerEntity)(entityRepo.getEntity(FileUtil.uniqFilePath(srcs[0])));
	    VarEntity var = file.lookupVarLocally("var2");
	    assertEquals(1,var.getFunctions().size());
	}
}

