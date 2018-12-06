package depends.extractor.java;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.entity.ContainerEntity;

public class JavaComplexExpressionTest extends JavaParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	@Test
	public void test_complexExpression() throws IOException {
        String src = "./src/test/resources/java-code-examples/ComplexExpressionTest.java";
        JavaFileParser parser = createParser(src);
        parser.parse();
        inferer.resolveAllBindings();
        ContainerEntity entity  = (ContainerEntity)(entityRepo.getEntity("ComplexExpressionTest.other"));
        assertEquals(3,entity.getRelations().size());
	}

}
