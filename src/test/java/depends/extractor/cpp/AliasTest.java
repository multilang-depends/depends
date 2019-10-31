package depends.extractor.cpp;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;

public class AliasTest extends CppParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void test_genericTypes() throws IOException {
	    String src = "./src/test/resources/cpp-code-examples/Alias.cpp";
	    CppFileParser parser = createParser(src);
        parser.parse();
        inferer.resolveAllBindings();
        this.assertContainsRelation(repo.getEntity("bar"), DependencyType.CALL, "F.foo");
	}
	


}
