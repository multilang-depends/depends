package depends.extractor.ruby;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.entity.FileEntity;
import depends.util.FileUtil;

public class RubyVarTest extends RubyParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	@Test
	public void test_var_should_be_created_if_not_declared() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/auto_var.rb",
	    	    };
	    
	    for (String src:srcs) {
		    RubyFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
        FileEntity f = (FileEntity) (entityRepo.getEntity(FileUtil.uniqFilePath(srcs[0])));
        assertEquals(1,f.getVars().size());
        assertEquals("var_1",f.getVars().get(0).getRawName());
	}
	
}
