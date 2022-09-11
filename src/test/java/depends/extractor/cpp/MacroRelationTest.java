package depends.extractor.cpp;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
import depends.entity.TypeEntity;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class MacroRelationTest extends CppParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void macro_var_relation_in_seperate_file() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/MacroRelationTestInSeperateFile.h",
	    		"./src/test/resources/cpp-code-examples/MacroRelationTestInSeperateFile.cpp",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = createParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
	    Entity e = entityRepo.getEntity("foo");
	    this.assertContainsRelation(e, DependencyType.RETURN, TypeEntity.buildInType.getQualifiedName());
	    this.assertContainsRelation(e, DependencyType.CONTAIN, TypeEntity.buildInType.getQualifiedName());
	}
	
	
}