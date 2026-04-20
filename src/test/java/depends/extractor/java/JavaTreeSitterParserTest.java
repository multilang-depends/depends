package depends.extractor.java;

import depends.extractor.ParserTest;

public abstract class JavaTreeSitterParserTest extends ParserTest {
    public void init() {
        langProcessor = new JavaTreeSitterProcessor();
        super.init();
    }

    public JavaTreeSitterFileParser createParser() {
        return new JavaTreeSitterFileParser(entityRepo, bindingResolver);
    }
}
