package depends;

import depends.extractor.AbstractLangProcessor;
import depends.extractor.LangProcessorRegistration;

public class LangRegister {


	public LangRegister() {
		add (new depends.extractor.java.JavaProcessor());
		add (new depends.extractor.cpp.CppProcessor());
		add (new depends.extractor.ruby.RubyProcessor());	}

	public void register() {

	}
	
	private void add(AbstractLangProcessor langProcessor) {
		LangProcessorRegistration.getRegistry().register(langProcessor);
	}
}


