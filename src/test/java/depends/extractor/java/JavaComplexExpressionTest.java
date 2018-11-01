package depends.extractor.java;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import depends.entity.ContainerEntity;
import depends.entity.repo.EntityRepo;

public class JavaComplexExpressionTest {
	@Test
	public void test_complexExpression() throws IOException {
		EntityRepo repo = new EntityRepo();
        String src = "./src/test/resources/java-code-examples/ComplexExpressionTest.java";
        JavaFileParser parser = new JavaFileParser(src,repo);
        parser.parse();
        repo.resolveAllBindings();
        ContainerEntity entity  = (ContainerEntity)(repo.getEntity("ComplexExpressionTest.other"));
        assertEquals(3,entity.getRelations().size());
	}

}
