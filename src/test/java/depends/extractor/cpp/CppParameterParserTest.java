package depends.extractor.cpp;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import depends.entity.repo.EntityRepo;

public class CppParameterParserTest {
	@Test
	public void test_parameter() throws IOException {
		EntityRepo repo = new EntityRepo();
        String src = "./src/test/resources/cpp-code-examples/FunctionParameters.cpp";
        CppFileParser parser = new CppFileParser(src,repo);
        parser.parse();
        repo.resolveAllBindings();
        //assertEquals(4,repo.getEntity("FunctionParameters.function_with_parameters_same_type").getRelations().size());
	}

}
