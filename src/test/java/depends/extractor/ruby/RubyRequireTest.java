package depends.extractor.ruby;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class RubyRequireTest extends RubyParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	@Test
	public void test_require_relation() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/require_1.rb",
	    		"./src/test/resources/ruby-code-examples/require_2.rb",
	    	    };
	    
	    for (String src:srcs) {
		    RubyFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
        File f = new File(srcs[0]);
        assertEquals(2,entityRepo.getEntity(f.getCanonicalPath()).getRelations().size());
	}
}
