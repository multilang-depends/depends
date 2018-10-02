package depends.extractor.java;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

import depends.entity.repo.EntityRepo;

public class JavaFunctionExtractTest {
	@Test
	public void test_parameter() throws IOException {
		EntityRepo repo = new EntityRepo();
        String src = "./src/test/resources/java-code-examples/Maze.java";
        JavaFileParser parser = new JavaFileParser(src,repo);
        parser.parse();
        repo.resolveAllBindings();
        assertNotNull(repo.getEntity("a.InternalClass.Internal"));
	}

}
