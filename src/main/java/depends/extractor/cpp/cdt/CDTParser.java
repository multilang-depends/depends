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

package depends.extractor.cpp.cdt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.parser.IScanner;
import org.eclipse.cdt.core.parser.NullLogService;
import org.eclipse.cdt.core.parser.ParserMode;
import org.eclipse.cdt.internal.core.dom.parser.AbstractGNUSourceCodeParser;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTTranslationUnit;
import org.eclipse.cdt.internal.core.dom.parser.cpp.GNUCPPSourceParser;

import depends.extractor.cpp.Scanner;

@SuppressWarnings("deprecation")
public class CDTParser {
	NullLogService NULL_LOG = new NullLogService();
	protected Map<String, String> macroMap ;
	
	protected List<String> sysIncludePath = new ArrayList<>();
	
	public CDTParser() {
	}
	
	public CDTParser(List<String> includesPath) {
	        this.sysIncludePath = includesPath;
	}
	
	public IASTTranslationUnit parse(String file, Map<String, String> macroMap   ) {
		try {
			this.macroMap = macroMap;
			return getTranslationUnitofCPP(file);
		} catch (IOException e) {
		}
		return new CASTTranslationUnit();
	}

	
	public IASTTranslationUnit getTranslationUnitofCPP(String file) throws IOException {
		
		IScanner scanner = Scanner.buildScanner(file,macroMap,sysIncludePath,false);
		if (scanner==null) return null;

		AbstractGNUSourceCodeParser sourceCodeParser = new GNUCPPSourceParser(
				scanner, ParserMode.COMPLETE_PARSE,  new NullLogService(),
				new GPPParserExtensionConfigurationExtension(), null);
		IASTTranslationUnit tu =  sourceCodeParser.parse();
		return tu;
	}



}
