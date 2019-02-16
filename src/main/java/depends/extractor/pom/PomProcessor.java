package depends.extractor.pom;

import depends.entity.repo.BuiltInType;
import depends.extractor.AbstractLangProcessor;
import depends.extractor.FileParser;
import depends.extractor.empty.EmptyBuiltInType;
import depends.extractor.empty.EmptyImportLookupStategy;
import depends.relations.ImportLookupStrategy;

public class PomProcessor extends AbstractLangProcessor {

	public PomProcessor() {
		super(false);
	}
	
	@Override
	public String supportedLanguage() {
		return "pom";
	}

	@Override
	public String[] fileSuffixes() {
		return new String[] {".pom"};
	}

	@Override
	public ImportLookupStrategy getImportLookupStrategy() {
		return new EmptyImportLookupStategy();
	}

	@Override
	public BuiltInType getBuiltInType() {
		return new EmptyBuiltInType();
	}

	@Override
	protected FileParser createFileParser(String fileFullPath) {
		return new PomFileParser(fileFullPath,entityRepo);
	}
}
