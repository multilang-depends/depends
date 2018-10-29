package depends.extractor.cpp.cdt;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

public class PreprocessorHandler {
	GlobalIncludeMap includeMap  = GlobalIncludeMap.INSTANCE;
	private String fileName;
	public void handlePreprocessors(IASTPreprocessorStatement[] statements, String fileName) {
		this.fileName = fileName;
		if (!includeMap.contains(fileName))
			processIncludes(statements);
		
	}

	private void processIncludes(IASTPreprocessorStatement[] statements) {
		for (int statementIndex=0;statementIndex<statements.length;statementIndex++) {
			if (statements[statementIndex] instanceof IASTPreprocessorIncludeStatement)
			{
				IASTPreprocessorIncludeStatement incl = (IASTPreprocessorIncludeStatement)(statements[statementIndex]);
				String explandedPath = includeMap.add(fileName,incl.getPath());
				if (!includeMap.contains(explandedPath)) {
					IASTTranslationUnit translationUnit = (new CDTParser()).parse(explandedPath);
					translationUnit.accept(new ASTVisitor() {
						@Override
						public int visit(IASTTranslationUnit tu) {
							handlePreprocessors(tu.getIncludeDirectives(),explandedPath);
							return super.visit(tu);
						}
					});
				}
			}
		}
	}
}
