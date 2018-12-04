package depends.extractor.java;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import depends.entity.ContainerEntity;
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
        ContainerEntity e = (ContainerEntity) repo.getEntity("LocalVarInferExample.setExample");
        System.out.println(e.dumpExpressions());
        assertEquals(15,repo.getEntity("LocalVarInferExample.setExample").getRelations().size());
	}
	
	@Test
	public void test_field_access_could_be_inferred() throws IOException {
		EntityRepo repo = new EntityRepo();
        String src = "./src/test/resources/java-code-examples/ComplexExpressionExample.java";
        JavaFileParser parser = new JavaFileParser(src,repo);
        parser.parse();
        repo.resolveAllBindings();
        assertEquals(20,repo.getEntity("test.ComplexExpressionExample.setExample").getRelations().size());
	}
	
	@Test
	public void test_long_static_function_should_be_inferred() throws IOException {
		EntityRepo repo = new EntityRepo();
        String src = "./src/test/resources/java-code-examples/LongExpressionWithAbsolutePath.java";
        JavaFileParser parser = new JavaFileParser(src,repo);
        parser.parse();
        repo.resolveAllBindings();
        System.out.println(((ContainerEntity)(repo.getEntity("x.LongExpressionWithAbsolutePath.setExample"))).dumpExpressions());
        assertEquals(6,repo.getEntity("x.LongExpressionWithAbsolutePath.setExample").getRelations().size());
	}
	
	
}
