package depends.extractor.java;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class JavaTreeSitterParseErrorToleranceTest extends JavaTreeSitterParserTest {
    @Before
    public void setUp() {
        super.init();
    }

    @Test
    public void test_should_tolerate_incomplete_source_file() throws IOException {
        String src = "./src/test/resources/java-code-examples/TreeSitterIncompleteSample.java";
        createParser().parse(src);
        resolveAllBindings();

        Entity type = entityRepo.getEntity("ts.TreeSitterIncompleteSample");
        Entity method = entityRepo.getEntity("ts.TreeSitterIncompleteSample.broken");
        assertNotNull(type);
        assertNotNull(method);
        assertContainsRelation(method, DependencyType.CALL, "ts.TSIncompleteDep.ping");
    }
}
