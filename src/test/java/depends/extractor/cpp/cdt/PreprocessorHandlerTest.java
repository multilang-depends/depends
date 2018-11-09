package depends.extractor.cpp.cdt;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.junit.Test;

public class PreprocessorHandlerTest {
	@Test
	public void test() {
        String src = "./src/test/resources/cpp-code-examples/includesTest/EntryFile.cpp";
        FileIndex fileIndex = new FileIndex();
		PreprocessorHandler handler = new PreprocessorHandler(new ArrayList<>(), fileIndex);
        IASTTranslationUnit translationUnit = (new CDTParser(new ArrayList<>())).parse(src);
		handler.handlePreprocessors(translationUnit.getIncludeDirectives(),src);
	}

}
