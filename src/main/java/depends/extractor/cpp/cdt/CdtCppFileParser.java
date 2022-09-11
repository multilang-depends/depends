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

import depends.entity.repo.EntityRepo;
import depends.extractor.cpp.CppFileParser;
import depends.extractor.cpp.MacroRepo;
import depends.relations.IBindingResolver;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CdtCppFileParser extends CppFileParser {
	private PreprocessorHandler preprocessorHandler ;
	private IBindingResolver bindingResolver;
	private MacroRepo macroRepo;

	public CdtCppFileParser(EntityRepo entityRepo, PreprocessorHandler preprocessorHandler, IBindingResolver bindingResolver, MacroRepo macroRepo) {
		super(entityRepo);
		this.preprocessorHandler = preprocessorHandler;
		this.bindingResolver = bindingResolver;
		this.macroRepo= macroRepo;
		}
	@Override
	protected void parseFile(String fileFullPath) throws IOException {
		Map<String, String> macroMap = new HashMap<>(macroRepo.getDefaultMap());
		parse(fileFullPath,macroMap);
	}
	
	/**
	 * 
	 * @param isInScope whether the parse is invoked by project file or an 'indirect' included file
	 * @return 
	 */
	public void parse(String fileFullPath,Map<String, String> macroMap) throws IOException {
		CppVisitor bridge = new CppVisitor(fileFullPath, entityRepo, preprocessorHandler, bindingResolver);
		IASTTranslationUnit tu = (new CDTParser(preprocessorHandler.getIncludePaths())).parse(fileFullPath,macroMap);
		boolean containsIncludes = false;
		for (String incl:preprocessorHandler.getDirectIncludedFiles(tu.getAllPreprocessorStatements(),fileFullPath)) {
			CdtCppFileParser importedParser = new CdtCppFileParser(entityRepo, preprocessorHandler, bindingResolver,macroRepo);
			importedParser.parse(incl);
			Map<String, String> macros = macroRepo.get(incl);
			if (macros!=null)
				macroMap.putAll(macros);
			containsIncludes = true;
		}
		if (containsIncludes) {
			tu = (new CDTParser(preprocessorHandler.getIncludePaths())).parse(fileFullPath,macroMap);
		}
		macroRepo.putMacros(fileFullPath,macroMap,tu.getMacroDefinitions());
		tu.accept(bridge);
		return;
	}

	@Override
	protected boolean isPhase2Files(String fileFullPath) {
		if (fileFullPath.endsWith(".h") || fileFullPath.endsWith(".hh") || fileFullPath.endsWith(".hpp")
				|| fileFullPath.endsWith(".hxx"))
			return true;
		return false;
	}
}
