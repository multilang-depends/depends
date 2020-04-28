package depends.extractor.python;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import depends.entity.CandidateTypes;
import depends.entity.FunctionEntity;
import depends.entity.TypeEntity;
import depends.entity.VarEntity;
import depends.extractor.FileParser;

public class PythonParameterTypeDedudceTest extends PythonParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	
	@Test
	public void test_deduce_type_of_parameter() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/deducetype_parameter.py",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    String name = withPackageName(srcs[0],"test");
	    FunctionEntity function = (FunctionEntity)( repo.getEntity(name));
	    VarEntity var = function.lookupVarLocally("t1");
	    TypeEntity type = var.getType();
	    assertTrue(type instanceof CandidateTypes);
	    assertEquals(2,((CandidateTypes)type).getCandidateTypes().size());
	}
	
	
	@Test
	public void test_deduce_type_of_builtIn() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/deducetype_builtin.py",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    String name = withPackageName(srcs[0],"test");
	    FunctionEntity function = (FunctionEntity)( repo.getEntity(name));
	    VarEntity var = function.lookupVarLocally("t1");
	    TypeEntity type = var.getType();
	    assertTrue(type == null);
	}

	
	@Test
	public void test_deduce_type_of_builtIn_cannot_override() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/deducetype_builtin.py",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    String name = withPackageName(srcs[0],"test2");
	    FunctionEntity function = (FunctionEntity)( repo.getEntity(name));
	    VarEntity var = function.lookupVarLocally("t1");
	    TypeEntity type = var.getType();
	    assertTrue(type instanceof CandidateTypes);
	    assertEquals(1,((CandidateTypes)type).getCandidateTypes().size());
	}
	
	
	@Ignore
	public void test_deduce_type_of_non_param_var() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/deducetype_nonparam.py",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    String name = withPackageName(srcs[0],"test");
	    FunctionEntity function = (FunctionEntity)( repo.getEntity(name));
	    VarEntity var = function.lookupVarLocally("t2");
	    TypeEntity type = var.getType();
	    assertTrue(type instanceof CandidateTypes);
	    assertEquals(2,((CandidateTypes)type).getCandidateTypes().size());
	}


}

