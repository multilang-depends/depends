package depends.extractor.cpp.g4;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.junit.Test;

import depends.entity.repo.EntityRepo;
import depends.extractor.cpp.cdt.CDTParser;

public class PreprocessorHandlerTest {
	@Test
	public void test() throws IOException {
		EntityRepo repo = new EntityRepo();
        String src = "./src/test/resources/cpp-code-examples/includesTest/EntryFile.cpp";
        Antlr4CppFileParser handler = new Antlr4CppFileParser(src, repo,new ArrayList<>());
        handler.parse();
	}

}
