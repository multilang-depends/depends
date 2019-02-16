package depends.extractor.java;

import depends.entity.repo.BuiltInType;
import depends.extractor.AbstractLangProcessor;
import depends.extractor.FileParser;
import depends.relations.ImportLookupStrategy;

public class JavaProcessor extends AbstractLangProcessor {
    private static final String JAVA_LANG = "java";
    private static final String JAVA_SUFFIX = ".java";
    
    public JavaProcessor() {
    	super(false);
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
	public ImportLookupStrategy getImportLookupStrategy() {
		return new JavaImportLookupStrategy();
	}
	
	@Override
	public BuiltInType getBuiltInType() {
		return new JavaBuiltInType();
	}
}
