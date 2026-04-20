package depends.extractor.java;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class JavaTreeSitterExpressionTest extends JavaTreeSitterParserTest {
    @Before
    public void setUp() {
        super.init();
    }

    @Test
    public void test_should_extract_call_create_and_cast_dependencies() throws IOException {
        String src = "./src/test/resources/java-code-examples/TreeSitterExpressionSample.java";
        createParser().parse(src);
        resolveAllBindings();

        Entity method = entityRepo.getEntity("ts.TreeSitterExpressionSample.test");
        assertNotNull(method);
        assertContainsRelation(method, DependencyType.CREATE, "ts.TSExprA");
        assertContainsRelation(method, DependencyType.CALL, "ts.TSExprA.foo");
        assertContainsRelation(method, DependencyType.CAST, "ts.TSExprA");
    }
}
