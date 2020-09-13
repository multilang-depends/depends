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

import depends.entity.FunctionEntity;
import depends.entity.VarEntity;
import depends.extractor.cpp.CppHandlerContext;
import depends.relations.Inferer;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorFunctionStyleMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorObjectStyleMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorStatement;

import java.util.ArrayList;

public class MacroExtractor {

	private final String fileLocation;
	private IASTPreprocessorStatement[] statements;

	public MacroExtractor(IASTPreprocessorStatement[] statements, String fileLocation) {
		this.statements = statements;
		this.fileLocation = fileLocation;
	}

	public void extract(CppHandlerContext context) {
		for (int statementIndex=0;statementIndex<statements.length;statementIndex++) {
			if (statements[statementIndex] instanceof IASTPreprocessorFunctionStyleMacroDefinition) {
				IASTPreprocessorFunctionStyleMacroDefinition funcMacro = (IASTPreprocessorFunctionStyleMacroDefinition)statements[statementIndex];
				if (!funcMacro.getFileLocation().getFileName().equals(fileLocation))
					continue;
				String func = funcMacro.getName().getRawSignature();
				FunctionEntity funcEntity = context.foundMethodDeclarator(func, Inferer.buildInType.getRawName().uniqName(), new ArrayList<>(),funcMacro.getFileLocation().getStartingLineNumber());
				funcEntity.setLine(funcMacro.getFileLocation().getStartingLineNumber());
				context.exitLastedEntity();
			}else if (statements[statementIndex] instanceof IASTPreprocessorObjectStyleMacroDefinition) {
				IASTPreprocessorObjectStyleMacroDefinition varMacro = (IASTPreprocessorObjectStyleMacroDefinition)statements[statementIndex];
				if (!varMacro.getFileLocation().getFileName().equals(fileLocation))
					continue;
				String var = varMacro.getName().getRawSignature();
				VarEntity varEntity = context.foundVarDefinition(var, Inferer.buildInType.getRawName(), new ArrayList<>(),varMacro.getFileLocation().getStartingLineNumber());
				varEntity.setLine(varMacro.getFileLocation().getStartingLineNumber());
			}
		}
	}
}
