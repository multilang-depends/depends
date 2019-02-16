package depends.extractor.java;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class JavaAnnotationParserTest extends JavaParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	@Test
	public void test_annotationType() throws IOException {
        String src = "./src/test/resources/java-code-examples/AnnotationTest.java";
        JavaFileParser parser =createParser(src);
        parser.parse();
        inferer.resolveAllBindings();
        assertEquals(1,entityRepo.getEntity("AnnotationTest.value").getRelations().size());
	}

}
