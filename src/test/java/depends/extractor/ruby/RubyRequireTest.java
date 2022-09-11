package depends.extractor.ruby;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.extractor.FileParser;

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
		    FileParser parser = createFileParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
        File f = new File(srcs[0]);
        File f2 = new File(srcs[1]);
        
        super.assertContainsRelation(entityRepo.getEntity(f.getCanonicalPath()), DependencyType.IMPORT, f2.getCanonicalPath());
	}
}
