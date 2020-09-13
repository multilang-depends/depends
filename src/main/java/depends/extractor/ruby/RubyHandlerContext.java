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

package depends.extractor.ruby;

import depends.entity.Entity;
import depends.entity.PackageEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.FileParser;
import depends.extractor.HandlerContext;
import depends.extractor.ParserCreator;
import depends.importtypes.FileImport;
import depends.relations.Inferer;
import multilang.depends.util.file.FileUtil;

import java.util.Collection;
import java.util.concurrent.ExecutorService;

public class RubyHandlerContext extends HandlerContext {

	private IncludedFileLocator includedFileLocator;
	private ParserCreator parserCreator;
	public RubyHandlerContext(EntityRepo entityRepo, 
			IncludedFileLocator includedFileLocator,
			ExecutorService executorService,
			Inferer inferer, ParserCreator parserCreator) {
		super(entityRepo,inferer);
		this.includedFileLocator = includedFileLocator;
		this.parserCreator = parserCreator;
	}
	
	public Entity foundNamespace(String nampespaceName, Integer startLine) {
		Entity parentEntity = currentFile();
		if (latestValidContainer()!=null) 
			parentEntity = latestValidContainer();
		PackageEntity pkgEntity = new PackageEntity(nampespaceName, parentEntity,idGenerator.generateId());
		entityRepo.add(pkgEntity);
		entityStack.push(pkgEntity);
		return pkgEntity;
	}

	public void processSpecialFuncCall(String methodName, Collection<String> params, Integer startLine) {
		// Handle Import relation
		if(methodName.equals("require") || methodName.equals("require_relative")) { 
			for (String importedFilename:params) {
				if (!importedFilename.endsWith(".rb")) importedFilename = importedFilename + ".rb";
				String dir = FileUtil.getLocatedDir(currentFile().getRawName().uniqName());
				String inclFileName = includedFileLocator.uniqFileName(dir,importedFilename);
				if (inclFileName==null) {
					System.err.println("Warning: cannot found included file " + importedFilename );
					continue;
				}
				FileParser importedParser = parserCreator.createFileParser(inclFileName);
				try {
					System.out.println("parsing "+inclFileName);
					importedParser.parse();
				} catch (Exception e) {
					System.err.println("parsing error in "+inclFileName);
				}
				foundNewImport(new FileImport(inclFileName));
			}
		}
		// Handle Extend relation
		else if (methodName.equals("extend")) {
			for (String moduleName:params) {
				foundExtends(moduleName);
			}
		}
		// Handle mixin relation
		else if (methodName.equals("include")) {
			for (String moduleName:params) {
				foundMixin(moduleName);
			}
		}
		// attribute methods
		else if (methodName.equals("attr_accessor")||methodName.equals("attr_writer")||methodName.equals("attr_reader")) {
			for (String name:params) {
				name = name.replace(":", ""); //remove symbol
				foundMethodDeclarator(name,startLine);
			}
		} 
	}



}
