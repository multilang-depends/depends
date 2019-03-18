package depends.extractor.python;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.antlr.v4.runtime.ParserRuleContext;

import depends.entity.ContainerEntity;
import depends.entity.DecoratedEntity;
import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.FunctionEntity;
import depends.entity.PackageEntity;
import depends.entity.TypeEntity;
import depends.entity.VarEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.python.Python3Parser.ClassdefContext;
import depends.extractor.python.Python3Parser.DecoratedContext;
import depends.extractor.python.Python3Parser.DecoratorContext;
import depends.extractor.python.Python3Parser.Dotted_as_nameContext;
import depends.extractor.python.Python3Parser.FuncdefContext;
import depends.extractor.python.Python3Parser.Global_stmtContext;
import depends.extractor.python.Python3Parser.Import_as_nameContext;
import depends.extractor.python.Python3Parser.Import_fromContext;
import depends.extractor.python.Python3Parser.Nonlocal_stmtContext;
import depends.extractor.python.Python3Parser.TfpdefContext;
import depends.extractor.ruby.IncludedFileLocator;
import depends.relations.Inferer;
import depends.util.FileUtil;

public class Python3CodeListener extends Python3BaseListener {

	private PythonHandlerContext context;
	private PythonParserHelper helper = PythonParserHelper.getInst();
	private ExpressionUsage expressionUsage;
	private EntityRepo entityRepo;
	private IncludedFileLocator includeFileLocator;
	private PythonProcessor pythonProcessor;
	private Inferer inferer;

	public Python3CodeListener(String fileFullPath, EntityRepo entityRepo, Inferer inferer,IncludedFileLocator includeFileLocator,PythonProcessor pythonProcessor) {
		
		this.context = new PythonHandlerContext(entityRepo,inferer);
		this.expressionUsage = new ExpressionUsage(context, entityRepo, helper, inferer);
		FileEntity fileEntity = context.startFile(fileFullPath);
		this.entityRepo = entityRepo;
		this.includeFileLocator = includeFileLocator;
		this.inferer = inferer;
		this.pythonProcessor = pythonProcessor;

		String dir = FileUtil.uniqFilePath(FileUtil.getLocatedDir(fileFullPath));
		if (entityRepo.getEntity(dir)==null) {
			entityRepo.add(new PackageEntity(dir,entityRepo.generateId()));
		}
		
		PackageEntity packageEntity = (PackageEntity) entityRepo.getEntity(dir);
		String moduleName = fileEntity.getRawName().substring(packageEntity.getRawName().length()+1);
		if (moduleName.endsWith(".py"))
			moduleName= moduleName.substring(0,moduleName.length()-".py".length());
		packageEntity.addChild(moduleName, fileEntity);
	}

	@Override
	public void enterImport_from(Import_fromContext ctx) {
		String moduleName = ctx.dotted_name().getText();
		String fullName = foundImportedModuleOrPackage(moduleName);
		if (fullName!=null) {
			if (ctx.import_as_names()==null) {//import *
				ContainerEntity moduleEntity = (ContainerEntity)(entityRepo.getEntity(fullName));
				for (FunctionEntity func:moduleEntity.getFunctions()) {
					context.foundNewImport(new NameAliasImport(fullName, func	,func.getRawName()));
				}
				for ( VarEntity var:moduleEntity.getVars()) {
					context.foundNewImport(new NameAliasImport(fullName, var	,var.getRawName()));
				}
				if (moduleEntity instanceof PackageEntity) {
					for (Entity file:moduleEntity.getChildren()) {
						String fileName = file.getRawName().substring(fullName.length());
						context.foundNewImport(new NameAliasImport(file.getRawName(), file, fileName));
					}
				}
			}else {
				for (Import_as_nameContext item:ctx.import_as_names().import_as_name()) {
					String name = item.NAME(0).getText();
					String alias = name;
					if (item.NAME().size()>1)
						alias = item.NAME(1).getText();
					Entity itemEntity = inferer.resolveName(entityRepo.getEntity(fullName), name, true);
					context.foundNewImport(new NameAliasImport(fullName, itemEntity	,alias));
				}
			}
		}
		super.enterImport_from(ctx);
	}



	@Override
	public void enterDotted_as_name(Dotted_as_nameContext ctx) {
		String originalName = ctx.dotted_name().getText();
		String aliasedName = originalName;
		//Alias
		if (ctx.NAME()!=null) {
			aliasedName = ctx.NAME().getText();
		}
		String fullName = foundImportedModuleOrPackage(originalName);
		context.foundNewImport(new NameAliasImport(fullName,entityRepo.getEntity(fullName),aliasedName));
		super.enterDotted_as_name(ctx);
	}



	private String foundImportedModuleOrPackage(String originalName) {
		String importedName = originalName.replace(".", File.separator);
		String fullName = includeFileLocator.uniqFileName(context.currentFile().getRawName(), importedName);
		if (fullName==null) {
			fullName = includeFileLocator.uniqFileName(context.currentFile().getRawName(), importedName+".py");
		}
		if (fullName!=null) {
			if (FileUtil.isDirectory(fullName)) {
				File d = new File(fullName);
				File[] files = d.listFiles();
				for (File file:files) {
					if (!file.isDirectory()) {
						if (file.getAbsolutePath().endsWith(".py")) {
							visitIncludedFile(FileUtil.uniqFilePath(file.getAbsolutePath()));
						}
					}
				}
			}else {
				visitIncludedFile(fullName);
			}
		}
		return fullName;
	}

	private void visitIncludedFile(String fullName) {
		PythonFileParser importedParser = new PythonFileParser(fullName, entityRepo, includeFileLocator,inferer, pythonProcessor);
		try {
			importedParser.parse();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}



	@Override
	public void enterFuncdef(FuncdefContext ctx) {
        String functionName = ctx.NAME().getText();
        context.foundMethodDeclarator(functionName, null, new ArrayList<>());
        super.enterFuncdef(ctx);
	}
	
	

	@Override
	public void enterTfpdef(TfpdefContext ctx) {
		String paramName = ctx.NAME().getText();
		context.addMethodParameter(paramName);
		super.enterTfpdef(ctx);
	}

	@Override
	public void exitFuncdef(FuncdefContext ctx) {
		context.exitLastedEntity();
		super.exitFuncdef(ctx);
	}

	@Override
	public void enterClassdef(ClassdefContext ctx) {
		String name = ctx.NAME().getText();
		TypeEntity type = context.foundNewType(name);
		ArrayList<String> baseClasses = this.helper.getArgList(ctx.arglist());
		baseClasses.forEach(base->type.addExtends(base));
		
		super.enterClassdef(ctx);
	}
	
	@Override
	public void exitClassdef(ClassdefContext ctx) {
		context.exitLastedEntity();
		super.exitClassdef(ctx);
	}
	
    @Override
	public void enterGlobal_stmt(Global_stmtContext ctx) {
    	//TODO: need to check
		context.foundVarDefinition(context.globalScope(), helper.getName(ctx));
    	super.enterGlobal_stmt(ctx);
	}

	@Override
	public void enterNonlocal_stmt(Nonlocal_stmtContext ctx) {
		//TODO: it is about scope , will be impl asap
		//context.foundVarDefinition(parent, helper.getName(ctx));
		super.enterNonlocal_stmt(ctx);
	}

	@Override
    public void exitDecorated(DecoratedContext ctx) {
    	String name = helper.getDecoratedElementName(ctx);
    	if (name==null) {
            super.exitDecorated(ctx);
    		return;
    	}
		Entity entity = context.foundEntityWithName(name);
		if (entity instanceof DecoratedEntity) {
	    	for (DecoratorContext decorator:ctx.decorators().decorator()){
		        ((DecoratedEntity)entity).addAnnotation(decorator.dotted_name().getText());
		    	//TODO: Annotation parameters  helper.getArgList(decorator.arglist()));
	    	}
		}
        super.exitDecorated(ctx);
    }

	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterEveryRule(ctx);
	}
}

