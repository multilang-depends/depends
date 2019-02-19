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

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.cpp.CppFileParser;
import depends.relations.Inferer;
import depends.util.FileUtil;

public class CdtCppFileParser extends CppFileParser {

	private PreprocessorHandler preprocessorHandler ;
	private Inferer inferer;

	public CdtCppFileParser(String fileFullPath, EntityRepo entityRepo,PreprocessorHandler preprocessorHandler, Inferer inferer) {
		super(fileFullPath, entityRepo,inferer);
		this.preprocessorHandler = preprocessorHandler;
		this.fileFullPath = FileUtil.uniqFilePath(fileFullPath);
		this.inferer = inferer;

	}
	@Override
	public void parse() throws IOException {
		parse(true);
	}
	
	/**
	 * 
	 * @param isInScope whether the parse is invoked by project file or an 'indirect' included file
	 */
	public void parse(boolean isInScope) {
		/** If file already exist, skip it */
		Entity fileEntity = entityRepo.getEntity(fileFullPath);
		if (fileEntity!=null && fileEntity instanceof FileEntity) {
			FileEntity t = ((FileEntity)fileEntity);
			if (!t.isInProjectScope() && isInScope)
				t.setInProjectScope(true);
			return;
		}
		
		CppVisitor bridge = new CppVisitor(fileFullPath, entityRepo, preprocessorHandler,inferer);
		IASTTranslationUnit translationUnit = (new CDTParser(preprocessorHandler.getIncludePaths())).parse(fileFullPath);
		translationUnit.accept(bridge);
	}
	
}
