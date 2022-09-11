package depends.extractor.ruby;

import depends.extractor.FileParser;
import depends.extractor.IncludedFileLocator;
import depends.extractor.ParserCreator;
import depends.extractor.ParserTest;
import depends.extractor.ruby.jruby.JRubyFileParser;

import java.util.ArrayList;
import java.util.List;
public abstract class RubyParserTest extends ParserTest implements ParserCreator{
	public void init() {
		langProcessor = new RubyProcessor();
		super.init(true);
	}
	
	public FileParser createFileParser(String src) {
		return new JRubyFileParser(src,entityRepo, new IncludedFileLocator(includePaths()), bindingResolver, this);
	}

	private List<String> includePaths() {
		return new ArrayList<>();
	}
}
