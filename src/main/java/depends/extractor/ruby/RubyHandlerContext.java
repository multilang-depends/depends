package depends.extractor.ruby;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;

import depends.entity.Entity;
import depends.entity.PackageEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.HandlerContext;
import depends.importtypes.FileImport;
import depends.relations.Inferer;

public class RubyHandlerContext extends HandlerContext {

	private IncludedFileLocator includedFileLocator;
	private ExecutorService executorService;
	private Inferer inferer;

	public RubyHandlerContext(EntityRepo entityRepo, 
			IncludedFileLocator includedFileLocator,
			ExecutorService executorService,
			Inferer inferer) {
		super(entityRepo);
		this.includedFileLocator = includedFileLocator;
		this.executorService = executorService;
		this.inferer = inferer;
	}
	
	public Entity foundNamespace(String nampespaceName) {
		PackageEntity pkgEntity = new PackageEntity(nampespaceName, currentFile(),idGenerator.generateId());
		entityRepo.add(pkgEntity);
		entityStack.push(pkgEntity);
		return pkgEntity;
	}

	public void processSpecialFuncCall(String methodName, Collection<String> params) {
		// Handle Import relation
		if(methodName.equals("require") || methodName.equals("require_relative")) { 
			for (String importedFilename:params) {
				if (!importedFilename.endsWith(".rb")) importedFilename = importedFilename + ".rb";
				String inclFileName = includedFileLocator.uniqFileName(currentFile().getRawName(),importedFilename);
				if (inclFileName==null) {
					System.err.println("Warning: cannot found included file " + importedFilename + "\n(maybe it is just a sys/lib file we do not care)");
					continue;
				}
				RubyFileParser importedParser = new RubyFileParser(inclFileName,entityRepo,executorService,includedFileLocator,inferer);
				try {
					System.out.println("parsing "+inclFileName);
					importedParser.parse();
				} catch (IOException e) {
					e.printStackTrace();
				}
				foundNewImport(new FileImport(inclFileName));
			}
		}
		// Handle Extend relation
		else if (methodName.equals("extend")) {
			for (String moduleName:params) {
				foundMixin(moduleName);
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
