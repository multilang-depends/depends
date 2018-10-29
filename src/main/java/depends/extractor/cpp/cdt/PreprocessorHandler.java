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
	GlobalIncludeMap includeMap  = GlobalIncludeMap.INSTANCE;
	private String fileName;
	private List<String> includedFullPathNames;
	public void handlePreprocessors(IASTPreprocessorStatement[] statements, String fileName) {
		this.fileName = fileName;
		includedFullPathNames= new ArrayList<>();
		if (!includeMap.contains(fileName))
			processIncludes(statements);
		else
			includedFullPathNames =  includeMap.get(fileName);
		
	}

	private void processIncludes(IASTPreprocessorStatement[] statements) {
		for (int statementIndex=0;statementIndex<statements.length;statementIndex++) {
			if (statements[statementIndex] instanceof IASTPreprocessorIncludeStatement)
			{
				IASTPreprocessorIncludeStatement incl = (IASTPreprocessorIncludeStatement)(statements[statementIndex]);
				
				if (incl.getPath().isEmpty()) {
					System.out.println("include file do not exists!"+incl.toString());
					continue;
				}
				
				this.includedFullPathNames.add(FileUtil.uniqFilePath(incl.getPath()));
				String explandedPath = includeMap.add(fileName,incl.getPath());
				if (!includeMap.contains(explandedPath)) {
					IASTTranslationUnit translationUnit = (new CDTParser()).parse(explandedPath);
					PreprocessorHandler processor = new PreprocessorHandler();
					processor.handlePreprocessors(translationUnit.getIncludeDirectives(),explandedPath);
				}
			}
		}
	}

	public List<String>  getIncludedFullPathNames() {
		return this.includedFullPathNames;
	}
}
