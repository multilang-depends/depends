package depends.extractor.cpp;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.AliasEntity;
import multilang.depends.util.file.FileUtil;

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
	    		"./src/test/resources/cpp-code-examples/typedeftest2.cpp",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
        assertEquals("abc",((AliasEntity)repo.getEntity("abc_t")).getOriginType().getRawName().uniqName());
        
	}
	
	@Test
	public void test_call_should_only_in_relations_with_include() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/includeTest2/f0.cpp",
	    		"./src/test/resources/cpp-code-examples/includeTest2/f_with_include.cpp",
	    		"./src/test/resources/cpp-code-examples/includeTest2/f_without_include.cpp",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    this.assertContainsRelation(this.repo.getEntity("foo"), DependencyType.CALL, "bar");
	    this.assertNotContainsRelation(this.repo.getEntity("foo2"), DependencyType.CALL, "bar");
	}
	
	@Test
	public void should_find_include_relation_in_conditional_macro_block() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/includeTest3/inc_macro_test.c",
	    		"./src/test/resources/cpp-code-examples/includeTest3/fx.h",
	    		"./src/test/resources/cpp-code-examples/includeTest3/fy.h",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    this.assertContainsRelation(this.repo.getEntity(FileUtil.uniqFilePath(srcs[0])), DependencyType.IMPORT, FileUtil.uniqFilePath(srcs[1]));
	    this.assertContainsRelation(this.repo.getEntity(FileUtil.uniqFilePath(srcs[0])), DependencyType.IMPORT, FileUtil.uniqFilePath(srcs[2]));
	} 
}
