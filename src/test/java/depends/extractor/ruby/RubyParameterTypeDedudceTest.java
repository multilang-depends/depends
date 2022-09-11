package depends.extractor.ruby;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.entity.CandidateTypes;
import depends.entity.FunctionEntity;
import depends.entity.TypeEntity;
import depends.entity.VarEntity;
import depends.extractor.FileParser;

public class RubyParameterTypeDedudceTest extends RubyParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	
	@Test
	public void test_deduce_type_of_return() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/deducetype_parameter.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
	    FunctionEntity function = (FunctionEntity)(entityRepo.getEntity("test"));
	    VarEntity var = function.lookupVarLocally("t1");
	    TypeEntity type = var.getType();
	    assertTrue(type instanceof CandidateTypes);
	    assertEquals(2,((CandidateTypes)type).getCandidateTypes().size());
	}
}

