package depends.extractor.cpp.cdt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import depends.util.FileUtil;

public class PreprocessorHandler {
	FileIndex fileIndex ;
	private String fileName;
	private List<String> includedFullPathNames;
	private List<String> includeSearchPath;
	
	public PreprocessorHandler(List<String> includeSearchPath, FileIndex fileIndex) {
		this.includeSearchPath = includeSearchPath;
		this.fileIndex = fileIndex;
	}
	public void handlePreprocessors(IASTPreprocessorStatement[] statements, String fileName) {
		this.fileName = fileName;
		includedFullPathNames= new ArrayList<>();
		if (!fileIndex.contains(fileName))
			processIncludes(statements);
		else
			includedFullPathNames =  fileIndex.get(fileName);
		
	}

	private void processIncludes(IASTPreprocessorStatement[] statements) {
		System.out.println(statements.length);
		for (int statementIndex=0;statementIndex<statements.length;statementIndex++) {
			if (statements[statementIndex] instanceof IASTPreprocessorIncludeStatement)
			{
				IASTPreprocessorIncludeStatement incl = (IASTPreprocessorIncludeStatement)(statements[statementIndex]);
				if (incl.getPath().isEmpty()) {
					System.out.println("include file do not exists!"+incl.toString());
					continue;
				}
				
				this.includedFullPathNames.add(FileUtil.uniqFilePath(incl.getPath()));
				String explandedPath = fileIndex.add(fileName,incl.getPath());
				if (!fileIndex.contains(explandedPath)) {
					IASTTranslationUnit translationUnit = (new CDTParser(includeSearchPath)).parse(explandedPath);
					PreprocessorHandler processor = new PreprocessorHandler(includeSearchPath, fileIndex);
					processor.handlePreprocessors(translationUnit.getIncludeDirectives(),explandedPath);
				}
			}
		}
	}

	public List<String>  getIncludedFullPathNames() {
		return this.includedFullPathNames;
	}

	public void setIncludePath(List<String> includeSearchPath) {
		this.includeSearchPath = includeSearchPath;
	}
}
