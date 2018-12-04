package depends.extractor.cpp;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.extractor.cpp.cdt.CdtCppFileParser;

public class MacroRelationTest extends CppParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void macro_var_relation() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/MacroRelationTest.cpp",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = new  CdtCppFileParser(src,repo, preprocessorHandler );
		    parser.parse();
	    }
        repo.resolveAllBindings();
        assertEquals(1,repo.getEntity("foo").getRelations().size());
	}
	
	@Test
	public void macro_var_relation_in_seperate_file() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/MacroRelationTestInSeperateFile.h",
	    		"./src/test/resources/cpp-code-examples/MacroRelationTestInSeperateFile.cpp",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = new  CdtCppFileParser(src,repo, preprocessorHandler );
		    parser.parse();
	    }
        repo.resolveAllBindings();
        assertEquals(1,repo.getEntity("foo").getRelations().size());
	}
	
	@Test
	public void macro_func_relation() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/MacroRelationTest.cpp",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = new  CdtCppFileParser(src,repo, preprocessorHandler );
		    parser.parse();
	    }
        repo.resolveAllBindings();
        assertEquals(1,repo.getEntity("bar").getRelations().size());
	}
	
	
	
	
}