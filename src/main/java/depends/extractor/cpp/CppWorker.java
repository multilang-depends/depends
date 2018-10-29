package depends.extractor.cpp;

import depends.extractor.AbstractLangWorker;
import depends.extractor.FileParser;
import depends.extractor.cpp.cdt.CdtCppFileParser;

public class CppWorker extends AbstractLangWorker {
    private static final String LANG = "cpp";
    private static final String[] SUFFIX = new String[] {".cpp",".cc",".c"};
    
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
		return new CdtCppFileParser(fileFullPath,entityRepo,super.includePaths());
	}

	
}
