package depends.extractor.cpp;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.FunctionEntity;

public class GenericTypeTest extends CppParserTest {
	@Before
	public void setUp() {
		super.init();
	}

	@Test
	public void test_templateSpecializationOfStruct() throws IOException {
		String src = "./src/test/resources/cpp-code-examples/template/TempateStructure.cpp";
		CppFileParser parser = createParser();
		parser.parse(src);
		resolveAllBindings();
		assertNotNull(entityRepo.getEntity("hash"));
	}

	@Test
	public void test_genericTypesVarParameterReference() throws IOException {
		String src = "./src/test/resources/cpp-code-examples/template/GenericTypes.cpp";
		CppFileParser parser = createParser();
		parser.parse(src);
		resolveAllBindings();
		this.assertContainsRelation(entityRepo.getEntity("xStack"), DependencyType.PARAMETER, "X");
	}

	@Test
	public void test_genericTypesExtends() throws IOException {
		String src = "./src/test/resources/cpp-code-examples/template/GenericTypes.cpp";
		CppFileParser parser = createParser();
		parser.parse(src);
		resolveAllBindings();
		this.assertContainsRelation(entityRepo.getEntity("XStack"), DependencyType.INHERIT, "Stack");
	}

	@Test
	public void test_GenericTypeEmbededShouldBeIdentified() throws IOException {
		String src = "./src/test/resources/cpp-code-examples/template/EmbededTemplates.cpp";
		CppFileParser parser = createParser();
		parser.parse(src);
		resolveAllBindings();
		this.assertContainsRelation(entityRepo.getEntity("GenericTypeEmbededTest"), DependencyType.CONTAIN, "MyHashMap");
		this.assertContainsRelation(entityRepo.getEntity("GenericTypeEmbededTest.data"), DependencyType.PARAMETER, "MyList");
		this.assertContainsRelation(entityRepo.getEntity("GenericTypeEmbededTest.data"), DependencyType.PARAMETER, "MyArray");
	}

	@Test
	public void test_TemplateWithDots() throws IOException {
		String src = "./src/test/resources/cpp-code-examples/template/TemplateWithDots.cpp";
		CppFileParser parser = createParser();
		parser.parse(src);
		resolveAllBindings();
		assertNotNull(entityRepo.getEntity("foo.t2"));
	}

	@Test
	public void test_TemplateInReturn() throws IOException {
		String src = "./src/test/resources/cpp-code-examples/template/TemplateInReturnValue.cpp";
		CppFileParser parser = createParser();
		parser.parse(src);
		resolveAllBindings();
		FunctionEntity func = (FunctionEntity) entityRepo.getEntity("get");
		this.assertContainsRelation(func, DependencyType.RETURN, "std.tuple_element.type");
		this.assertContainsRelation(entityRepo.getEntity("get"), DependencyType.PARAMETER, "Index");
		this.assertContainsRelation(entityRepo.getEntity("get"), DependencyType.PARAMETER, "std.tuple");
		this.assertContainsRelation(entityRepo.getEntity("get"), DependencyType.PARAMETER, "Domains");
	}

}
