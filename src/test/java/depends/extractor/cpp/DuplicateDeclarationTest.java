package depends.extractor.cpp;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.extractor.cpp.cdt.CdtCppFileParser;

public class DuplicateDeclarationTest extends CppParserTest {
	 @Before
	    public void setUp() {
	    	super.init();
	    }
		
		@Test
		public void duplication_declaration_should_be_resolved() throws IOException {
			
			 String[] srcs = new String[] {
			    		"./src/test/resources/cpp-code-examples/DuplicationDeclarationCouldBeResolved.cpp",
			    	    };
			    
			    for (String src:srcs) {
				    CppFileParser parser = new  CdtCppFileParser(src,repo, preprocessorHandler );
				    parser.parse();
			    }
		        repo.resolveAllBindings();
		        assertEquals(2,repo.getEntity("X.invoke").getRelations().size());
		}
}
