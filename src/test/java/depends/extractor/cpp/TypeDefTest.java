package depends.extractor.cpp;
import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.extractor.cpp.cdt.CdtCppFileParser;

public class TypeDefTest extends CppParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void test_ref_parameter() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/TypeDefTest.cpp",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = new  CdtCppFileParser(src,repo, preprocessorHandler,inferer );
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
        this.assertContainsRelation(repo.getEntity("foo"), DependencyType.PARAMETER, "MyInt");
	}
			
}