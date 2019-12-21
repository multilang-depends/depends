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

public class MacroMemoryRepo extends MacroRepo{

	private Map<String, Map<String, String>> fileMacroDefinition = new HashMap<>();

	private void put(String file, Map<String, String> macros) {
		Map<String, String> existingMacros = fileMacroDefinition.get(file);
		if (existingMacros==null)
			fileMacroDefinition.put(file, macros);
		else
			existingMacros.putAll(macros);
	}
	private void put(String file, IASTPreprocessorMacroDefinition[] macroDefinitions) {
		Map<String, String> macros = new HashMap<>();
		for (IASTPreprocessorMacroDefinition def:macroDefinitions) {
			macros.put(def.getName().toString().intern(), new String(def.getExpansion()).intern());
		}
		Map<String, String>  existingMacros = fileMacroDefinition.get(file);
		if (existingMacros==null)
			fileMacroDefinition.put(file, macros);
		else
			existingMacros.putAll(macros);
	}

	@Override
	public Map<String, String> get(String file) {
		return fileMacroDefinition.get(file);
	}
	@Override
	public void putMacros(String fileFullPath,Map<String, String> macroMap, IASTPreprocessorMacroDefinition[] macroDefinitions) {
		put(fileFullPath,macroMap);
		put(fileFullPath,macroDefinitions);
	}

	
}
