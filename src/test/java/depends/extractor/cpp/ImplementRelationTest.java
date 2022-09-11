package depends.extractor.cpp;
import depends.deptypes.DependencyType;
import depends.entity.Entity;
import depends.entity.FunctionEntityImpl;
import depends.entity.MultiDeclareEntities;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ImplementRelationTest extends CppParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void should_detect_implements_from_source_to_header() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/relationImplements/A.cpp",
	    		"./src/test/resources/cpp-code-examples/relationImplements/A.h",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser =createParser();
		    parser.parse(src);
	    }
	    resolveAllBindings();
	    MultiDeclareEntities multiDeclare = (MultiDeclareEntities)( entityRepo.getEntity("foo"));
	    List<Entity> f = multiDeclare.getEntities().stream().filter(item->item.getClass().equals(FunctionEntityImpl.class)).collect(Collectors.toList());
        this.assertContainsRelation(f.get(0),DependencyType.IMPLEMENT,"foo");
	}
	
}