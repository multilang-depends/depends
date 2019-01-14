package depends.extractor.ruby;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import depends.entity.Entity;
import depends.entity.PackageEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.HandlerContext;
import depends.extractor.ruby.RubyParser.Function_call_paramContext;
import depends.extractor.ruby.RubyParser.PrimaryFunctionCall0Context;
import depends.importtypes.FileImport;
import depends.importtypes.Import;

public class RubyHandlerContext extends HandlerContext {

	public RubyHandlerContext(EntityRepo entityRepo) {
		super(entityRepo);
	}
	
	public Entity foundNamespace(String nampespaceName) {
		PackageEntity pkgEntity = new PackageEntity(nampespaceName, currentFile(),idGenerator.generateId());
		entityRepo.add(pkgEntity);
		entityStack.push(pkgEntity);
		return pkgEntity;
	}

	public void processSpecialFuncCall(String methodName, Collection<String> params, IncludedFileLocator includedFileLocator) {
		// Handle Import relation
		if(methodName.equals("require") || methodName.equals("require_relative")) { 
			for (String importedFilename:params) {
				if (!importedFilename.endsWith(".rb")) importedFilename = importedFilename + ".rb";
				String inclFileName = includedFileLocator.uniqFileName(currentFile().getRawName(),importedFilename);
				if (inclFileName==null) {
					System.err.println("cannot found included file" + inclFileName);
					continue;
				}
				foundNewImport(new FileImport(inclFileName));
			}
		}
		// Handle Extend relation
		else if (methodName.equals("extend")) {
			for (String parentName:params) {
				foundExtends(parentName);
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
				foundMethodDeclarator(name, null, new ArrayList<>());
			}
		}		
	}


}
