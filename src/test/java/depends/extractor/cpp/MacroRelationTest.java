package depends.extractor.cpp;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
import depends.relations.Inferer;

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
		    CppFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    Entity e = repo.getEntity("foo");
	    this.assertContainsRelation(e, DependencyType.RETURN, Inferer.buildInType.getQualifiedName());
	    this.assertContainsRelation(e, DependencyType.CONTAIN, Inferer.buildInType.getQualifiedName());
	    this.assertContainsRelation(e, DependencyType.USE, Inferer.buildInType.getQualifiedName());
	}
	
	
}