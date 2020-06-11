package depends.extractor.python;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.FunctionEntity;
import depends.entity.MultiDeclareEntities;
import depends.extractor.python.union.PythonFileParser;
import multilang.depends.util.file.FileUtil;

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
		this.assertContainsRelation(file, DependencyType.IMPORT,FileUtil.uniqFilePath(srcs[1]));
		this.assertContainsRelation(file, DependencyType.CALL,withPackageName(srcs[1],"foo"));
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
	
	@Test
	public void should_parse_import_with_prefix_dots2() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/import_with_dir/subdir/importing2.py",
	    	    };
	    
	    for (String src:srcs) {
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	}
	
	
	@Test
	public void should_import_from_package__init__file() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/import_from_init/importing.py",
	    		"./src/test/resources/python-code-examples/import_from_init/pkg/__init__.py",
	    	    };
	    
	    for (String src:srcs) {
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
		this.assertContainsRelation(repo.getEntity(FileUtil.uniqFilePath(srcs[0])), DependencyType.IMPORT,FileUtil.uniqFilePath(srcs[1]));
	}
	
	
	@Test
	public void should_not_bypass_import_in_same_dir() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/import_of_same_dir/pkg/importing.py",
	    		"./src/test/resources/python-code-examples/import_of_same_dir/pkg/a.py",
	    	    };
	   
	    for (String src:srcs) {
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
		this.assertContainsRelation(repo.getEntity(FileUtil.uniqFilePath(srcs[0])), DependencyType.IMPORT,FileUtil.uniqFilePath(srcs[1]));
	}
	
	
	@Test
	public void should_resolve_symbols_of_imported_in_same_dir() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/import_of_same_dir/pkg/importing.py",
	    		"./src/test/resources/python-code-examples/import_of_same_dir/pkg/a.py",
	    	    };
	   
	    for (String src:srcs) {
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    FunctionEntity func = (FunctionEntity)repo.getEntity(withPackageName(srcs[0],"test"));
	    this.assertContainsRelation(func, DependencyType.CALL, withPackageName(srcs[1],"foo"));
	}
	
	
	
	@Test
	public void should_resolve_symbols_of_ducktyping() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/duck_typing/forest.py",
	    		"./src/test/resources/python-code-examples/duck_typing/animals.py",
	    		"./src/test/resources/python-code-examples/duck_typing/controller.py",
	    	    };
	   
	    for (String src:srcs) {
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    
	    MultiDeclareEntities funcs = (MultiDeclareEntities)repo.getEntity(withPackageName(srcs[1],"in_the_forest"));
	    Entity func = funcs.getEntities().get(0);

	    this.assertContainsRelation(func, DependencyType.CALL, withPackageName(srcs[1],"Duck.quack"));
	    this.assertContainsRelation(func, DependencyType.CALL, withPackageName(srcs[1],"Doge.quack"));
	    this.assertContainsRelation(func, DependencyType.CALL, withPackageName(srcs[1],"Bird.quack"));
	}

	@Test
	public void should_resolve_symbols_of_ducktyping2() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/duck_typing/animals.py",
	    		"./src/test/resources/python-code-examples/duck_typing/forest.py",
	    		"./src/test/resources/python-code-examples/duck_typing/controller.py",
	    	    };
	   
	    for (String src:srcs) {
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    
	    MultiDeclareEntities funcs = (MultiDeclareEntities)repo.getEntity(withPackageName(srcs[1],"in_the_forest"));
	    Entity func = funcs.getEntities().get(0);
	    this.assertContainsRelation(func, DependencyType.CALL, withPackageName(srcs[0],"Duck.quack"));
	    this.assertContainsRelation(func, DependencyType.CALL, withPackageName(srcs[0],"Bird.quack"));
	    this.assertContainsRelation(func, DependencyType.CALL, withPackageName(srcs[0],"Doge.quack"));
	}
	
	@Test
	public void should_resolve_imported_symbols() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/import_from_init/use_imported.py",
	    		"./src/test/resources/python-code-examples/import_from_init/pkg/__init__.py",
	    		"./src/test/resources/python-code-examples/import_from_init/pkg/core.py",
	    	    };
	   
	    for (String src:srcs) {
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    FunctionEntity func = (FunctionEntity)repo.getEntity(withPackageName(srcs[0],"bar"));
	    this.assertContainsRelation(func, DependencyType.CALL, withPackageName(srcs[2],"C"));
	}

	@Test
	public void should_resolve_imported_vars() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/import_from_var/use_imported.py",
	    		"./src/test/resources/python-code-examples/import_from_var/pkg/core.py",
	    	    };
	   
	    for (String src:srcs) {
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    FileEntity f = (FileEntity)repo.getEntity(FileUtil.uniqFilePath(srcs[0]));
	    this.assertContainsRelation(f, DependencyType.CALL, withPackageName(srcs[1],"Core.foo"));
	}

}
