package depends.extractor.java;

import java.util.ArrayList;
import java.util.List;

import depends.extractor.AbstractLangProcessor;
import depends.extractor.FileParser;
import depends.relations.Inferer;

public class JavaProcessor extends AbstractLangProcessor {
    private static final String JAVA_LANG = "java";
    private static final String JAVA_SUFFIX = ".java";
    
    public JavaProcessor(String inputDir, String[] includeDir) {
    	super(inputDir,includeDir);
		inferer = new Inferer(entityRepo,new JavaImportLookupStrategy(),new JavaBuiltInType());
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
	protected FileParser createFileParser(String fileFullPath) {
		return new JavaFileParser(fileFullPath,entityRepo, inferer);
	}


	@Override
	public List<String> getErrors() {
		return new ArrayList<String>();
	}
}
