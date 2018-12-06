package depends.extractor.java;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class JavaInternalClassTest extends JavaParserTest{
	@Before
	public void setUp() {
		super.init();
	}
	
	@Test
	public void test_parameter() throws IOException {
        String src = "./src/test/resources/java-code-examples/InternalClass.java";
        JavaFileParser parser = createParser(src);
        parser.parse();
        assertNotNull(entityRepo.getEntity("a.InternalClass.Internal"));
	}

}
