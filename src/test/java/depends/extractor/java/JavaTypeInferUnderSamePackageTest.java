package depends.extractor.java;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class JavaTypeInferUnderSamePackageTest extends JavaParserTest{
	@Before
	public void setUp() {
		super.init();
	}
	
	@Test
	public void test_GenericTypeShouldBeIdentified() throws IOException {
        String src = "./src/test/resources/java-code-examples/TypeInferUnderSamePackageA.java";
        JavaFileParser parser = createParser();
        parser.parse(src);
        src = "./src/test/resources/java-code-examples/TypeInferUnderSamePackageB.java";
        parser = createParser();
        parser.parse(src);
        resolveAllBindings();
        assertEquals(1,entityRepo.getEntity("x.TypeInferUnderSamePackageA").getRelations().size());
	}
}
