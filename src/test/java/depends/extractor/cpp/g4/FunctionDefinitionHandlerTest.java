package depends.extractor.cpp.g4;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import depends.extractor.cpp.CppParserTest;


public class FunctionDefinitionHandlerTest extends CppParserTest {
	
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void test_parameter() throws IOException {
        String src = "./src/test/resources/cpp-code-examples/FunctionParameters.cpp";
        Antlr4CppFileParser parser = new  Antlr4CppFileParser(src,repo, new ArrayList<>() );
        parser.parse();
        repo.resolveAllBindings();
        assertEquals(4,repo.getEntity("FunctionParameters.function_with_parameters_same_type").getRelations().size());
	}

}
