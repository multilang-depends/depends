package depends.extractor.java;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
import depends.relations.Relation;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class JavaTreeSitterSwitchExpressionTest extends JavaTreeSitterParserTest {
    @Before
    public void setUp() {
        super.init();
    }

    @Test
    public void test_should_extract_relations_in_switch_expression_and_yield() throws IOException {
        String src = "./src/test/resources/java-code-examples/TreeSitterSwitchExpressionSample.java";
        createParser().parse(src);
        resolveAllBindings();

        Entity method = entityRepo.getEntity("ts.TreeSitterSwitchExpressionSample.select");
        assertNotNull(method);
        assertContainsRelation(method, DependencyType.CALL, "ts.TSSEA.make");
        assertContainsRelation(method, DependencyType.CALL, "ts.TSSEA.ping");
        assertHasCallRelation(method);
    }

    private void assertHasCallRelation(Entity entity) {
        for (Relation relation : entity.getRelations()) {
            if (DependencyType.CALL.equals(relation.getType())) {
                return;
            }
        }
        org.junit.Assert.fail("cannot found relation type of Call");
    }
}
