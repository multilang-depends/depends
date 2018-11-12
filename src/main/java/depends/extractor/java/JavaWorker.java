package depends.extractor.java;

import java.util.ArrayList;
import java.util.List;

import depends.extractor.AbstractLangWorker;
import depends.extractor.FileParser;
import depends.util.Configure;

public class JavaWorker extends AbstractLangWorker {
    public static final String JAVA_LANG = "java";
    public static final String JAVA_SUFFIX = ".java";
    
    public JavaWorker(Configure configure) {
    	super(configure);
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


	@Override
	public List<String> getErrors() {
		return new ArrayList<String>();
	}
}
