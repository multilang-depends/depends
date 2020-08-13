package depends.extractor.cpp;

import org.eclipse.cdt.core.dom.ast.IASTPreprocessorMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IMacroBinding;

import java.util.HashMap;
import java.util.Map;

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
			macros.put(((IMacroBinding)def.getName().resolveBinding()).toString(), new String(def.getExpansion()).intern());
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
