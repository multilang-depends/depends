package depends.extractor.java;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class JavaTreeSitterCallInArgumentsTest extends JavaTreeSitterParserTest {
    @Before
    public void setUp() {
        super.init();
    }

    @Test
    public void test_should_extract_nested_calls_in_arguments() throws IOException {
        String src = "./src/test/resources/java-code-examples/TreeSitterCallInArgumentsSample.java";
        createParser().parse(src);
        resolveAllBindings();

        Entity method = entityRepo.getEntity("ts.TreeSitterCallInArgumentsSample.test");
        assertNotNull(method);
        assertContainsRelation(method, DependencyType.CALL, "ts.TreeSitterCallInArgumentsSample.sink");
        assertContainsRelation(method, DependencyType.CALL, "ts.TreeSitterCallInArgumentsSample.helper");
        assertContainsRelation(method, DependencyType.CALL, "ts.TSArgs.bar");
        assertContainsRelation(method, DependencyType.CALL, "ts.TSArgs.baz");
    }
}
