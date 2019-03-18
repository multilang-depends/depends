package depends.extractor.python;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
import depends.entity.FunctionEntity;
import depends.entity.TypeEntity;
import depends.extractor.FileParser;
import depends.extractor.pom.PomFileParser;
import depends.util.FileUtil;

public class PythonImportTest extends PythonParserTest {
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void should_parse_module_in_same_package() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/imported_a.py",
	    		"./src/test/resources/python-code-examples/importing.py",
	    	    };
	    
	    for (String src:srcs) {
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    Entity file = repo.getEntity(FileUtil.uniqFilePath(srcs[1]));
		this.assertContainsRelation(file, DependencyType.IMPORT,FileUtil.uniqFilePath(srcs[0]));
	}
	
	
	@Test
	public void should_parse_module_in_same_package_order_robust() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/importing.py",
	    		"./src/test/resources/python-code-examples/imported_a.py",
	    	    };
	    
	    for (String src:srcs) {
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    Entity file = repo.getEntity(FileUtil.uniqFilePath(srcs[0]));
		this.assertContainsRelation(file, DependencyType.IMPORT,FileUtil.uniqFilePath(srcs[1]));
		this.assertContainsRelation(file, DependencyType.CALL,"foo");
	}
	
	@Test
	public void should_parse_module_in_same_package_with_alias() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/importing_with_alias.py",
	    		"./src/test/resources/python-code-examples/imported_a.py",
	    	    };
	    
	    for (String src:srcs) {
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    Entity file = repo.getEntity(FileUtil.uniqFilePath(srcs[0]));
		this.assertContainsRelation(file, DependencyType.IMPORT,FileUtil.uniqFilePath(srcs[1]));
		this.assertContainsRelation(file, DependencyType.CALL,"foo");
	}
	
}
