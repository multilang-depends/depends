package depends.extractor.java;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import depends.entity.Entity;
import depends.entity.repo.EntityRepo;

public class JavaVarResolveTest {
	@Test
	public void test_field_var_should_be_parsed() throws IOException {
		EntityRepo repo = new EntityRepo();
        String src = "./src/test/resources/java-code-examples/FieldVar.java";
        JavaFileParser parser = new JavaFileParser(src,repo);
        parser.parse();
        Entity classEntity = repo.getEntity("FieddVar");
        assertEquals(3,classEntity.getVars().size()); 
	}
}
