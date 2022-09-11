package depends.extractor.ruby;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.extractor.FileParser;

public class RubyMixinTest extends RubyParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	@Test
	public void test_mixin_relation() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/mix_in.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
	    assertContainsRelation(entityRepo.getEntity("MixedIn"),DependencyType.MIXIN,"ToBeMixin");

	}

}
