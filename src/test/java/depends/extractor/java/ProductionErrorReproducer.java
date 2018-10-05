package depends.extractor.java;

import java.io.IOException;

import org.junit.Test;

import depends.entity.Entity;
import depends.entity.repo.EntityRepo;

public class ProductionErrorReproducer {
	@Test
	public void reproduce_error() throws IOException {
		EntityRepo repo = new EntityRepo();
        String src = "./src/test/resources/java-code-examples/Maze.java";
        JavaFileParser parser = new JavaFileParser(src,repo);
        parser.parse();
        repo.resolveAllBindings();
        Entity t = repo.getEntity("maze.BlueMazeFactory.makeDoor");
        System.out.println(t);
	}

}
