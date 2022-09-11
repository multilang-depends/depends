package depends.extractor.ruby;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.entity.PackageEntity;
import depends.extractor.FileParser;

public class RubyModuleLevelsTest extends RubyParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	
	@Test
	public void test_parameter_should_be_created() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/ruby_modules.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser();
		    parser.parse(src);
	    }
	    PackageEntity module = (PackageEntity)(entityRepo.getEntity("A.B"));
	    assertNotNull(module);
	    module = (PackageEntity)(entityRepo.getEntity("X.Y.Z"));
	    assertNotNull(module);
	    module = (PackageEntity)(entityRepo.getEntity("L.M.N"));
	    assertNotNull(module);
	}
}

