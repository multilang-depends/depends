package depends.extractor.cpp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.cdt.core.dom.ast.IASTPreprocessorMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IMacroBinding;
import org.eclipse.cdt.core.parser.IScanner;
import org.eclipse.cdt.core.parser.NullLogService;
import org.eclipse.cdt.core.parser.ParserMode;
import org.eclipse.cdt.internal.core.dom.parser.AbstractGNUSourceCodeParser;
import org.eclipse.cdt.internal.core.dom.parser.cpp.GNUCPPSourceParser;

import depends.extractor.cpp.cdt.GPPParserExtensionConfigurationExtension;
import depends.util.FileUtil;

public class MacroRepo {

	private Map<String, String> defaultMacroMap = new HashMap<>();
	private Map<String, Map<String, String>> fileMacroDefinition = new HashMap<>();

	public void put(String file, Map<String, String> macros) {
		Map<String, String> existingMacros = fileMacroDefinition.get(file);
		if (existingMacros==null)
			fileMacroDefinition.put(file, macros);
		else
			existingMacros.putAll(macros);
	}
	public void put(String file, IASTPreprocessorMacroDefinition[] macroDefinitions) {
		Map<String, String> macros = new HashMap<>();
		for (IASTPreprocessorMacroDefinition def:macroDefinitions) {
			macros.put(def.getName().toString(), new String(def.getExpansion()));
		}
		Map<String, String> existingMacros = fileMacroDefinition.get(file);
		if (existingMacros==null)
			fileMacroDefinition.put(file, macros);
		else
			existingMacros.putAll(macros);
	}

	public Map<String, String> buildDefaultMap(List<String> sysIncludePath) {
		for (String p : sysIncludePath) {
			if (!FileUtil.isDirectory(p)) {
				IScanner scanner = Scanner.buildScanner(p,defaultMacroMap);
				if (scanner==null) continue;
				AbstractGNUSourceCodeParser sourceCodeParser = new GNUCPPSourceParser(scanner,
						ParserMode.COMPLETE_PARSE, new NullLogService(), new GPPParserExtensionConfigurationExtension(),
						null);
				sourceCodeParser.parse();
				Map<String, IMacroBinding> macros = scanner.getMacroDefinitions();
				for (String key : macros.keySet()) {
					String exp = new String(macros.get(key).getExpansion());
					if (exp.length() > 0) {
						defaultMacroMap.put(key, exp);
					}

				}
			}
		}
		return defaultMacroMap;
	}
	
	public Map<String, String> getDefaultMap() {
		return defaultMacroMap;
	}

	public Map<String, String> get(String file) {
		return fileMacroDefinition.get(file);
	}

	
}
