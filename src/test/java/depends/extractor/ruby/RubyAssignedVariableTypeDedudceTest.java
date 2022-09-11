package depends.extractor.ruby;

import depends.entity.FunctionEntity;
import depends.entity.TypeEntity;
import depends.entity.VarEntity;
import depends.extractor.FileParser;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

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
		    FileParser parser = createFileParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
	    FunctionEntity function = (FunctionEntity)(entityRepo.getEntity("Class.test"));
	    VarEntity var = function.lookupVarLocally("var_int");
	    assertEquals(TypeEntity.buildInType.getRawName(),var.getType().getRawName());

	    var = function.lookupVarLocally("var_c");
	    assertEquals("Class",var.getType().getRawName().uniqName());
	    

	    TypeEntity classEntity = (TypeEntity)(entityRepo.getEntity("Class"));
	    var = classEntity.lookupVarLocally("inst_var");
	    assertEquals("Class",var.getType().getRawName().uniqName());
	    
	    var = classEntity.lookupVarLocally("class_var");
	    assertEquals("Class",var.getType().getRawName().uniqName());
	}
	
	@Test
	public void test_compose_expression_with_operator() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/variable_assignment.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
	    FunctionEntity function = (FunctionEntity)(entityRepo.getEntity("Class.operator_is_call"));
	    VarEntity var = function.lookupVarLocally("var_compose");
	    assertEquals(TypeEntity.buildInType.getRawName(),var.getType().getRawName());
	    
	    var = function.lookupVarLocally("var_1");
	    assertEquals(TypeEntity.buildInType.getRawName(),var.getType().getRawName());

	    var = function.lookupVarLocally("var_2");
	    assertEquals(TypeEntity.buildInType.getRawName(),var.getType().getRawName());

	    var = function.lookupVarLocally("var_3");
	    assertEquals(TypeEntity.buildInType.getRawName(),var.getType().getRawName());

	    var = function.lookupVarLocally("var_4");
	    assertEquals(TypeEntity.buildInType.getRawName(),var.getType().getRawName());

	    var = function.lookupVarLocally("var_5");
	    assertEquals(TypeEntity.buildInType.getRawName(),var.getType().getRawName());

	    var = function.lookupVarLocally("var_6");
	    assertEquals(TypeEntity.buildInType.getRawName(),var.getType().getRawName());

	}
}

