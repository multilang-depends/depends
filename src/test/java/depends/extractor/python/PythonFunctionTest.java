package depends.extractor.python;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.extractor.pom.PomFileParser;

public class PythonFunctionTest extends PythonParserTest {
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void should_parse_methods() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/func.py",
	    	    };
	    
	    for (String src:srcs) {
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
        assertEquals(0,repo.getEntity("foo").getRelations().size());
	}
}
