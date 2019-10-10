package depends.extractor.java;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import depends.extractor.UnsolvedBindings;

public class UnsolvedSymbolsTest extends JavaParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	@Test
	public void test_missing_import() throws IOException {
        String src = "./src/test/resources/java-code-examples/UnsolvedSymbols/MissingImport.java";
        JavaFileParser parser = createParser(src);
        parser.parse();
        Set<UnsolvedBindings> missing = inferer.resolveAllBindings();
        assertEquals(1,missing.size());
        assertEquals("a.b",missing.iterator().next().getRawName());
	}

}
