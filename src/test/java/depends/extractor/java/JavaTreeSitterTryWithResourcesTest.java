package depends.extractor.java;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
import depends.relations.Relation;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class JavaTreeSitterTryWithResourcesTest extends JavaTreeSitterParserTest {
    @Before
    public void setUp() {
        super.init();
    }

    @Test
    public void test_should_extract_relations_in_try_with_resources() throws IOException {
        String src = "./src/test/resources/java-code-examples/TreeSitterTryWithResourcesSample.java";
        createParser().parse(src);
        resolveAllBindings();

        Entity method = entityRepo.getEntity("ts.TreeSitterTryWithResourcesSample.test");
        assertNotNull(method);
        assertContainsRelation(method, DependencyType.CREATE, "ts.TSResource");
        assertHasCallRelation(method);
    }

    private void assertHasCallRelation(Entity method) {
        for (Relation relation : method.getRelations()) {
            if (DependencyType.CALL.equals(relation.getType())) {
                return;
            }
        }
        org.junit.Assert.fail("cannot found relation type of Call");
    }
}
