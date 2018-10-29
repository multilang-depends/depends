package depends.extractor.java;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import depends.entity.repo.EntityRepo;

public class JavaAnnotationParserTest {
	@Test
	public void test_annotationType() throws IOException {
		EntityRepo repo = new EntityRepo();
        String src = "./src/test/resources/java-code-examples/AnnotationTest.java";
        JavaFileParser parser = new JavaFileParser(src,repo);
        parser.parse();
        repo.resolveAllBindings();
        assertEquals(1,repo.getEntity("AnnotationTest").getRelations().size());
	}

}
