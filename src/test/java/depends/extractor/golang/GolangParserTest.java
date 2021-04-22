package depends.extractor.golang;

import depends.extractor.ParserTest;

public abstract class GolangParserTest extends ParserTest{

	public void init() {
		langProcessor = new GoProcessor();
		super.init();
	}
	
	public GoFileParser createParser(String src) {
		return new GoFileParser(src,entityRepo, bindingResolver);
	}

}
