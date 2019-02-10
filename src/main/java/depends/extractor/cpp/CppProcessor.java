package depends.extractor.cpp;

import depends.entity.repo.BuiltInType;
import depends.extractor.AbstractLangProcessor;
import depends.extractor.FileParser;
import depends.extractor.cpp.cdt.CdtCppFileParser;
import depends.extractor.cpp.cdt.PreprocessorHandler;
import depends.relations.ImportLookupStrategy;

public class CppProcessor extends AbstractLangProcessor {
    private static final String LANG = "cpp";
    private static final String[] SUFFIX = new String[] {".cpp",".cc",".c",".h",".hpp",".hh", ".cxx",".hxx"};
    PreprocessorHandler preprocessorHandler;
    public CppProcessor(String inputDir, String[] includeDir) {
    	super(inputDir,includeDir);
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
	protected FileParser createFileParser(String fileFullPath) {
    	preprocessorHandler = new PreprocessorHandler(super.includePaths());
		return new CdtCppFileParser(fileFullPath,entityRepo,preprocessorHandler,inferer);
	}

	@Override
	public ImportLookupStrategy getImportLookupStrategy() {
		return new CppImportLookupStrategy();
	}


	@Override
	public BuiltInType getBuiltInType() {
		return new CppBuiltInType();
	}
}
