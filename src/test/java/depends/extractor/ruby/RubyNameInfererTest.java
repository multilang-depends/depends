package depends.extractor.ruby;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.entity.ContainerEntity;
import depends.entity.Entity;
import depends.entity.FunctionEntity;
import depends.entity.PackageEntity;
import depends.entity.TypeEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.FileParser;

public class RubyNameInfererTest extends RubyParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	
	@Test
	public void test_infer_function_in_multiple_module() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/ruby_name_infer.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    FunctionEntity function = (FunctionEntity)(entityRepo.getEntity("M1.test"));
	    this.assertContainReturnType(function, "M1");
	}
	

}

