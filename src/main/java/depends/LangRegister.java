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

package depends;

import depends.extractor.AbstractLangProcessor;
import depends.extractor.LangProcessorRegistration;

public class LangRegister {


	public LangRegister() {
		add (new depends.extractor.java.JavaProcessor());
		add (new depends.extractor.cpp.CppProcessor());
		add (new depends.extractor.ruby.RubyProcessor());
		add (new depends.extractor.pom.PomProcessor());
		add (new depends.extractor.kotlin.KotlinProcessor());
		add (new depends.extractor.python.union.PythonProcessor());
	}
	
	public void register() {

	}
	
	private void add(AbstractLangProcessor langProcessor) {
		LangProcessorRegistration.getRegistry().register(langProcessor);
	}
}


