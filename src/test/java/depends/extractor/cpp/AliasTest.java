package depends.extractor.cpp;
import depends.deptypes.DependencyType;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.fail;

public class AliasTest extends CppParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void test_genericTypes() throws IOException {
	    String src = "./src/test/resources/cpp-code-examples/Alias.cpp";
	    CppFileParser parser = createParser();
        parser.parse(src);
        resolveAllBindings();
        this.assertContainsRelation(entityRepo.getEntity("bar"), DependencyType.CALL, "F.foo");
	}
	
	@Test
	public void test_refer_to_alias_type_should_work() throws IOException {
	    String src = "./src/test/resources/cpp-code-examples/AliasType.cpp";
	    CppFileParser parser = createParser();
        parser.parse(src);
        resolveAllBindings();
        this.assertContainsRelation(entityRepo.getEntity("C"), DependencyType.INHERIT, "A");
	}
	
	@Test
	public void test_multi_declares_should_only_count_actual_referred() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/cpp-code-examples/MultiDeclareRef.h",
	    		"./src/test/resources/cpp-code-examples/MultiDeclareRef.cpp",
	    	    };
	    
	    for (String src:srcs) {
		    CppFileParser parser = createParser();
		    parser.parse(src);
	    }
        resolveAllBindings();
        this.assertContainsRelation(entityRepo.getEntity("bar"), DependencyType.CALL, "foo");
	}
	
	@Ignore
	public void test_header_files_not_contains_include_should_be_resolved() {
		/*非规范的include形式，和include顺序有关，例如
		 A header file contains 
		     class T
		 A.hpp // the file use T, but not include T, because it always use after previous header file  
		     typedef T1 T;
		 * */
		//TODO: 
		fail("to be implemented");
	}

}
