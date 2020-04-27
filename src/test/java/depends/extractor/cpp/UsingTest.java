package depends.extractor.cpp;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
import depends.relations.Inferer;

public class UsingTest extends CppParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void full_qualified_names_should_be_resolved() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/UsingTest.cpp",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
        Entity e = repo.getEntity("foo");
        this.assertContainsRelation(e, DependencyType.RETURN, Inferer.buildInType.getQualifiedName());
        this.assertContainsRelation(e, DependencyType.CONTAIN, "A.C");
        this.assertContainsRelation(e, DependencyType.CONTAIN, "B.X");
        
	}
	
	@Test
	public void suffix_names_should_be_resolved() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/UsingTest.cpp",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
        Entity e = repo.getEntity("bar");
        this.assertContainsRelation(e, DependencyType.RETURN, Inferer.buildInType.getQualifiedName());
        this.assertContainsRelation(e, DependencyType.CONTAIN, "A.C");
        this.assertContainsRelation(e, DependencyType.CONTAIN, "B.X");
	}
}
