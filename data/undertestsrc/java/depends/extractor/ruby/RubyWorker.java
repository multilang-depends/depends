package depends.extractor.ruby;

import java.util.ArrayList;
import java.util.List;

import depends.extractor.AbstractLangWorker;
import depends.extractor.FileParser;
import depends.extractor.cpp.cdt.PreprocessorHandler;
import depends.relations.Inferer;

public class RubyWorker extends AbstractLangWorker {
    private static final String LANG = "ruby";
    private static final String[] SUFFIX = new String[] {".rb",".ruby"};
    PreprocessorHandler preprocessorHandler;
    public RubyWorker(String inputDir, String[] includeDir) {
    	super(inputDir,includeDir);
    	preprocessorHandler = new PreprocessorHandler(super.includePaths());
		inferer = new Inferer(entityRepo,new RubyImportLookupStrategy(),new RubyBuiltInType());
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
		return new RubyFileParser(fileFullPath,entityRepo);
	}

	public List<String> getErrors(){
		return new ArrayList<String>(preprocessorHandler.getNotExistedIncludedFiles());
	}
}
