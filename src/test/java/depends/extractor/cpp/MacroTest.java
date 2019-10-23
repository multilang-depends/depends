package depends.extractor.cpp;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;

public class MacroTest extends CppParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void macro_should_be_expanded() throws IOException {
	    String src = "./src/test/resources/cpp-code-examples/macros/Macro.cpp";
	    CppFileParser parser = createParser(src);
        parser.parse();
        inferer.resolveAllBindings();
        assertNotNull(this.repo.getEntity("Macro"));
	}

}
