package depends.extractor.ruby;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.relations.Relation;

public class RubyMixinTest extends RubyParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	@Test
	public void test_mixin_relation() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/mix_in.rb",
	    	    };
	    
	    for (String src:srcs) {
		    RubyFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
        assertEquals(1,entityRepo.getEntity("MixedIn").getRelations().size());
        Relation r = entityRepo.getEntity("MixedIn").getRelations().get(0);
        assertEquals(DependencyType.MIXIN,r.getType());
        assertEquals("ToBeMixin",r.getEntity().getRawName());
	}
}
