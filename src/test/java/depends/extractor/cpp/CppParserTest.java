package depends.extractor.cpp;

import depends.extractor.ParserTest;
import depends.extractor.cpp.cdt.CdtCppFileParser;
import depends.extractor.cpp.cdt.PreprocessorHandler;

import java.util.ArrayList;

public abstract class CppParserTest extends ParserTest{
    protected PreprocessorHandler preprocessorHandler;
	private MacroRepo macroRepo;

	public void init() {
		langProcessor = new CppProcessor();
    	preprocessorHandler = new PreprocessorHandler("./src/test/resources/cpp-code-examples/",new ArrayList<>());
		super.init();
    	macroRepo = new MacroEhcacheRepo(entityRepo);
    }
	
	public CppFileParser createParser() {
		return new  CdtCppFileParser(entityRepo, preprocessorHandler, bindingResolver,macroRepo );
	}
}
