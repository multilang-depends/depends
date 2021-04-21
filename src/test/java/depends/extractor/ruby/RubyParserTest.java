package depends.extractor.ruby;

import depends.extractor.FileParser;
import depends.extractor.ParserCreator;
import depends.extractor.ParserTest;
import depends.extractor.ruby.jruby.JRubyFileParser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public abstract class RubyParserTest extends ParserTest implements ParserCreator{
	public void init() {
		langProcessor = new RubyProcessor();
		super.init(true);
	}
	
	public FileParser createFileParser(String src) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		return new JRubyFileParser(src,entityRepo, executor,new IncludedFileLocator(includePaths()), inferer, this);
	}

	private List<String> includePaths() {
		return new ArrayList<>();
	}
}
