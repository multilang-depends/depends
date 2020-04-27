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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.cpp.CppFileParser;
import depends.extractor.cpp.MacroRepo;
import depends.relations.Inferer;
import depends.util.FileUtil;

public class CdtCppFileParser extends CppFileParser {

	private PreprocessorHandler preprocessorHandler ;
	private Inferer inferer;
	private MacroRepo macroRepo;

	public CdtCppFileParser(String fileFullPath, EntityRepo entityRepo,PreprocessorHandler preprocessorHandler, Inferer inferer, MacroRepo macroRepo) {
		super(fileFullPath, entityRepo,inferer);
		this.preprocessorHandler = preprocessorHandler;
		this.fileFullPath = FileUtil.uniqFilePath(fileFullPath);
		this.inferer = inferer;
		this.macroRepo= macroRepo;
		}
	@Override
	public void parse() throws IOException {
		Map<String, String> macroMap = new HashMap<>(macroRepo.getDefaultMap());
		parse(true,macroMap);
	}
	
	/**
	 * 
	 * @param isInScope whether the parse is invoked by project file or an 'indirect' included file
	 * @return 
	 */
	public void parse(boolean isInScope,Map<String, String> macroMap) {
		/** If file already exist, skip it */
		Entity fileEntity = entityRepo.getEntity(fileFullPath);
		if (fileEntity!=null && fileEntity instanceof FileEntity) {
			FileEntity t = ((FileEntity)fileEntity);
			if (!t.isInProjectScope() && isInScope)
				t.setInProjectScope(true);
			return;
		}
		
		CppVisitor bridge = new CppVisitor(fileFullPath, entityRepo, preprocessorHandler,inferer);
		IASTTranslationUnit tu = (new CDTParser(preprocessorHandler.getIncludePaths())).parse(fileFullPath,macroMap);
		boolean containsIncludes = false;
		for (String incl:preprocessorHandler.getDirectIncludedFiles(tu.getAllPreprocessorStatements(),fileFullPath)) {
			CdtCppFileParser importedParser = new CdtCppFileParser(incl, entityRepo, preprocessorHandler,inferer,macroRepo);
			importedParser.parse(false,macroMap);
			Map<String, String> macros = macroRepo.get(incl);
			if (macros!=null)
				macroMap.putAll(macros);
			containsIncludes = true;
		}
		if (containsIncludes) {
			tu = (new CDTParser(preprocessorHandler.getIncludePaths())).parse(fileFullPath,macroMap);
		}
		macroRepo.putMacros(this.fileFullPath,macroMap,tu.getMacroDefinitions());
		tu.accept(bridge);
		fileEntity = entityRepo.getEntity(fileFullPath);
		((FileEntity)fileEntity).cacheAllExpressions();
		bridge.done();
		return;
	}
	
}
