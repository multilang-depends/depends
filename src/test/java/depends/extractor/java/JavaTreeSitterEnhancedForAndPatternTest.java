package depends.extractor.java;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
import depends.relations.Relation;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class JavaTreeSitterEnhancedForAndPatternTest extends JavaTreeSitterParserTest {
    @Before
    public void setUp() {
        super.init();
    }

    @Test
    public void test_should_extract_calls_from_enhanced_for_and_pattern() throws IOException {
        String src = "./src/test/resources/java-code-examples/TreeSitterEnhancedForAndPatternSample.java";
        createParser().parse(src);
        resolveAllBindings();

        Entity method = entityRepo.getEntity("ts.TreeSitterEnhancedForAndPatternSample.run");
        assertNotNull(method);
        assertContainsRelation(method, DependencyType.CALL, "ts.TSLoopItem.ping");
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
