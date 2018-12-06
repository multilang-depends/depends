package depends.extractor.cpp;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import depends.entity.TypeAliasEntity;
import depends.extractor.cpp.CppFileParser;

public class IncludeRelationTest extends CppParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void test_includefiles_should_be_imported_relations() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/includesTest/EntryFile.cpp",
	    		"./src/test/resources/cpp-code-examples/includesTest/LocalHeader.h",
	    		"./src/test/resources/cpp-code-examples/includesTest/IndirectIncluded.h",
	    		"./src/test/resources/cpp-code-examples/includesTest/RelativeInclude.h",
	    		"./src/test/resources/cpp-code-examples/includesTest/path/Header.h",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
        File f = new File(srcs[0]);
        assertEquals(3,repo.getEntity(f.getCanonicalPath()).getRelations().size());
	}
	
	@Test
	public void test_includefiles_outside_project_will_not_count() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/includesTest/EntryFile.cpp",
	    		"./src/test/resources/cpp-code-examples/includesTest/LocalHeader.h",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
        File f = new File(srcs[0]);
        assertEquals(1,repo.getEntity(f.getCanonicalPath()).getRelations().size());
	}

	@Test
	public void test_could_found_files_in_include_path() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/includesTest/EntryFileIncludePathTest.cpp",
	    		"./src/test/resources/cpp-code-examples/includesTest/path/HeadersWithoutPath.h",
	    	    };
	    
	    List<String> includePaths = new ArrayList<>();
	    includePaths.add("./src/test/resources/cpp-code-examples/includesTest/path/");
	    for (String src:srcs) {
		    CppFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
        File f = new File(srcs[0]);
        assertEquals(1,repo.getEntity(f.getCanonicalPath()).getRelations().size());
	}
	
	
	@Test
	public void test_type_t_should_be_treat_as_structure() throws IOException {
	    String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/typedefTest.cpp",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
        File f = new File(srcs[0]);
        assertEquals("abc",((TypeAliasEntity)repo.getEntity("abc_t")).getOriginType().getRawName());
        
	}
}
