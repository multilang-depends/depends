package depends.extractor.ruby;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.entity.ContainerEntity;
import depends.entity.FunctionEntity;
import depends.entity.TypeEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.FileParser;

public class RubyVarTest extends RubyParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	
	@Test
	public void test_parameter_should_be_created() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/auto_var.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser();
		    parser.parse(src);
	    }
	    FunctionEntity function = (FunctionEntity)(entityRepo.getEntity("method1"));
	    assertEquals(1,function.getParameters().size());
	    assertContainsParametersWithRawName(function, "param1");
	}
	

	@Test
	public void test_var_should_be_created_if_not_declared() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/auto_var.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser();
		    parser.parse(src);
	    }
	    FunctionEntity function = (FunctionEntity)(entityRepo.getEntity("method_with_local_var"));
	    assertEquals(1,function.getVars().size());
	    assertContainsVarWithRawName(function, "var_1");
	}


	@Test
	public void test_var_should_only_create_once() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/auto_var.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser();
		    parser.parse(src);
	    }
	    FunctionEntity function = (FunctionEntity)(entityRepo.getEntity("method_with_local_var_2times"));
	    assertEquals(1,function.getVars().size());
	}
	
	@Test
	public void test_var_should_not_be_created_if_it_actually_parameter() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/auto_var.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser();
		    parser.parse(src);
	    }
	    FunctionEntity function = (FunctionEntity)(entityRepo.getEntity("method_without_local_var_and_param"));
	    assertEquals(0,function.getVars().size());
	}
	

	@Test
	public void test_var_should_not_be_created_if_it_actually_a_file_level_var() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/auto_var.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser();
		    parser.parse(src);
	    }
	    FunctionEntity function = (FunctionEntity)(entityRepo.getEntity("method_access_file_level_var"));
	    assertEquals(0,function.getVars().size());
	}
	
	@Test
	public void test_var_should_not_be_created_with_a_lot_of_levels() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/auto_var.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser();
		    parser.parse(src);
	    }
	    FunctionEntity function = (FunctionEntity)(entityRepo.getEntity("M.C.method"));
	    assertEquals(1,function.getVars().size());
	}
	@Test
	public void test_var_should_not_be_created_not_in_scope() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/auto_var.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser();
		    parser.parse(src);
	    }
	    FunctionEntity function = (FunctionEntity)(entityRepo.getEntity("M.C.method2"));
	    assertEquals(1,function.getVars().size());
	}
	
	
	@Test
	public void test_var_should_created_at_class_level() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/auto_var.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser();
		    parser.parse(src);
	    }
	    TypeEntity c = (TypeEntity)(entityRepo.getEntity("M.C"));
	    assertEquals(3,c.getVars().size());
	    assertContainsVarWithRawName(c,"class_level_var");
	    assertContainsVarWithRawName(c,"class_var");
	    assertContainsVarWithRawName(c,"instance_var");
	}
	
	@Test
	public void test_var_in_block() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/auto_var.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser();
		    parser.parse(src);
	    }
	    ContainerEntity c = (ContainerEntity)(entityRepo.getEntity("Block"));
	    assertEquals(1,c.getVars().size());
	    assertContainsVarWithRawName(c,"a");
	}
	
	@Test
	public void test_global_var()throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/auto_var.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser();
		    parser.parse(src);
	    }
	    ContainerEntity c = (ContainerEntity)(entityRepo.getEntity(EntityRepo.GLOBAL_SCOPE_NAME));
	    assertEquals(1,c.getVars().size());
	    assertContainsVarWithRawName(c,"global_var");
	}
}

