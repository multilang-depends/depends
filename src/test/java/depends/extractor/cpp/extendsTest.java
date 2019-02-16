package depends.extractor.cpp;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.entity.Entity;

public class extendsTest extends CppParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void full_qualified_names_should_be_resolved() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/inheritTest.hpp",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
        Entity e = repo.getEntity("B");
        assertEquals(2,e.getRelations().size());
	}
	
}
