package depends.extractor.ruby;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.extractor.FileParser;
import depends.relations.Relation;

public class RubyInheritTest extends RubyParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	@Test
	public void test_relation() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/extends.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    this.assertContainsRelation(entityRepo.getEntity("Cat"), DependencyType.INHERIT, "Animal");
	}
	
	@Test
	public void test_relation_of_included_file() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/extends_animal.rb",
	    		"./src/test/resources/ruby-code-examples/extends_cat.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    this.assertContainsRelation(entityRepo.getEntity("Cat"), DependencyType.INHERIT, "Animal");
	}
	
	
	@Ignore
	public void test_relation_with_cpath_1() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/extends_with_cpath.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    super.assertContainsRelation(entityRepo.getEntity("Cat"), DependencyType.INHERIT, "Animal");
	}
	
	@Test
	public void test_relation_with_cpath_2() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/extends_with_cpath.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    super.assertContainsRelation(entityRepo.getEntity("ZooCat"), DependencyType.INHERIT, "Zoo.Animal");
	}
	
	@Test
	public void test_relation_with_cpath_3() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/extends_with_cpath.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    this.assertContainsRelation(entityRepo.getEntity("Zoo.Cow"), DependencyType.INHERIT, "Zoo.Animal");
	}
	
	@Ignore
	public void test_relation_with_alias() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/ruby-code-examples/extends_with_alias.rb",
	    	    };
	    
	    for (String src:srcs) {
		    FileParser parser = createFileParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
        assertEquals(1,entityRepo.getEntity("Cat").getRelations().size());
        Relation r = entityRepo.getEntity("Cat").getRelations().get(0);
        assertEquals(DependencyType.INHERIT,r.getType());
        assertEquals("Zoo.Animal",r.getEntity().getQualifiedName());
	}
}
