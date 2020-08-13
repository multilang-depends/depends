package depends.extractor.cpp;

import depends.entity.Entity;
import depends.entity.FunctionEntityImpl;
import depends.entity.FunctionEntityProto;
import depends.relations.Relation;
import multilang.depends.util.file.FileUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class RelationInSameFileTest extends CppParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void should_convert_call_relation_to_impl() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/relation_in_samefile/A.c",
				"./src/test/resources/cpp-code-examples/relation_in_samefile/B.c"
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings(true,null);
	    Entity bar = repo.getEntity(FileUtil.uniqFilePath(srcs[0]));
	    int matchCount = 0;
	    for (Relation relation:bar.getRelations()) {
	    	if (relation.getEntity() instanceof FunctionEntityProto) {
	    		matchCount++;
	    	}
	    	if (relation.getEntity() instanceof FunctionEntityImpl) {
	    		matchCount++;
	    	}
	    }
	    assertEquals(1, matchCount);
	}
	

}
