package depends.extractor.java;

import depends.extractor.UnsolvedBindings;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class UnsolvedSymbolsTest extends JavaParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	@Test
	public void test_missing_import() throws IOException {
        String src = "./src/test/resources/java-code-examples/UnsolvedSymbols/MissingImport.java";
        JavaFileParser parser = createParser();
        parser.parse(src);
        Set<UnsolvedBindings> missing = resolveAllBindings();
        assertEquals(1,missing.size());
        assertEquals("a.b",missing.iterator().next().getRawName());
	}

}
