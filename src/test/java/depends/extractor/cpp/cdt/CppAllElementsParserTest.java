package depends.extractor.cpp.cdt;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import depends.extractor.cpp.CppFileParser;
import depends.extractor.cpp.CppParserTest;

public class CppAllElementsParserTest extends CppParserTest{
    @Before
    public void setUp() {
    	super.init();
    }
	
	@Test
	public void shouldFoundNamespaceDefinition() throws IOException {
	    String src = "./src/test/resources/cpp-code-examples/elementsTest/Elements.cpp";
	    CppFileParser parser = new  CdtCppFileParser(src,repo, new ArrayList<>(), fileIndex );
        parser.parse();
	}

	@Test
	public void shouldFoundNamespaceUsing() throws IOException {
	    String src = "./src/test/resources/cpp-code-examples/elementsTest/Elements.cpp";
	    CppFileParser parser = new  CdtCppFileParser(src,repo, new ArrayList<>(), fileIndex );
        parser.parse();
	}
	
	public void shouldFoundClassStructEnumUnionDefintion() throws IOException {
	    String src = "./src/test/resources/cpp-code-examples/elementsTest/Elements.cpp";
	    CppFileParser parser = new  CdtCppFileParser(src,repo, new ArrayList<>(), fileIndex );
        parser.parse();
	}
	
	public void shouldFoundSimpleMethodAndmemberMethodDeclarator() throws IOException {
	    String src = "./src/test/resources/cpp-code-examples/elementsTest/Elements.cpp";
	    CppFileParser parser = new  CdtCppFileParser(src,repo, new ArrayList<>(), fileIndex );
        parser.parse();
	}
	
	public void shouldFoundClassVarsAndMethodVars() throws IOException {
	    String src = "./src/test/resources/cpp-code-examples/elementsTest/Elements.cpp";
	    CppFileParser parser = new  CdtCppFileParser(src,repo, new ArrayList<>(), fileIndex );
        parser.parse();
	}
	
}
