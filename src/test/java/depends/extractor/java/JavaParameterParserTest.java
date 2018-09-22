package depends.extractor.java;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import depends.entity.repo.EntityRepo;

public class JavaParameterParserTest {
	@Test
	public void test_parameter() throws IOException {
		EntityRepo repo = new EntityRepo();
        String src = "./src/test/resources/java-code-examples/FunctionParameters.java";
        JavaFileParser parser = new JavaFileParser(src,repo);
        parser.parse();
        assertEquals(3,repo.getEntity("FunctionParameters.function_with_parameters_same_type").getRelations().size());
	}

}
