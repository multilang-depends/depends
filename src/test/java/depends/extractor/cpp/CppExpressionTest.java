package depends.extractor.cpp;
import depends.deptypes.DependencyType;
import depends.entity.Entity;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

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
        this.assertContainsRelation(e, DependencyType.CAST,"ClassA");
        this.assertContainsRelation(e, DependencyType.CALL,"ClassA");
        this.assertContainsRelation(e, DependencyType.CREATE,"ClassA");
        this.assertContainsRelation(e, DependencyType.USE,"ClassA");
        this.assertContainsRelation(e, DependencyType.USE,"foo.a2");
        this.assertContainsRelation(e, DependencyType.USE,"foo.a3");
        this.assertContainsRelation(e, DependencyType.USE,"foo.a");        
        this.assertContainsRelation(e, DependencyType.CONTAIN,"ClassA");
	}

	@Test
    public void test_should_not_count_expr_duplicated() throws IOException {
        String src = "./src/test/resources/cpp-code-examples/DupExpressions.cpp";
        CppFileParser parser = createParser(src);
        parser.parse();
        inferer.resolveAllBindings();
        Entity e = repo.getEntity("foo");
        assertEquals(4,e.getRelations().size());
    }
}
