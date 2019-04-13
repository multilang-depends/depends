package depends.extractor.java;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.entity.Entity;

public class JavaParseErrorFileTest extends JavaParserTest{
	@Before
	public void setUp() {
		super.init();
	}
	
	@Test
	public void test_incomplete_file_should_not_stop_process() throws IOException {
        String src = "./src/test/resources/java-code-examples/IncompleteFile.java";
        JavaFileParser parser = createParser(src);
        parser.parse();
        inferer.resolveAllBindings();
        Entity classEntity = entityRepo.getEntity("FieldVar");
	}
	
	@Test
	public void test_should_resolve_types() throws IOException {
        String src = "./src/test/resources/java-code-examples/EclipseTestBase_No_ResponseDuirngTypeResolve.java";
        JavaFileParser parser = createParser(src);
        parser.parse();
        inferer.resolveAllBindings();
        Entity classEntity = entityRepo.getEntity("FieldVar");
	}
	
	
}
