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

package depends.extractor.cpp;

import static depends.deptypes.DependencyType.*;
import java.util.ArrayList;
import java.util.List;

import depends.entity.repo.BuiltInType;
import depends.extractor.AbstractLangProcessor;
import depends.extractor.FileParser;
import depends.extractor.cpp.cdt.CdtCppFileParser;
import depends.extractor.cpp.cdt.PreprocessorHandler;
import depends.relations.ImportLookupStrategy;

public class CppProcessor extends AbstractLangProcessor {
	private static final String LANG = "cpp";
	private static final String[] SUFFIX = new String[] { ".cpp", ".cc", ".c", ".c++", ".h", ".hpp", ".hh", ".cxx", ".hxx" };
	PreprocessorHandler preprocessorHandler = null;

	MacroRepo macroRepo = null;

	public CppProcessor() {
		super(false);

	}

	@Override
	public String supportedLanguage() {
		return LANG;
	}

	@Override
	public String[] fileSuffixes() {
		return SUFFIX;
	}

	@Override
	protected FileParser createFileParser(String fileFullPath) {
		if (macroRepo == null) {
			macroRepo = new MacroEhcacheRepo(entityRepo);
			macroRepo.buildDefaultMap(super.includePaths());
		}
		if (preprocessorHandler==null) {
			preprocessorHandler = new PreprocessorHandler(super.inputSrcPath,super.includePaths());
		}
		return new CdtCppFileParser(fileFullPath, entityRepo, preprocessorHandler, inferer, macroRepo);
	}

	@Override
	public ImportLookupStrategy getImportLookupStrategy() {
		return new CppImportLookupStrategy();
	}

	@Override
	public BuiltInType getBuiltInType() {
		return new CppBuiltInType();
	}

	@Override
	public List<String> supportedRelations() {
		ArrayList<String> depedencyTypes = new ArrayList<>();
		depedencyTypes.add(IMPORT);
		depedencyTypes.add(CONTAIN);
		depedencyTypes.add(IMPLEMENT);
		depedencyTypes.add(INHERIT);
		depedencyTypes.add(CALL);
		depedencyTypes.add(PARAMETER);
		depedencyTypes.add(RETURN);
		depedencyTypes.add(SET);
		depedencyTypes.add(CREATE);
		depedencyTypes.add(USE);
		depedencyTypes.add(CAST);
		depedencyTypes.add(THROW);
		depedencyTypes.add(IMPLLINK);
		return depedencyTypes;
	}

	@Override
	protected boolean isPhase2Files(String fileFullPath) {
		if (fileFullPath.endsWith(".h") || fileFullPath.endsWith(".hh") || fileFullPath.endsWith(".hpp")
				|| fileFullPath.endsWith(".hxx"))
			return true;
		return false;
	}

}
