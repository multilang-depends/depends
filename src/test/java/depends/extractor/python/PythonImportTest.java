package depends.extractor.python;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
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
		this.assertContainsRelation(file, DependencyType.CALL,withPackageName(srcs[0],"foo"));
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
		this.assertContainsRelation(file, DependencyType.CALL,withPackageName(srcs[0],"foo"));
	}
	
	@Test
	public void should_parse_module_in_from_importing() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/from_importing.py",
	    		"./src/test/resources/python-code-examples/imported_a.py",
	    	    };
	    
	    for (String src:srcs) {
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    Entity file = repo.getEntity(FileUtil.uniqFilePath(srcs[0]));
		this.assertContainsRelation(file, DependencyType.CALL,withPackageName(srcs[0],"foo"));
		this.assertContainsRelation(file, DependencyType.IMPORT,FileUtil.uniqFilePath(srcs[1]));
	}
	
	
	@Test
	public void should_parse_module_in_from_importing_star() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/from_importing_star.py",
	    		"./src/test/resources/python-code-examples/imported_a.py",
	    	    };
	    
	    for (String src:srcs) {
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    Entity file = repo.getEntity(FileUtil.uniqFilePath(srcs[0]));
		this.assertContainsRelation(file, DependencyType.CALL,withPackageName(srcs[0],"foo"));
		this.assertContainsRelation(file, DependencyType.IMPORT,FileUtil.uniqFilePath(srcs[1]));
	}
	
	
	@Test
	public void should_parse_import_with_multi_dots() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/from_importing_multidot.py",
	    		"./src/test/resources/python-code-examples/pkg/imported.py",
	    	    };
	    
	    for (String src:srcs) {
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    Entity file = repo.getEntity(FileUtil.uniqFilePath(srcs[0]));
		this.assertContainsRelation(file, DependencyType.CALL,withPackageName(srcs[1],"foo"));
		this.assertContainsRelation(file, DependencyType.IMPORT,FileUtil.uniqFilePath(srcs[1]));
	}
	
	@Test
	public void should_parse_import_with_prefix_dots() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/import_with_dir/importing.py",
	    		"./src/test/resources/python-code-examples/import_with_dir/imported_a.py",
	    		"./src/test/resources/python-code-examples/import_with_dir/subdir/importing.py",
	    		"./src/test/resources/python-code-examples/import_with_dir/subdir/importing2.py",
	    	    };
	    
	    for (String src:srcs) {
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
		this.assertContainsRelation(repo.getEntity(FileUtil.uniqFilePath(srcs[0])), DependencyType.IMPORT,FileUtil.uniqFilePath(srcs[1]));
		this.assertContainsRelation(repo.getEntity(FileUtil.uniqFilePath(srcs[2])), DependencyType.IMPORT,FileUtil.uniqFilePath(srcs[1]));
		this.assertContainsRelation(repo.getEntity(FileUtil.uniqFilePath(srcs[3])), DependencyType.IMPORT,FileUtil.uniqFilePath(srcs[1]));
	}
}
