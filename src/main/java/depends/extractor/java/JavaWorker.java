package depends.extractor.java;

import depends.extractor.AbstractLangWorker;
import depends.extractor.FileParser;

public class JavaWorker extends AbstractLangWorker {
    public static final String JAVA_LANG = "java";
    public static final String JAVA_SUFFIX = ".java";
    
    public JavaWorker() {

    }


	@Override
	public String supportedLanguage() {
		return JAVA_LANG;
	}

	@Override
	public String[] fileSuffixes() {
		return new String[] {JAVA_SUFFIX};
	}


	@Override
	protected FileParser getFileParser(String fileFullPath) {
		return new JavaFileParser(fileFullPath,entityRepo);
	}

	
}
