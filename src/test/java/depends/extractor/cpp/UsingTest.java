package depends.extractor.cpp;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
import depends.entity.TypeEntity;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

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
		    CppFileParser parser = createParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
        Entity e = entityRepo.getEntity("foo");
        this.assertContainsRelation(e, DependencyType.RETURN, TypeEntity.buildInType.getQualifiedName());
        this.assertContainsRelation(e, DependencyType.CONTAIN, "A.C");
        this.assertContainsRelation(e, DependencyType.CONTAIN, "B.X");
        
	}
	
	@Test
	public void suffix_names_should_be_resolved() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/UsingTest.cpp",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = createParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
        Entity e = entityRepo.getEntity("bar");
        this.assertContainsRelation(e, DependencyType.RETURN, TypeEntity.buildInType.getQualifiedName());
        this.assertContainsRelation(e, DependencyType.CONTAIN, "A.C");
        this.assertContainsRelation(e, DependencyType.CONTAIN, "B.X");
	}
}
