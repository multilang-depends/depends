package depends.extractor.java;

import depends.extractor.FileParser;

public class JavaTreeSitterProcessor extends JavaProcessor {
    private static final String JAVA_TS_LANG = "java-ts";

    @Override
    public String supportedLanguage() {
        return JAVA_TS_LANG;
    }

    @Override
    public FileParser createFileParser() {
        return new JavaTreeSitterFileParser(entityRepo, bindingResolver);
    }
}
