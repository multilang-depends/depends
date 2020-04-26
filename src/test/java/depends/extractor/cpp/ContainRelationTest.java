package depends.extractor.cpp;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.extractor.cpp.cdt.CdtCppFileParser;

public class ContainRelationTest extends CppParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void same_file_contains() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/relationContain/SameFileContainTest.hpp",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
        assertEquals(1,repo.getEntity("UnderTest").getRelations().size());
	}
	
	@Test
	public void included_file_contains() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/relationContain/ContainTest.h",
	    		"./src/test/resources/cpp-code-examples/relationContain/BeContained.h",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
        assertEquals(2,repo.getEntity("UnderTest").getRelations().size());
	}
	
	@Test
	public void precedence_declaration_file_contains() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/relationContain/PrecedenceDeclaration.hpp",
	    		"./src/test/resources/cpp-code-examples/relationContain/BeContained.h",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    this.assertContainsRelation(repo.getEntity("UnderTest"), DependencyType.CONTAIN, "Member");
	}
	
	
	@Test
	public void precedence_used_in_impl_file() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/relationContain/PrecedenceDeclaration.cpp",
	    		"./src/test/resources/cpp-code-examples/relationContain/PrecedenceDeclaration.hpp",
	    		"./src/test/resources/cpp-code-examples/relationContain/BeContained.h",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	}
	
	
}