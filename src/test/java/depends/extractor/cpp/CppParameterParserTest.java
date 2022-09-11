package depends.extractor.cpp;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class CppParameterParserTest extends CppParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void test_parameter() throws IOException {
	    String src = "./src/test/resources/cpp-code-examples/FunctionParameters.cpp";
	    CppFileParser parser = createParser();
        parser.parse(src);
        resolveAllBindings();
        assertEquals(4, entityRepo.getEntity("FunctionParameters.function_with_parameters_same_type").getRelations().size());
	}

}
