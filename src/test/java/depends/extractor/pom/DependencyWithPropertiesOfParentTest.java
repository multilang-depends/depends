package depends.extractor.pom;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.Entity;
import depends.entity.MultiDeclareEntities;

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
		    PomFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    assertFalse(repo.getEntity("parent-group.a-parent_1.0_") instanceof MultiDeclareEntities);
	    this.assertContainsRelation(repo.getEntity("testgroup.test_1.0_"), DependencyType.CONTAIN, "a-dep-group.a-artifact_0.2_");
	}
	
	@Test
	public void should_extract_plugin_relation() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/maven-code-examples/dependencyWithPropertiesOfParent/from.pom",
	    		"./src/test/resources/maven-code-examples/dependencyWithPropertiesOfParent/parent-group/a-parent/1.0/a-parent-1.0.pom",
	    		"./src/test/resources/maven-code-examples/dependencyWithPropertiesOfParent/plugin.pom",
	    	    };
	    
	    for (String src:srcs) {
		    PomFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    this.assertContainsRelation(repo.getEntity("testgroup.test_1.0_"), DependencyType.USE, "aplugins.aplugin_0.1_");
	}
	
	
}