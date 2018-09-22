package depends.extractor.java;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

import depends.entity.repo.EntityRepo;

public class JavaInternalClassTest {
	@Test
	public void test_parameter() throws IOException {
		EntityRepo repo = new EntityRepo();
        String src = "./src/test/resources/java-code-examples/InternalClass.java";
        JavaFileParser parser = new JavaFileParser(src,repo);
        parser.parse();
        assertNotNull(repo.getEntity("a.InternalClass.Internal"));
	}

}
