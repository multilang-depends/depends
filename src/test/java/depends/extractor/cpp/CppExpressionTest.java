package depends.extractor.cpp;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import depends.extractor.cpp.CppFileParser;

public class CppExpressionTest extends CppParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void test_expressions() throws IOException {
	    String src = "./src/test/resources/cpp-code-examples/Expressions.cpp";
	    CppFileParser parser = createParser(src);
        parser.parse();
        inferer.resolveAllBindings();
        assertEquals(18,repo.getEntity("foo").getRelations().size());
	}

}
