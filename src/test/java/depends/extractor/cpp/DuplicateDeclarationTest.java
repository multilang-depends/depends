package depends.extractor.cpp;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.Entity;

public class DuplicateDeclarationTest extends CppParserTest {
	 @Before
	    public void setUp() {
	    	super.init();
	    }
		
		@Test
		public void duplication_declaration_should_be_resolved() throws IOException {
			
			 String[] srcs = new String[] {
			    		"./src/test/resources/cpp-code-examples/DuplicationDeclarationCouldBeResolved.cpp",
			    	    };
			    
			    for (String src:srcs) {
				    CppFileParser parser = createParser(src);
				    parser.parse();
			    }
			    inferer.resolveAllBindings();
			    Entity e = repo.getEntity("X.invoke");
			    this.assertContainsRelation(e, DependencyType.CONTAIN,"DupClass");
			    this.assertContainsRelation(e, DependencyType.CALL,"DupClass");
			    this.assertContainsRelation(e, DependencyType.CREATE,"DupClass");
			    this.assertContainsRelation(e, DependencyType.USE,"DupClass");
			    this.assertContainsRelation(e, DependencyType.USE,"X.invoke.c");
			    			    
		}


}
