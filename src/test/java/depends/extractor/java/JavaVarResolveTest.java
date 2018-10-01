package depends.extractor.java;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import depends.entity.Entity;
import depends.entity.repo.EntityRepo;
import depends.entity.types.FunctionEntity;
import depends.entity.types.TypeEntity;

public class JavaVarResolveTest {
	@Test
	public void test_field_var_should_be_parsed() throws IOException {
		EntityRepo repo = new EntityRepo();
        String src = "./src/test/resources/java-code-examples/FieldVar.java";
        JavaFileParser parser = new JavaFileParser(src,repo);
        parser.parse();
        repo.resolveAllBindings();
        Entity classEntity = repo.getEntity("FieldVar");
        assertEquals(3,((TypeEntity)classEntity).getVars().size()); 
	}
	
	@Test
	public void test_local_var_should_be_parsed() throws IOException {
		EntityRepo repo = new EntityRepo();
        String src = "./src/test/resources/java-code-examples/LocalVar.java";
        JavaFileParser parser = new JavaFileParser(src,repo);
        parser.parse();
        repo.resolveAllBindings();
        assertEquals(1,((TypeEntity)repo.getEntity("LocalVar")).getVars().size());
        assertEquals(1,((FunctionEntity)repo.getEntity("LocalVar.foo")).getVars().size());
	}
	
	@Test
	public void test_local_var_type_could_be_inferred() throws IOException {
		EntityRepo repo = new EntityRepo();
        String src = "./src/test/resources/java-code-examples/LocalVarInferExample.java";
        JavaFileParser parser = new JavaFileParser(src,repo);
        parser.parse();
        repo.resolveAllBindings();
        assertEquals(15,repo.getEntity("LocalVarInferExample.setExample").getRelations().size());
        
	}
	
	@Test
	public void test_field_access_could_be_inferred() throws IOException {
		EntityRepo repo = new EntityRepo();
        String src = "./src/test/resources/java-code-examples/ComplexExpressionExample.java";
        JavaFileParser parser = new JavaFileParser(src,repo);
        parser.parse();
        repo.resolveAllBindings();
        assertEquals(13,repo.getEntity("test.ComplexExpressionExample.setExample").getRelations().size());
	}
	
}
