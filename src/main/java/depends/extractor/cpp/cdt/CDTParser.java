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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.parser.IScannerExtensionConfiguration;
import org.eclipse.cdt.core.dom.parser.cpp.GPPScannerExtensionConfiguration;
import org.eclipse.cdt.core.parser.CodeReader;
import org.eclipse.cdt.core.parser.FileContent;
import org.eclipse.cdt.core.parser.IScanner;
import org.eclipse.cdt.core.parser.NullLogService;
import org.eclipse.cdt.core.parser.ParserLanguage;
import org.eclipse.cdt.core.parser.ParserMode;
import org.eclipse.cdt.core.parser.ScannerInfo;
import org.eclipse.cdt.internal.core.dom.parser.AbstractGNUSourceCodeParser;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTTranslationUnit;
import org.eclipse.cdt.internal.core.dom.parser.cpp.GNUCPPSourceParser;
import org.eclipse.cdt.internal.core.parser.scanner.CPreprocessor;

@SuppressWarnings("deprecation")
public class CDTParser {
	List<String> sysIncludePath = new ArrayList<>();

	public CDTParser() {
	}
	
	public CDTParser(List<String> includesPath) {
		for (String f:includesPath) {
			File file = new File(f);
			if (file.exists()) {
				try {
					sysIncludePath.add(file.getCanonicalPath());
				} catch (IOException e) {
				}
			}else {
				//System.err.println("include path " + f + " does not exist!");
			}
		}
	}
	NullLogService NULL_LOG = new NullLogService();
	Map<String, String> macroMap = new HashMap<>();
	public IASTTranslationUnit parse(String file   ) {
		CodeReader cr;
		try {
			cr = new CodeReader(file);
			return getTranslationUnitofCPP(file,new String(cr.buffer));
		} catch (IOException e) {
		}
		return new CASTTranslationUnit();
	}

	
	private IASTTranslationUnit getTranslationUnitofCPP(String file, String content) {
		IScannerExtensionConfiguration configuration = GPPScannerExtensionConfiguration
				.getInstance();
		IScanner scanner = new CPreprocessor(FileContent.create(file,
				content.toCharArray()), new ScannerInfo(new HashMap<>(),sysIncludePath.toArray(new String[] {})), ParserLanguage.CPP,
				new NullLogService(), configuration, null);
		AbstractGNUSourceCodeParser sourceCodeParser = new GNUCPPSourceParser(
				scanner, ParserMode.COMPLETE_PARSE, new NullLogService(),
				new GPPParserExtensionConfigurationExtension(), null);
		IASTTranslationUnit astTranslationUnit =  sourceCodeParser.parse();
		return astTranslationUnit;
	}

}
