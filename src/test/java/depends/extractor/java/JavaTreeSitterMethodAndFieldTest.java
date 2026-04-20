package depends.extractor.java;

import depends.entity.Entity;
import depends.entity.FunctionEntity;
import depends.entity.TypeEntity;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class JavaTreeSitterMethodAndFieldTest extends JavaTreeSitterParserTest {
    @Before
    public void setUp() {
        super.init();
    }

    @Test
    public void test_should_parse_method_signature_and_parameters() throws IOException {
        String src = "./src/test/resources/java-code-examples/TreeSitterMethodFieldSample.java";
        createParser().parse(src);
        resolveAllBindings();

        Entity method = entityRepo.getEntity("ts.TreeSitterMethodFieldSample.sum");
        assertNotNull(method);
        assertContainsParametersWithRawName((FunctionEntity) method, "p1");
        assertContainsParametersWithRawName((FunctionEntity) method, "p2");
    }

    @Test
    public void test_should_parse_field_declarations() throws IOException {
        String src = "./src/test/resources/java-code-examples/TreeSitterMethodFieldSample.java";
        createParser().parse(src);
        resolveAllBindings();

        TypeEntity type = (TypeEntity) entityRepo.getEntity("ts.TreeSitterMethodFieldSample");
        assertNotNull(type);
        assertContainsVarWithRawName(type, "x");
        assertContainsVarWithRawName(type, "y");
        assertContainsVarWithRawName(type, "z");
    }
}
