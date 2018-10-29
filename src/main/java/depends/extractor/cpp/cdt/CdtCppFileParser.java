package depends.extractor.cpp.cdt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import org.eclipse.cdt.internal.core.dom.parser.c.GNUCSourceParser;
import org.eclipse.cdt.internal.core.dom.parser.cpp.GNUCPPSourceParser;
import org.eclipse.cdt.internal.core.parser.scanner.CPreprocessor;
import org.eclipse.cdt.internal.core.parser.scanner.InternalFileContent;

import depends.entity.repo.EntityRepo;
import depends.extractor.cpp.CppFileParser;
import depends.util.FileUtil;

public class CdtCppFileParser extends CppFileParser {

	private List<String> includeSearchPath = new ArrayList<>();

	public CdtCppFileParser(String fileFullPath, EntityRepo entityRepo, List<String> includeSearchPath) {
		super(fileFullPath, entityRepo);
		this.includeSearchPath = includeSearchPath;
	}
	@Override
	public void parse() throws IOException {
		CdtCppEntitiesListener bridge = new CdtCppEntitiesListener(FileUtil.uniqFilePath(fileFullPath), entityRepo, includeSearchPath );
		IASTTranslationUnit translationUnit = (new CDTParser(includeSearchPath)).parse(this.fileFullPath);
		translationUnit.accept(bridge);
	}
	
}
