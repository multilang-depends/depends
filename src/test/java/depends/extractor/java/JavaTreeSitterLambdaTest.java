package depends.extractor.java;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class JavaTreeSitterLambdaTest extends JavaTreeSitterParserTest {
    @Before
    public void setUp() {
        super.init();
    }

    @Test
    public void test_should_extract_call_inside_lambda_body() throws IOException {
        String src = "./src/test/resources/java-code-examples/TreeSitterLambdaSample.java";
        createParser().parse(src);
        resolveAllBindings();

        Entity method = entityRepo.getEntity("ts.TreeSitterLambdaSample.test");
        assertNotNull(method);
        assertContainsRelation(method, DependencyType.CALL, "ts.TSLambdaTarget.ping");
        assertContainsRelation(method, DependencyType.CALL, "ts.TSLambdaConsumer.apply");
        assertContainsRelation(method, DependencyType.CREATE, "ts.TSLambdaTarget");
    }
}
