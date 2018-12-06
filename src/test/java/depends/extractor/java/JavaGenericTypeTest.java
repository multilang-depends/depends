package depends.extractor.java;

import static org.junit.Assert.assertEquals;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.entity.JavaParserTest;

public class JavaGenericTypeTest extends JavaParserTest{
	@Before
	public void setUp() {
		super.init();
	}
	
	@Test
	public void test_GenericTypeShouldBeIdentified() throws IOException {
        String src = "./src/test/resources/java-code-examples/GenericTypeTest.java";
        JavaFileParser parser = createParser(src);
        parser.parse();
        inferer.resolveAllBindings();
        assertEquals(1,entityRepo.getEntity("x.GenericTypeTest").getRelations().size());
	}
}
