package depends.extractor.java;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class JavaTreeSitterGenericBoundsWildcardTest extends JavaTreeSitterParserTest {
    @Before
    public void setUp() {
        super.init();
    }

    @Test
    public void test_should_extract_generic_bounds_and_wildcards() throws IOException {
        String src = "./src/test/resources/java-code-examples/TreeSitterGenericBoundsWildcardSample.java";
        createParser().parse(src);
        resolveAllBindings();

        Entity type = entityRepo.getEntity("ts.TreeSitterGenericBoundsWildcardSample");
        assertNotNull(type);
        assertContainsRelation(type, DependencyType.USE, "ts.TSGBase");
        assertContainsRelation(type, DependencyType.USE, "ts.TSGMarker");

        assertNotNull(entityRepo.getEntity("ts.TreeSitterGenericBoundsWildcardSample.up"));
        assertNotNull(entityRepo.getEntity("ts.TreeSitterGenericBoundsWildcardSample.down"));
    }
}
