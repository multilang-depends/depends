package depends.extractor.cpp;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
import depends.entity.FunctionEntityImpl;
import depends.entity.FunctionEntityProto;
import depends.entity.VarEntity;
import depends.relations.Relation;

public class RelationToImplementationTest extends CppParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void should_convert_call_relation_to_impl() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/relation_to_impl/A.h",
	    		"./src/test/resources/cpp-code-examples/relation_to_impl/B.c",
	    		"./src/test/resources/cpp-code-examples/relation_to_impl/A.c",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings(true,null);
	    Entity bar = repo.getEntity("bar");
	    int matchCount = 0;
	    for (Relation relation:bar.getRelations()) {
	    	if (relation.getEntity() instanceof FunctionEntityProto) {
	    		matchCount++;
	    	}
	    	if (relation.getEntity() instanceof FunctionEntityImpl) {
	    		matchCount++;
	    	}
	    }
	    assertEquals(2, matchCount);
	}
	
	
	@Test
	public void should_convert_var_relation_to_impl() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/relation_to_impl/A.h",
	    		"./src/test/resources/cpp-code-examples/relation_to_impl/B.c",
	    		"./src/test/resources/cpp-code-examples/relation_to_impl/A.c",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings(true,null);
	    Entity bar = repo.getEntity("baz");
	    int matchCount = 0;
	    for (Relation relation:bar.getRelations()) {
	    	if (relation.getEntity() instanceof VarEntity) {
	    		matchCount++;
	    	}
	    }
	    assertEquals(2, matchCount);
	}
}
