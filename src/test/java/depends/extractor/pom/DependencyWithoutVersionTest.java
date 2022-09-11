package depends.extractor.pom;
import depends.deptypes.DependencyType;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class DependencyWithoutVersionTest extends MavenParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void should_extract_dep_relation() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/maven-code-examples/dependencyWithoutVersion/from.pom",
	    		"./src/test/resources/maven-code-examples/dependencyWithoutVersion/a-dep-group/a-artifact/0.2/to.pom",
	    	    };
	    
	    for (String src:srcs) {
		    PomFileParser parser = createParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
	    this.assertContainsRelation(entityRepo.getEntity("testgroup.test_1.0_"), DependencyType.PomDependency, "a-dep-group.a-artifact_0.2_");
	}
	
	@Test
	public void should_extract_plugin_relation() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/maven-code-examples/dependencyWithoutVersion/from.pom",
	    		"./src/test/resources/maven-code-examples/dependencyWithoutVersion/aplugins/aplugin/0.1/plugin.pom",
	    	    };
	    
	    for (String src:srcs) {
		    PomFileParser parser = createParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
	    this.assertContainsRelation(entityRepo.getEntity("testgroup.test_1.0_"), DependencyType.PomPlugin, "aplugins.aplugin_0.1_");
	}
	
	
}