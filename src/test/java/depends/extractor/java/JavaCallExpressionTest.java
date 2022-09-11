package depends.extractor.java;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.entity.ContainerEntity;

public class JavaCallExpressionTest extends JavaParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	@Test
	public void test() throws IOException {
        String src = "./src/test/resources/java-code-examples/SimpleExpressionCallTest.java";
        JavaFileParser parser = createParser();
        parser.parse(src);
        resolveAllBindings();
	}

}
