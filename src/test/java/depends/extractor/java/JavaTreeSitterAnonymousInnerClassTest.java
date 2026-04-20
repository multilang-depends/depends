package depends.extractor.java;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
import depends.relations.Relation;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class JavaTreeSitterAnonymousInnerClassTest extends JavaTreeSitterParserTest {
    @Before
    public void setUp() {
        super.init();
    }

    @Test
    public void test_should_extract_anonymous_class_body_relations() throws IOException {
        String src = "./src/test/resources/java-code-examples/TreeSitterAnonymousInnerClassSample.java";
        createParser().parse(src);
        resolveAllBindings();

        Entity method = entityRepo.getEntity("ts.TreeSitterAnonymousInnerClassSample.test");
        assertNotNull(method);
        assertContainsRelation(method, DependencyType.CREATE, "ts.TSAnonAction");
        assertContainsRelation(method, DependencyType.CALL, "ts.TSAnonAction.run");
        assertTrue("anonymous class body should call assist()", hasCallTo("ts.TreeSitterAnonymousInnerClassSample.assist"));
    }

    private boolean hasCallTo(String targetQualifiedName) {
        Iterator<Entity> iterator = entityRepo.entityIterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            for (Relation relation : entity.getRelations()) {
                if (DependencyType.CALL.equals(relation.getType())
                        && relation.getEntity() != null
                        && targetQualifiedName.equals(relation.getEntity().getQualifiedName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
