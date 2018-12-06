package depends.extractor.cpp;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.entity.Entity;
import depends.extractor.cpp.CppFileParser;
import depends.format.matrix.DependencyMatrix;
import depends.format.matrix.FileDependencyGenerator;

public class ForwardDeclareTest extends CppParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void should_slove_forward_declare_in_cpp() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/forwardDeclare/Mutex.h",
	    		"./src/test/resources/cpp-code-examples/forwardDeclare/App.h",
	    		"./src/test/resources/cpp-code-examples/forwardDeclare/App.cpp",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
        File f = new File(srcs[0]);
        Entity e = repo.getEntity("App.foo");
        FileDependencyGenerator dependencyGenerator= new FileDependencyGenerator();
        DependencyMatrix dependencyMatrix = dependencyGenerator.buildWithRemap(repo);
	}
	
}
