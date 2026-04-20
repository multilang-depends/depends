package depends.extractor.java;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class JavaTreeSitterMethodReferenceTest extends JavaTreeSitterParserTest {
    @Before
    public void setUp() {
        super.init();
    }

    @Test
    public void test_should_extract_method_reference_dependencies() throws IOException {
        String src = "./src/test/resources/java-code-examples/TreeSitterMethodReferenceSample.java";
        createParser().parse(src);
        resolveAllBindings();

        Entity method = entityRepo.getEntity("ts.TreeSitterMethodReferenceSample.test");
        assertNotNull(method);
        assertContainsRelation(method, DependencyType.CALL, "ts.TSRefTarget.staticPing");
        assertContainsRelation(method, DependencyType.CALL, "ts.TSRefTarget.instancePing");
        assertContainsRelation(method, DependencyType.CREATE, "ts.TSRefTarget");
    }
}
