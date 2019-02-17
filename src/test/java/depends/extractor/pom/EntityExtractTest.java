package depends.extractor.pom;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class EntityExtractTest extends MavenParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void use_package_contains() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/maven-code-examples/simple/log4j.pom",
	    	    };
	    
	    for (String src:srcs) {
		    PomFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
        assertEquals(0,repo.getEntity("org.log4j-test.log4j(1.2.12)").getRelations().size());
	}
	
	
	@Test
	public void should_use_parent_groupId() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/maven-code-examples/use_parent_groupId_and_version.pom",
	    	    };
	    
	    for (String src:srcs) {
		    PomFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
        assertNotNull(repo.getEntity("org.apache.maven.surefire.surefire-junit4(2.12.4)"));
	}
	
	@Test
	public void should_parse_properties_in_same_pom() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/maven-code-examples/properties-test1.pom",
	    	    };
	    
	    for (String src:srcs) {
		    PomFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
        PomArtifactEntity entity = (PomArtifactEntity)(repo.getEntity("properties-test.test(1)"));
    	/*
        <project.version>1.00</project.version>
        <activeio-version>3.1.4</activeio-version>
        <projectName>Apache ActiveMQ</projectName>
        <siteId>activemq-${project.version}</siteId>	 */
        assertEquals("1.00",entity.getProperty("project.version"));
        assertEquals("activemq-1.00",entity.getProperty("siteId"));
	}
	
	
	@Test
	public void should_parse_multiple_properties_in_same_pom() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/maven-code-examples/properties-test1.pom",
	    	    };
	    
	    for (String src:srcs) {
		    PomFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
        PomArtifactEntity entity = (PomArtifactEntity)(repo.getEntity("properties-test.test(1)"));
    	/*
        <project.version>1.00</project.version>
        <activeio-version>3.1.4</activeio-version>
        <projectName>Apache ActiveMQ</projectName>
        <anotherId>activemq-${project.version}--${activeio-version}</anotherId>	 */
        assertEquals("activemq-1.00-3.1.4",entity.getProperty("anotherId"));
	}
	
	@Test
	public void should_parse_multiple_properties_in_parent_pom() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/maven-code-examples/properties-test-child.pom"
	    	    };
	    
	    for (String src:srcs) {
		    PomFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
        PomArtifactEntity entity = (PomArtifactEntity)(repo.getEntity("properties-test.test(1)"));
        assertEquals("13",entity.getProperty("project.version"));
	}
}