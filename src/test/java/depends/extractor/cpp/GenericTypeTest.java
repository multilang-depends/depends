package depends.extractor.cpp;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;

public class GenericTypeTest extends CppParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void test_parameter() throws IOException {
	    String src = "./src/test/resources/cpp-code-examples/GenericTypes.cpp";
	    CppFileParser parser = createParser(src);
        parser.parse();
        inferer.resolveAllBindings();
        this.assertContainsRelation(repo.getEntity("foo"), DependencyType.CALL, "GetMax");
	}

}
