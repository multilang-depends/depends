package depends.extractor.cpp.cdt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTPreprocessorFunctionStyleMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorObjectStyleMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorStatement;

public class MacroExtractor {
	ArrayList<String> macroVars = new ArrayList<>();
	ArrayList<String> macroFuncs = new ArrayList<>();

	public MacroExtractor(IASTPreprocessorStatement[] statements) {
		for (int statementIndex=0;statementIndex<statements.length;statementIndex++) {
			if (statements[statementIndex] instanceof IASTPreprocessorFunctionStyleMacroDefinition) {
				IASTPreprocessorFunctionStyleMacroDefinition funcMacro = (IASTPreprocessorFunctionStyleMacroDefinition)statements[statementIndex];
				macroFuncs.add(funcMacro.getName().getRawSignature());
			}else if (statements[statementIndex] instanceof IASTPreprocessorObjectStyleMacroDefinition) {
				IASTPreprocessorObjectStyleMacroDefinition varMacro = (IASTPreprocessorObjectStyleMacroDefinition)statements[statementIndex];
				macroVars.add(varMacro.getName().getRawSignature());
			}
		}
	}
	public List<String> getMacroVars() {
		return macroVars;
	}
	public List<String> getMacroFuncs() {
		return macroFuncs;
	}	
}
