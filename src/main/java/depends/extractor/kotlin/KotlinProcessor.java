package depends.extractor.kotlin;

import depends.entity.repo.BuiltInType;
import depends.extractor.AbstractLangProcessor;
import depends.extractor.FileParser;
import depends.extractor.java.JavaBuiltInType;
import depends.extractor.java.JavaFileParser;
import depends.extractor.java.JavaImportLookupStrategy;
import depends.relations.ImportLookupStrategy;

public class KotlinProcessor extends AbstractLangProcessor {

	public KotlinProcessor() {
    	super(true);
	}

	@Override
	public String supportedLanguage() {
		return "kotlin";
	}

	@Override
	public String[] fileSuffixes() {
		return new String[] {".kt"};
	}

	@Override
	public ImportLookupStrategy getImportLookupStrategy() {
		return new JavaImportLookupStrategy();
	}

	@Override
	public BuiltInType getBuiltInType() {
		return new JavaBuiltInType();
	}

	@Override
	protected FileParser createFileParser(String fileFullPath) {
		return new KotlinFileParser(fileFullPath,entityRepo, inferer);
	}

}
