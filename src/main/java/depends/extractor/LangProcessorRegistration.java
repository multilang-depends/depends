/*
MIT License

Copyright (c) 2018-2019 Gang ZHANG

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

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
