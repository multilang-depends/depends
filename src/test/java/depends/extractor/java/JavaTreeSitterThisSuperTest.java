package depends.extractor.java;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class JavaTreeSitterThisSuperTest extends JavaTreeSitterParserTest {
    @Before
    public void setUp() {
        super.init();
    }

    @Test
    public void test_should_extract_calls_on_this_and_super() throws IOException {
        String src = "./src/test/resources/java-code-examples/TreeSitterThisSuperSample.java";
        createParser().parse(src);
        resolveAllBindings();

        Entity method = entityRepo.getEntity("ts.TreeSitterThisSuperSample.test");
        assertNotNull(method);
        assertContainsRelation(method, DependencyType.CALL, "ts.TreeSitterThisSuperSample.local");
        assertContainsRelation(method, DependencyType.CALL, "ts.TSBase.ping");
    }
}
