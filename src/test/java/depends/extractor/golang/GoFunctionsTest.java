package depends.extractor.golang;

import depends.entity.TypeEntity;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GoFunctionsTest extends GolangParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	@Test
	public void test_could_parse_function() throws IOException {
        String src = "./src/test/resources/go-code-examples/func.go";
        GoFileParser parser =createParser();
        parser.parse(src);
        resolveAllBindings();
        assertNotNull(entityRepo.getEntity("main"));
	}


	@Ignore
	public void test_could_parse_struct_type() throws IOException {
		String src = "./src/test/resources/go-code-examples/struct.go";
		GoFileParser parser =createParser();
		parser.parse(src);
		resolveAllBindings();
		assertNotNull(entityRepo.getEntity("Books"));
	}

	@Ignore
	public void test_could_parse_struct_members() throws IOException {
		String src = "./src/test/resources/go-code-examples/struct.go";
		GoFileParser parser =createParser();
		parser.parse(src);
		resolveAllBindings();
		assertNotNull(entityRepo.getEntity("Books"));
		TypeEntity book = (TypeEntity)entityRepo.getEntity("Books");
		assertEquals(4,book.getChildren().size());
	}
}
