package depends.extractor.cpp.cdt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTPreprocessorFunctionStyleMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorObjectStyleMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorStatement;

import depends.util.FileUtil;

public class PreprocessorHandler {
	private HashMap<String, String> notExistedIncludedFiles = new HashMap<>();
	public Collection<String> getNotExistedIncludedFiles() {
		return notExistedIncludedFiles.values();
	}
	private List<String> includePaths;
	public PreprocessorHandler(List<String> includePaths){
		notExistedIncludedFiles = new HashMap<>();
		this.setIncludePaths(includePaths);
	}
	public List<String> getDirectIncludedFiles(IASTPreprocessorStatement[] statements) {
		ArrayList<String> includedFullPathNames = new ArrayList<>();
		for (int statementIndex=0;statementIndex<statements.length;statementIndex++) {
			if (statements[statementIndex] instanceof IASTPreprocessorIncludeStatement)
			{
				IASTPreprocessorIncludeStatement incl = (IASTPreprocessorIncludeStatement)(statements[statementIndex]);
				String path = FileUtil.uniqFilePath(incl.getPath());
				includedFullPathNames.add(path);
				if (!FileUtil.existFile(path)) {
					if (!notExistedIncludedFiles.containsKey(path)) {
						notExistedIncludedFiles.put(path,"Error: " + path + " does not exist in include path!");
					}
					continue;
				}
			}else if (statements[statementIndex] instanceof IASTPreprocessorFunctionStyleMacroDefinition) {
				System.out.println(statements[statementIndex].getRawSignature());
				IASTPreprocessorFunctionStyleMacroDefinition funcMacro = (IASTPreprocessorFunctionStyleMacroDefinition)statements[statementIndex];
				System.out.println(funcMacro.getName());
			}else if (statements[statementIndex] instanceof IASTPreprocessorObjectStyleMacroDefinition) {
				System.out.println(statements[statementIndex].getRawSignature());
				IASTPreprocessorObjectStyleMacroDefinition varMacro = (IASTPreprocessorObjectStyleMacroDefinition)statements[statementIndex];
				System.out.println(varMacro.getName());
			}
			
		}
		return includedFullPathNames;
	}
	
	
	public List<String> getIncludePaths() {
		return includePaths;
	}
	public void setIncludePaths(List<String> includePaths) {
		this.includePaths = includePaths;
	}
}
