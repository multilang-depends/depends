package depends.extractor.java;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class JavaTreeSitterCallChainTest extends JavaTreeSitterParserTest {
    @Before
    public void setUp() {
        super.init();
    }

    @Test
    public void test_should_extract_chained_call_dependencies() throws IOException {
        String src = "./src/test/resources/java-code-examples/TreeSitterCallChainSample.java";
        createParser().parse(src);
        resolveAllBindings();

        Entity method = entityRepo.getEntity("ts.TreeSitterCallChainSample.test");
        assertNotNull(method);
        assertContainsRelation(method, DependencyType.CALL, "ts.TreeSitterCallChainSample.helper");
        assertContainsRelation(method, DependencyType.CALL, "ts.TSChain.foo");
        assertContainsRelation(method, DependencyType.CALL, "ts.TSChain.bar");
        assertContainsRelation(method, DependencyType.CALL, "ts.TSChain.baz");
    }
}
