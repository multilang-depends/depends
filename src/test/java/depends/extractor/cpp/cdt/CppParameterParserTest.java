package depends.extractor.cpp.cdt;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import depends.extractor.cpp.CppFileParser;
import depends.extractor.cpp.CppParserTest;

public class CppParameterParserTest extends CppParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void test_parameter() throws IOException {
	    String src = "./src/test/resources/cpp-code-examples/FunctionParameters.cpp";
	    CppFileParser parser = createParser(src);
        parser.parse();
        inferer.resolveAllBindings();
        assertEquals(4,repo.getEntity("FunctionParameters.function_with_parameters_same_type").getRelations().size());
	}

}
