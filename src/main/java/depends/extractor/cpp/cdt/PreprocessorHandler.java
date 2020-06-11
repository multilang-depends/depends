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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTFileLocation;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorStatement;
import org.eclipse.cdt.internal.core.parser.scanner.ScannerUtility;

import multilang.depends.util.file.FileTraversal;
import multilang.depends.util.file.FileTraversal.IFileVisitor;
import multilang.depends.util.file.FileUtil;

public class PreprocessorHandler {
	private List<String> includePaths;
	private String inputSrcPath;
	private HashSet<String> allFiles = new HashSet<>();
	public PreprocessorHandler(String inputSrcPath, List<String> includePaths){
		this.inputSrcPath = inputSrcPath;
		this.includePaths = includePaths;
		buildAllFiles();
	}
	
	class AllFileVisitor implements IFileVisitor{
		@Override
		public void visit(File file) {
			try {
				allFiles.add(file.getCanonicalPath());
			} catch (IOException e) {
			}
		}
	}
	private void buildAllFiles() {
		allFiles = new HashSet<>();
		AllFileVisitor v = new AllFileVisitor();
		if (inputSrcPath!=null) {
			FileTraversal ft = new FileTraversal(v,false,true);
			ft.travers(inputSrcPath);
		}
		for (String includePath:includePaths) {
			FileTraversal ft = new FileTraversal(v,false,true);
			ft.travers(includePath);
		}
	}

	private boolean existFile(String checkPath) {
		checkPath = FileUtil.uniformPath(checkPath);
		return allFiles.contains(checkPath);
	}
	
	public List<String> getDirectIncludedFiles(IASTPreprocessorStatement[] statements, String fileLocation) {
		ArrayList<String> includedFullPathNames = new ArrayList<>();
		for (int statementIndex=0;statementIndex<statements.length;statementIndex++) {
			if (statements[statementIndex] instanceof IASTPreprocessorIncludeStatement)
			{
				IASTPreprocessorIncludeStatement incl = (IASTPreprocessorIncludeStatement)(statements[statementIndex]);
				if (!incl.getFileLocation().getFileName().equals(fileLocation))
					continue;
				String path = resolveInclude(incl);
				if (!existFile(path)) {
					continue;
				}
				if (FileUtil.isDirectory(path)) {
					continue;
				}
				includedFullPathNames.add(path);
			}
		}
		return includedFullPathNames;
	}
	private String resolveInclude(IASTPreprocessorIncludeStatement incl) {
		String path = incl.toString();
		int pos = path.indexOf(' ');
		path = path.substring(pos+1).trim();
		if (path.startsWith("\"") || path.startsWith("<")){
			path = path.substring(1);
			path = path.substring(0,path.length()-1);
		}
		//First search in local directory
		IASTFileLocation location = incl.getFileLocation();
		String locationDir = FileUtil.getLocatedDir(location.getFileName());
		ArrayList<String> searchPath = new ArrayList<>();
		searchPath.add(locationDir);
		searchPath.addAll(includePaths);
		for (String includePath:searchPath) {
			String checkPath = ScannerUtility.createReconciledPath(includePath,path);
			if (existFile(checkPath)) {
				return FileUtil.uniqFilePath(checkPath);
			}
		}
		return "";
	}
	

	public List<String> getIncludePaths() {
		return includePaths;
	}
}
