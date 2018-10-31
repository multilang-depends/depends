package depends.extractor.cpp;

import depends.extractor.AbstractLangWorker;
import depends.extractor.FileParser;
import depends.extractor.cpp.cdt.CdtCppFileParser;
import depends.extractor.cpp.g4.Antlr4CppFileParser;

public class CppWorker extends AbstractLangWorker {
    private static final String LANG = "cpp";
    private static final String[] SUFFIX = new String[] {".cpp",".cc",".c",".h",".hpp",".hh"};
    
    public CppWorker() {

    }


	@Override
	public String supportedLanguage() {
		return LANG;
	}

	@Override
	public String[] fileSuffixes() {
		return SUFFIX;
	}


	@Override
	protected FileParser getFileParser(String fileFullPath) {
	//	return new CdtCppFileParser(fileFullPath,entityRepo,super.includePaths());
		return new Antlr4CppFileParser(fileFullPath,entityRepo,super.includePaths());
	}

	
}
