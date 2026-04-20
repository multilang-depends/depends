package depends.extractor.java;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class JavaTreeSitterFieldAccessTest extends JavaTreeSitterParserTest {
    @Before
    public void setUp() {
        super.init();
    }

    @Test
    public void test_should_extract_use_relations_for_field_access() throws IOException {
        String src = "./src/test/resources/java-code-examples/TreeSitterFieldAccessSample.java";
        createParser().parse(src);
        resolveAllBindings();

        Entity method = entityRepo.getEntity("ts.TreeSitterFieldAccessSample.test");
        assertNotNull(method);
        assertContainsRelation(method, DependencyType.USE, "ts.TreeSitterFieldAccessSample.localField");
        assertContainsRelation(method, DependencyType.USE, "ts.TSAccessHolder.value");
    }
}
