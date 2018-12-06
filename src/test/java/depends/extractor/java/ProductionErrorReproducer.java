package depends.extractor.java;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.entity.Entity;
import depends.entity.JavaParserTest;

public class ProductionErrorReproducer extends JavaParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	
	@Test
	public void reproduce_error() throws IOException {
        String src = "./src/test/resources/java-code-examples/Maze.java";
        JavaFileParser parser = createParser(src);
        parser.parse();
        inferer.resolveAllBindings();
        Entity t = entityRepo.getEntity("maze.BlueMazeFactory.makeDoor");
        System.out.println(t);
	}
}
