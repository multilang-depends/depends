package depends;

import depends.extractor.AbstractLangProcessor;
import depends.extractor.LangProcessorRegistration;

public class LangRegister {

	private String inputDir;
	private String[] includeDirs;

	public LangRegister(String inputDir, String[] includeDirs) {
		this.inputDir = inputDir;
		this.includeDirs = includeDirs;
	}

	public void register() {
		add (new depends.extractor.java.JavaProcessor(inputDir, includeDirs));
		add (new depends.extractor.cpp.CppProcessor(inputDir, includeDirs));
		add (new depends.extractor.ruby.RubyProcessor(inputDir, includeDirs));
	}
	
	private void add(AbstractLangProcessor langProcessor) {
		LangProcessorRegistration.getRegistry().register(langProcessor);
	}
}


