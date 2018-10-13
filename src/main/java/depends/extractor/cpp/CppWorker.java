package depends.extractor.cpp;

import depends.extractor.AbstractLangWorker;
import depends.extractor.FileParser;

public class CppWorker extends AbstractLangWorker {
    private static final String LANG = "java";
    private static final String SUFFIX = ".cpp";
    
    public CppWorker() {

    }


	@Override
	public String supportedLanguage() {
		return LANG;
	}

	@Override
	public String fileSuffix() {
		return SUFFIX;
	}


	@Override
	protected FileParser getFileParser(String fileFullPath) {
		return new CppFileParser(fileFullPath,entityRepo);
	}

	
}
