package depends.extractor.maven;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.extractor.pom.PomFileParser;

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
        assertEquals(0,repo.getEntity("log4j.log4j(1.2.12)").getRelations().size());
	}
}