package depends.extractor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/* Registration of the lang processors. 
 * */
public class LangProcessorRegistration {
	private static LangProcessorRegistration inst = new LangProcessorRegistration();
	public HashMap<String, AbstractLangProcessor> langProcessors = new HashMap<>();
	public static LangProcessorRegistration getRegistry() {
		return inst;
	}
	public AbstractLangProcessor getProcessorOf(String lang) {
		return langProcessors.get(lang);
	}
	public void register(AbstractLangProcessor processor) {
		if (getProcessorOf(processor.supportedLanguage())!=null) return;
		langProcessors.put(processor.supportedLanguage(), processor);
	}
	public Collection<String> getLangs() {
		ArrayList<String> langs = new ArrayList<>();
		langProcessors.values().forEach(item->{langs.add(item.supportedLanguage());});
		return langs;
	}
}
