package depends.extractor.java;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.Test;
import depends.entity.repo.EntityRepo;

public class JavaTypeInferUnderSamePackageTest {
	@Test
	public void test_GenericTypeShouldBeIdentified() throws IOException {
		EntityRepo repo = new EntityRepo();
        String src = "./src/test/resources/java-code-examples/TypeInferUnderSamePackageA.java";
        JavaFileParser parser = new JavaFileParser(src,repo);
        parser.parse();
        src = "./src/test/resources/java-code-examples/TypeInferUnderSamePackageB.java";
        parser = new JavaFileParser(src,repo);
        parser.parse();
        repo.resolveAllBindings();
        assertEquals(1,repo.getEntity("x.TypeInferUnderSamePackageA").getRelations().size());
	}
}
