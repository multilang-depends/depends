package depends.extractor.ruby;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.extractor.FileParser;
import depends.relations.Relation;

public class RubyObjectCreationTest extends RubyParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	@Test
	public void test_relation() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/object_creation.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
        assertEquals(1,entityRepo.getEntity("T.foo").getRelations().size());
        Relation r = entityRepo.getEntity("T.foo").getRelations().get(0);
        assertEquals(DependencyType.INHERIT,r.getType());
        assertEquals("Animal",r.getEntity().getRawName());
	}
	

}
