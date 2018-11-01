package depends.extractor.java;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.Test;
import depends.entity.repo.EntityRepo;

public class JavaEmbededClassTest {
	@Test
	public void test_EmbededTypeWithImport() throws IOException {
		EntityRepo repo = new EntityRepo();
        String src = "./src/test/resources/java-code-examples/EmbededTest.java";
        JavaFileParser parser = new JavaFileParser(src,repo);
        parser.parse();
        repo.resolveAllBindings();
        assertEquals(1,repo.getEntity("x.EmbededTest").getRelations().size());
	}

	@Test
	public void test_EmbededTypeWithoutImport() throws IOException {
		EntityRepo repo = new EntityRepo();
        String src = "./src/test/resources/java-code-examples/EmbededTest.java";
        JavaFileParser parser = new JavaFileParser(src,repo);
        parser.parse();
        repo.resolveAllBindings();
        assertEquals(1,repo.getEntity("x.EmbededTest2").getRelations().size());
	}
}
