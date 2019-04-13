package depends.extractor.java;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.entity.Entity;

public class JavaCylicInheritTest extends JavaParserTest{
	@Before
	public void setUp() {
		super.init();
	}
	
	@Test(timeout=3000L)
	public void test_cyclic_should_not_occur_inifinite_test() throws IOException {
        String src = "./src/test/resources/java-code-examples/CyclicInherit.java";
        JavaFileParser parser = createParser(src);
        parser.parse();
        inferer.resolveAllBindings();
	}
	
	
}
