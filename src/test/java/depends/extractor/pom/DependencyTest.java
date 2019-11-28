package depends.extractor.pom;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;

public class DependencyTest extends MavenParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void should_extract_dep_relation() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/maven-code-examples/dependencyRelation/from.pom",
	    		"./src/test/resources/maven-code-examples/dependencyRelation/to.pom",
	    	    };
	    
	    for (String src:srcs) {
		    PomFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    this.assertContainsRelation(repo.getEntity("testgroup.test_1.0_"), DependencyType.CONTAIN, "a-dep-group.a-artifact_0.2_");
	}
	
	
}