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
