package depends.extractor.ruby;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.entity.ContainerEntity;
import depends.entity.VarEntity;
import depends.extractor.FileParser;
import depends.relations.Inferer;

public class RubyAssignedVariableTypeDedudceTest extends RubyParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	
	@Test
	public void test_variable_call_should_be_resoved_in_case_of_new() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/variable_assignment.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    ContainerEntity function = (ContainerEntity)(entityRepo.getEntity("Class.test"));
	    VarEntity var = function.lookupVarLocally("var_int");
	    assertEquals(Inferer.buildInType.getRawName(),var.getType().getRawName());

	    var = function.lookupVarLocally("var_c");
	    assertEquals("Class",var.getType().getRawName());
	    

	    function = (ContainerEntity)(entityRepo.getEntity("Class"));
	    var = function.lookupVarLocally("inst_var");
	    assertEquals("Class",var.getType().getRawName());
	    
	    var = function.lookupVarLocally("class_var");
	    assertEquals("Class",var.getType().getRawName());
	}
	
	@Test
	public void test_compose_expression_with_operator() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/variable_assignment.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    ContainerEntity function = (ContainerEntity)(entityRepo.getEntity("Class.operator_is_call"));
	    VarEntity var = function.lookupVarLocally("var_compose");
	    assertEquals(Inferer.buildInType.getRawName(),var.getType().getRawName());
	    
	    var = function.lookupVarLocally("var_1");
	    assertEquals(Inferer.buildInType.getRawName(),var.getType().getRawName());

	    var = function.lookupVarLocally("var_2");
	    assertEquals(Inferer.buildInType.getRawName(),var.getType().getRawName());

	    var = function.lookupVarLocally("var_3");
	    assertEquals(Inferer.buildInType.getRawName(),var.getType().getRawName());

	    var = function.lookupVarLocally("var_4");
	    assertEquals(Inferer.buildInType.getRawName(),var.getType().getRawName());

	    var = function.lookupVarLocally("var_5");
	    assertEquals(Inferer.buildInType.getRawName(),var.getType().getRawName());

	    var = function.lookupVarLocally("var_6");
	    assertEquals(Inferer.buildInType.getRawName(),var.getType().getRawName());

	}
}

