package depends.extractor.java;

import depends.entity.Entity;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class JavaTreeSitterEnumDetailTest extends JavaTreeSitterParserTest {
    @Before
    public void setUp() {
        super.init();
    }

    @Test
    public void test_should_extract_enum_constants() throws IOException {
        String src = "./src/test/resources/java-code-examples/TreeSitterEnumDetailSample.java";
        createParser().parse(src);
        resolveAllBindings();

        Entity enumType = entityRepo.getEntity("ts.TreeSitterEnumDetailSample");
        assertNotNull(enumType);
        assertContainsVarWithRawName(enumType, "FIRST");
        assertContainsVarWithRawName(enumType, "SECOND");
    }
}
