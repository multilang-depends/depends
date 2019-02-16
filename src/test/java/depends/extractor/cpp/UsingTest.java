package depends.extractor.cpp;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.entity.Entity;

public class UsingTest extends CppParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void full_qualified_names_should_be_resolved() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/UsingTest.cpp",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
        Entity e = repo.getEntity("foo");
        assertEquals(3,e.getRelations().size());
	}
	
	@Test
	public void suffix_names_should_be_resolved() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/UsingTest.cpp",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
        Entity e = repo.getEntity("bar");
        assertEquals(3,e.getRelations().size());
	}
}
