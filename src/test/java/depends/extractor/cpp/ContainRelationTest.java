package depends.extractor.cpp;
import depends.deptypes.DependencyType;
import multilang.depends.util.file.FileUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

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
		    CppFileParser parser = createParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
        assertEquals(1, entityRepo.getEntity("UnderTest").getRelations().size());
	}
	
	@Test
	public void included_file_contains() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/relationContain/ContainTest.h",
	    		"./src/test/resources/cpp-code-examples/relationContain/BeContained.h",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = createParser();
		    parser.parse(FileUtil.uniqFilePath(src));
	    }
	    resolveAllBindings();
        assertEquals(2, entityRepo.getEntity("UnderTest").getRelations().size());
	}
	
	@Test
	public void precedence_declaration_file_contains() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/relationContain/PrecedenceDeclaration.hpp",
	    		"./src/test/resources/cpp-code-examples/relationContain/BeContained.h",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = createParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
	    this.assertContainsRelation(entityRepo.getEntity("UnderTest"), DependencyType.CONTAIN, "Member");
	}
	
	
	@Test
	public void precedence_used_in_impl_file() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/relationContain/PrecedenceDeclaration.cpp",
	    		"./src/test/resources/cpp-code-examples/relationContain/PrecedenceDeclaration.hpp",
	    		"./src/test/resources/cpp-code-examples/relationContain/BeContained.h",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = createParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
	}
	
	
}