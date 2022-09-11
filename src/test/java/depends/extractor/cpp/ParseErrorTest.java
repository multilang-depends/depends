package depends.extractor.cpp;
import depends.extractor.cpp.cdt.CdtCppFileParser;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ParseErrorTest extends CppParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void full_qualified_names_should_be_resolved() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/parseError/error1.c",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = createParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
	}

	@Test
	public void macro_definition() throws IOException {
		String[] srcs = new String[] {
				"./src/test/resources/cpp-code-examples/parseError/macro.c",
		};

		for (String src:srcs) {
			CdtCppFileParser parser = (CdtCppFileParser)createParser();

			Map<String, String> macroMap = new HashMap<>();
			macroMap.put("AP_DECLARE(x)","x");
			parser.parse(src,macroMap);
		}
		resolveAllBindings();
	}
	
}
