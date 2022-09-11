package depends.extractor.pom;
import depends.deptypes.DependencyType;
import depends.entity.MultiDeclareEntities;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;

public class DependencyWithPropertiesOfParentTest extends MavenParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void should_extract_dep_relation() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/maven-code-examples/dependencyWithPropertiesOfParent/parent-group/a-parent/1.0/a-parent-1.0.pom",
	    		"./src/test/resources/maven-code-examples/dependencyWithPropertiesOfParent/from.pom",
	    		"./src/test/resources/maven-code-examples/dependencyWithPropertiesOfParent/to.pom",
	    	    };
	    
	    for (String src:srcs) {
		    PomFileParser parser = createParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
	    assertFalse(entityRepo.getEntity("parent-group.a-parent_1.0_") instanceof MultiDeclareEntities);
	    this.assertContainsRelation(entityRepo.getEntity("testgroup.test_1.0_"), DependencyType.PomDependency, "a-dep-group.a-artifact_0.2_");
	}
	
	@Test
	public void should_extract_plugin_relation() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/maven-code-examples/dependencyWithPropertiesOfParent/from.pom",
	    		"./src/test/resources/maven-code-examples/dependencyWithPropertiesOfParent/parent-group/a-parent/1.0/a-parent-1.0.pom",
	    		"./src/test/resources/maven-code-examples/dependencyWithPropertiesOfParent/plugin.pom",
	    	    };
	    
	    for (String src:srcs) {
		    PomFileParser parser = createParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
	    this.assertContainsRelation(entityRepo.getEntity("testgroup.test_1.0_"), DependencyType.PomPlugin, "aplugins.aplugin_0.1_");
	}
	
	
}