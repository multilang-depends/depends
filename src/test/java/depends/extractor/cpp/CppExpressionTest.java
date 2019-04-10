package depends.extractor.cpp;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.Entity;

public class CppExpressionTest extends CppParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void test_expressions() throws IOException {
	    String src = "./src/test/resources/cpp-code-examples/Expressions.cpp";
	    CppFileParser parser = createParser(src);
        parser.parse();
        inferer.resolveAllBindings();
        Entity e = repo.getEntity("foo");
        this.assertContainsRelation(e, DependencyType.PARAMETER,"ClassA");
        this.assertContainsRelation(e, DependencyType.CONTAIN,"ClassA");
        this.assertContainsRelation(e, DependencyType.CAST,"ClassA");
        this.assertContainsRelation(e, DependencyType.CALL,"ClassA");
        this.assertContainsRelation(e, DependencyType.CREATE,"ClassA");
        this.assertContainsRelation(e, DependencyType.USE,"ClassA");
        this.assertContainsRelation(e, DependencyType.USE,"foo.a2");
        this.assertContainsRelation(e, DependencyType.USE,"foo.a3");
        this.assertContainsRelation(e, DependencyType.USE,"foo.a");        
	}

}
