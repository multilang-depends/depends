package depends.extractor.pom;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
		    PomFileParser parser = createParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
        assertEquals(0,entityRepo.getEntity("org.log4j-test.log4j_1.2.12_").getRelations().size());
	}
	
	
	@Test
	public void should_use_parent_groupId() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/maven-code-examples/use_parent_groupId_and_version.pom",
	    	    };
	    
	    for (String src:srcs) {
		    PomFileParser parser = createParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
        assertNotNull(entityRepo.getEntity("org.apache.maven.surefire.surefire-junit4_2.12.4_"));
	}
	
	@Test
	public void should_parse_properties_in_same_pom() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/maven-code-examples/properties-test1.pom",
	    	    };
	    
	    for (String src:srcs) {
		    PomFileParser parser = createParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
        PomArtifactEntity entity = (PomArtifactEntity)(entityRepo.getEntity("properties-test.test_1_"));
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
		    PomFileParser parser = createParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
        PomArtifactEntity entity = (PomArtifactEntity)(entityRepo.getEntity("properties-test.test_1_"));
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
		    PomFileParser parser = createParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
        PomArtifactEntity entity = (PomArtifactEntity)(entityRepo.getEntity("properties-test.test_1_"));
        assertEquals("13",entity.getProperty("project.version"));
	}
}