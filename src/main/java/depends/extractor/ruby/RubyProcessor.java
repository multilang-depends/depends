package depends.extractor.ruby;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import depends.entity.repo.BuiltInType;
import depends.extractor.AbstractLangProcessor;
import depends.extractor.FileParser;
import depends.extractor.ParserCreator;
import depends.extractor.ruby.jruby.JRubyFileParser;
import depends.relations.ImportLookupStrategy;

public class RubyProcessor extends AbstractLangProcessor implements ParserCreator{
    private static final String LANG = "ruby";
    private static final String[] SUFFIX = new String[] {".rb"};
	private ExecutorService executor;
    public RubyProcessor(String inputDir, String[] includeDirs) {
    	super(inputDir,includeDirs);
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
		executor = Executors.newSingleThreadExecutor();
		return new JRubyFileParser(fileFullPath,entityRepo,executor,new IncludedFileLocator(super.includePaths()),inferer,this);
	}


	@Override
	protected void finalize() throws Throwable {
		this.executor.shutdown();
		super.finalize();
	}

	@Override
	public ImportLookupStrategy getImportLookupStrategy() {
		return new RubyImportLookupStrategy();
	}


	@Override
	public BuiltInType getBuiltInType() {
		return new RubyBuiltInType();
	}
	
}
