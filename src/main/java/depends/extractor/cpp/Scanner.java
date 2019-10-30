package depends.extractor.cpp;

import java.io.IOException;
import java.util.Map;

import org.eclipse.cdt.core.dom.parser.IScannerExtensionConfiguration;
import org.eclipse.cdt.core.dom.parser.cpp.GPPScannerExtensionConfiguration;
import org.eclipse.cdt.core.index.IIndexFileLocation;
import org.eclipse.cdt.core.parser.CodeReader;
import org.eclipse.cdt.core.parser.FileContent;
import org.eclipse.cdt.core.parser.IScanner;
import org.eclipse.cdt.core.parser.IScannerInfo;
import org.eclipse.cdt.core.parser.NullLogService;
import org.eclipse.cdt.core.parser.ParserLanguage;
import org.eclipse.cdt.core.parser.ScannerInfo;
import org.eclipse.cdt.internal.core.parser.IMacroDictionary;
import org.eclipse.cdt.internal.core.parser.scanner.CPreprocessor;
import org.eclipse.cdt.internal.core.parser.scanner.InternalFileContent;
import org.eclipse.cdt.internal.core.parser.scanner.InternalFileContentProvider;

import depends.extractor.cpp.cdt.FileCache;

public class Scanner {

	public static IScanner buildScanner(String file, Map<String, String> macroMap) {
		String content = "";
		try {
			CodeReader cr = new CodeReader(file);
			content = new String(cr.buffer);
		} catch (IOException e) {
		}
//		IScannerInfo scannerInfo = new ScannerInfo(macroMap, sysIncludePath.toArray(new String[] {}));
		IScannerInfo scannerInfo = new ScannerInfo(macroMap, new String[] {});
		IScannerExtensionConfiguration configuration = GPPScannerExtensionConfiguration.getInstance(scannerInfo);
		InternalFileContentProvider ifcp = new InternalFileContentProvider() {
			@Override
			public InternalFileContent getContentForInclusion(String filePath, IMacroDictionary macroDictionary) {
				InternalFileContent c = FileCache.getInstance().get(filePath);
				if (c == null) {
					c = (InternalFileContent) FileContent.createForExternalFileLocation(filePath);
					FileCache.getInstance().put(filePath, c);
				}
				return c;
			}

			@Override
			public InternalFileContent getContentForInclusion(IIndexFileLocation ifl, String astPath) {
				InternalFileContent c = FileCache.getInstance().get(ifl);
				if (c == null) {
					c = (InternalFileContent) FileContent.create(ifl);
					FileCache.getInstance().put(ifl, c);
				}
				return c;
			}
		};
		ParserLanguage lang = ParserLanguage.CPP;
		if (file.endsWith(".c"))
			lang = ParserLanguage.C;
		IScanner scanner = new CPreprocessor(FileContent.create(file, content.toCharArray()), scannerInfo, lang,
				new NullLogService(), configuration, ifcp);
		scanner.setProcessInactiveCode(true);
		return scanner;
	}

}
