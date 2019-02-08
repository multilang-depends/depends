package depends.extractor.ruby;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import depends.extractor.AbstractLangWorker;
import depends.extractor.FileParser;
import depends.extractor.ParserCreator;
import depends.extractor.ruby.antlr.RubyFileAntlrParser;
import depends.extractor.ruby.jruby.JRubyFileParser;
import depends.relations.Inferer;

public class RubyWorker extends AbstractLangWorker implements ParserCreator{
    private static final String LANG = "ruby";
    private static final String[] SUFFIX = new String[] {".rb"};
    IncludedFileLocator preprocessorHandler;
	private ExecutorService executor;
    public RubyWorker(String inputDir, String[] includeDir) {
    	super(inputDir,includeDir);
    	preprocessorHandler = new IncludedFileLocator(super.includePaths());
		inferer = new Inferer(entityRepo,new RubyImportLookupStrategy(),new RubyBuiltInType());
		executor = Executors.newSingleThreadExecutor();
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
	public FileParser createFileParser(String fileFullPath) {
//		return new RubyFileAntlrParser(fileFullPath,entityRepo,executor,preprocessorHandler,inferer,this);
		return new JRubyFileParser(fileFullPath,entityRepo,executor,preprocessorHandler,inferer,this);
	}

	public List<String> getErrors(){
		return new ArrayList<String>();
	}


	@Override
	protected void finalize() throws Throwable {
		this.executor.shutdown();
		super.finalize();
	}
	
}
