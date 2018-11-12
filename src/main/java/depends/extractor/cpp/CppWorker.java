package depends.extractor.cpp;

import java.util.ArrayList;
import java.util.List;

import depends.extractor.AbstractLangWorker;
import depends.extractor.FileParser;
import depends.extractor.cpp.cdt.CdtCppFileParser;
import depends.extractor.cpp.cdt.PreprocessorHandler;
import depends.util.Configure;

public class CppWorker extends AbstractLangWorker {
    private static final String LANG = "cpp";
    private static final String[] SUFFIX = new String[] {".cpp",".cc",".c",".h",".hpp",".hh"};
    PreprocessorHandler preprocessorHandler;
    public CppWorker(Configure configure) {
    	super(configure);
    	preprocessorHandler = new PreprocessorHandler(super.includePaths());
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
		return new CdtCppFileParser(fileFullPath,entityRepo,preprocessorHandler);
		//return new Antlr4CppFileParser(fileFullPath,entityRepo,super.includePaths());
	}

	public List<String> getErrors(){
		return new ArrayList<String>(preprocessorHandler.getNotExistedIncludedFiles());
	}
}
