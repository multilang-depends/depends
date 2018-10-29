package depends.extractor.cpp.cdt;

import java.io.IOException;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.parser.IScannerExtensionConfiguration;
import org.eclipse.cdt.core.dom.parser.c.ANSICParserExtensionConfiguration;
import org.eclipse.cdt.core.dom.parser.c.GCCScannerExtensionConfiguration;
import org.eclipse.cdt.core.dom.parser.cpp.GPPParserExtensionConfiguration;
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
import org.eclipse.cdt.internal.core.dom.parser.c.GNUCSourceParser;
import org.eclipse.cdt.internal.core.dom.parser.cpp.GNUCPPSourceParser;
import org.eclipse.cdt.internal.core.parser.scanner.CPreprocessor;

public class CDTParser {
	NullLogService NULL_LOG = new NullLogService();
	public IASTTranslationUnit parse(String file   ) {
		/*
		 * *    QUICK_PARSE
				Does not parse inside functions or included files
				
				STRUCTURAL_PARSE
				    Does not parse inside functions but parses included files
				
				COMPLETE_PARSE
				    Parses inside functions and included files
				
				COMPLETION_PARSE
				    Parses inside functions and included files, stops at offsets, and optimizes symbol query lookups
				
				SELECTION_PARSE
				    Parses inside functions and included files, stops at offsets, and provides semantic information about a selected range*
		 */
		CodeReader cr;
		try {
			cr = new CodeReader(file);
			if (file.endsWith(".c"))
				return getTranslationUnitofC(file, new String(cr.buffer));
			else
				return getTranslationUnitofCPP(file,new String(cr.buffer));
		} catch (IOException e) {
			System.err.println("File " + file + "does not exists!");
		}
		return new CASTTranslationUnit();
	}

	private IASTTranslationUnit getTranslationUnitofC(String file, String content) {
		IScannerExtensionConfiguration configuration = GCCScannerExtensionConfiguration
				.getInstance();
		IScanner scanner = new CPreprocessor(FileContent.create(file,
				content.toCharArray()), new ScannerInfo(), ParserLanguage.C,
				NULL_LOG, configuration, null);
		ANSICParserExtensionConfiguration conf = new ANSICParserExtensionConfiguration();
		
		AbstractGNUSourceCodeParser sourceCodeParser = new GNUCSourceParser(
				scanner, ParserMode.COMPLETE_PARSE, NULL_LOG,conf );
		IASTTranslationUnit astTranslationUnit =  sourceCodeParser.parse();
		return astTranslationUnit;
	}
	
	private IASTTranslationUnit getTranslationUnitofCPP(String file, String content) {
		IScannerExtensionConfiguration configuration = GPPScannerExtensionConfiguration
				.getInstance();
		IScanner scanner = new CPreprocessor(FileContent.create(file,
				content.toCharArray()), new ScannerInfo(), ParserLanguage.CPP,
				new NullLogService(), configuration, null);
		AbstractGNUSourceCodeParser sourceCodeParser = new GNUCPPSourceParser(
				scanner, ParserMode.COMPLETE_PARSE, new NullLogService(),
				new GPPParserExtensionConfiguration(), null);
		IASTTranslationUnit astTranslationUnit =  sourceCodeParser.parse();
		return astTranslationUnit;
	}

}
