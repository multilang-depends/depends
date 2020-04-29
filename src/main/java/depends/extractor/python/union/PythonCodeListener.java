package depends.extractor.python.union;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import depends.entity.ContainerEntity;
import depends.entity.DecoratedEntity;
import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.GenericName;
import depends.entity.PackageEntity;
import depends.entity.TypeEntity;
import depends.entity.VarEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.python.NameAliasImport;
import depends.extractor.python.PythonHandlerContext;
import depends.extractor.python.PythonParser.ArglistContext;
import depends.extractor.python.PythonParser.Assert_stmtContext;
import depends.extractor.python.PythonParser.Class_or_func_def_stmtContext;
import depends.extractor.python.PythonParser.ClassdefContext;
import depends.extractor.python.PythonParser.DecoratorContext;
import depends.extractor.python.PythonParser.Def_parameterContext;
import depends.extractor.python.PythonParser.Def_parametersContext;
import depends.extractor.python.PythonParser.Del_stmtContext;
import depends.extractor.python.PythonParser.Dotted_as_nameContext;
import depends.extractor.python.PythonParser.Dotted_nameContext;
import depends.extractor.python.PythonParser.Expr_stmtContext;
import depends.extractor.python.PythonParser.From_stmtContext;
import depends.extractor.python.PythonParser.FuncdefContext;
import depends.extractor.python.PythonParser.Global_stmtContext;
import depends.extractor.python.PythonParser.Import_as_nameContext;
import depends.extractor.python.PythonParser.Import_stmtContext;
import depends.extractor.python.PythonParser.NameContext;
import depends.extractor.python.PythonParser.Raise_stmtContext;
import depends.extractor.python.PythonParser.Return_stmtContext;
import depends.extractor.python.PythonParser.Yield_stmtContext;
import depends.extractor.python.PythonParserBaseListener;
import depends.extractor.ruby.IncludedFileLocator;
import depends.importtypes.FileImport;
import depends.relations.Inferer;
import depends.util.FileUtil;

public class PythonCodeListener extends PythonParserBaseListener{
	private PythonHandlerContext context;
	private ExpressionUsage expressionUsage;
	private EntityRepo entityRepo;
	private IncludedFileLocator includeFileLocator;
	private PythonProcessor pythonProcessor;
	private Inferer inferer;
	public PythonCodeListener(String fileFullPath, EntityRepo entityRepo, Inferer inferer,
			IncludedFileLocator includeFileLocator, PythonProcessor pythonProcessor) {
		this.context = new PythonHandlerContext(entityRepo, inferer);
		this.expressionUsage = new ExpressionUsage(context, entityRepo, inferer);
		FileEntity fileEntity = context.startFile(fileFullPath);
		this.entityRepo = entityRepo;
		this.includeFileLocator = includeFileLocator;
		this.inferer = inferer;
		this.pythonProcessor = pythonProcessor;

		String dir = FileUtil.uniqFilePath(FileUtil.getLocatedDir(fileFullPath));
		if (entityRepo.getEntity(dir) == null) {
			PackageEntity pacakgeEntity = new PackageEntity(dir, entityRepo.generateId());
			entityRepo.add(pacakgeEntity);
		}

		PackageEntity packageEntity = (PackageEntity) entityRepo.getEntity(dir);
		String moduleName = fileEntity.getRawName().uniqName().substring(packageEntity.getRawName().uniqName().length() + 1);
		if (moduleName.endsWith(".py"))
			moduleName = moduleName.substring(0, moduleName.length() - ".py".length());
		Entity.setParent(fileEntity, packageEntity);
		packageEntity.addChild(FileUtil.getShortFileName(fileEntity.getRawName().uniqName()).replace(".py", ""), fileEntity);
	}
	@Override
	public void enterImport_stmt(Import_stmtContext ctx) {
		String moduleName = null;
		for(Dotted_as_nameContext dotted_as_name:ctx.dotted_as_names().dotted_as_name()){
			moduleName = getName(dotted_as_name.dotted_name());
			String aliasName = moduleName;
			if (dotted_as_name.name()!=null) {
				aliasName = dotted_as_name.name().getText();
			}
			List<String> fullNames = foundImportedModuleOrPackage(0,moduleName);
			 
			for (String fullName:fullNames) {
				if (FileUtil.existFile(fullName) && !(FileUtil.isDirectory(fullName))) {
					context.foundNewImport(new FileImport(fullName));
				}
				context.foundNewImport(new NameAliasImport(fullName, entityRepo.getEntity(fullName), aliasName));
			}
		}
		super.enterImport_stmt(ctx);
	}
	@Override
	public void enterFrom_stmt(From_stmtContext ctx) {
		String moduleName = null;
		if (ctx.dotted_name() != null) {
			moduleName = ctx.dotted_name().getText();
		}
		int prefixDotCount = getDotCounter(ctx);

		 List<String> fullNames = foundImportedModuleOrPackage(prefixDotCount, moduleName);
		for (String fullName:fullNames) {

			if (ctx.import_as_names() == null) {// import *
				ContainerEntity moduleEntity = (ContainerEntity) (entityRepo.getEntity(fullName));
				if (moduleEntity != null) {
					for (Entity child:moduleEntity.getChildren()) {
						context.foundNewImport(new NameAliasImport(fullName, child, child.getRawName().uniqName()));
						context.foundNewAlias(child.getRawName(), child);
					}
					if (moduleEntity instanceof PackageEntity) {
						for (Entity file : moduleEntity.getChildren()) {
							if (file instanceof FileEntity) {
								String fileName = file.getRawName().uniqName().substring(fullName.length());
								context.foundNewImport(new NameAliasImport(file.getRawName().uniqName(), file, fileName));
								context.foundNewAlias(GenericName.build(FileUtil.getShortFileName(fileName).replace(".py", "")), file);
							}else {
								context.foundNewImport(new NameAliasImport(file.getRawName().uniqName(), file, file.getRawName().uniqName()));
								context.foundNewAlias(GenericName.build(FileUtil.getShortFileName(file.getRawName().uniqName())), file);
							}
						}
					}
					if (moduleEntity instanceof FileEntity) {
						String fileName = moduleEntity.getRawName().uniqName().substring(fullName.length());
						context.foundNewImport(new NameAliasImport(moduleEntity.getRawName().uniqName(), moduleEntity, fileName));
					}
				}
			} else {
				for (Import_as_nameContext item : ctx.import_as_names().import_as_name()) {
					String name = item.name(0).getText();
					String alias = name;
					if (item.name().size() > 1)
						alias = item.name(1).getText();
					if (FileUtil.isDirectory(fullName)) {
						String fileName = fullName + File.separator + name + ".py";
						if (FileUtil.existFile(fileName) && !(FileUtil.isDirectory(fileName))) {
							context.foundNewImport(new FileImport(fileName));
						}
					}
					if (FileUtil.existFile(fullName) && !(FileUtil.isDirectory(fullName))) {
						context.foundNewImport(new FileImport(fullName));
					}
					Entity itemEntity = inferer.resolveName(entityRepo.getEntity(fullName), GenericName.build(name), true);
					if (itemEntity != null) {
						context.foundNewAlias(GenericName.build(alias), itemEntity);
						context.foundNewImport(new NameAliasImport(itemEntity.getQualifiedName(), itemEntity, alias));
					}
				}
			}
		}
		super.enterFrom_stmt(ctx);
	}
	
	
	private int getDotCounter(From_stmtContext ctx) {
		int total = 0;
		if (ctx.DOT()!=null){
			total = ctx.DOT().size();
		}
		if (ctx.ELLIPSIS()!=null) {
			total += ctx.ELLIPSIS().size()*3;
		}
		return total;
	}
	private List<String> foundImportedModuleOrPackage(int prefixDotCount, String originalName) {
		String dir = FileUtil.getLocatedDir(context.currentFile().getRawName().uniqName());
		String preFix = "";
		for (int i = 0; i < prefixDotCount - 1; i++) {
			preFix = preFix + ".." + File.separator;
		}
		dir = dir + File.separator + preFix;
		String fullName = null;
		if (originalName != null) {
			String importedName = originalName.replace(".", File.separator);
			fullName = includeFileLocator.uniqFileName(dir, importedName);
			if (fullName == null) {
				fullName = includeFileLocator.uniqFileName(dir, importedName + ".py");
			}
		} else {
			fullName = FileUtil.uniqFilePath(dir);
		}
		if (fullName != null) {
			if (FileUtil.isDirectory(fullName)) {
				if (!FileUtil.uniqFilePath(fullName).equals(FileUtil.uniqFilePath(dir))) {
					File d = new File(fullName);
					File[] files = d.listFiles();
					for (File file : files) {
						if (!file.isDirectory()) {
							if (file.getAbsolutePath().endsWith(".py")) {
								visitIncludedFile(FileUtil.uniqFilePath(file.getAbsolutePath()));
							}
						}
					}
				}
			} else {
				visitIncludedFile(fullName);
			}
		}
		ArrayList<String> r = new ArrayList<>();
		if (fullName==null) return r;
		r.add(fullName);
		if (FileUtil.existFile(fullName+File.separator + "__init__.py")) {
			r.add( fullName+File.separator +"__init__.py");
		}
		return r;
	}

	private void visitIncludedFile(String fullName) {
		PythonFileParser importedParser = new PythonFileParser(fullName, entityRepo, includeFileLocator, inferer,
				pythonProcessor);
		try {
			importedParser.parse();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void enterFuncdef(FuncdefContext ctx) {
		String functionName ="<empty>";
		String name = getName(ctx.name());
		if (name!=null) {
			functionName = name;
		}
		
		context.foundMethodDeclarator(functionName);
		if (ctx.typedargslist()!=null) {
			List<String> parameters = getParameterList(ctx.typedargslist().def_parameters());
			for (String param : parameters) {
				VarEntity paramEntity = context.addMethodParameter(param);
				if (param.equals("self")) {
					paramEntity.setType(context.currentType());
				}
			}
		}
		super.enterFuncdef(ctx);
	}

	@Override
	public void exitFuncdef(FuncdefContext ctx) {
		context.exitLastedEntity();
		super.exitFuncdef(ctx);
	}
	
	
	@Override
	public void enterClassdef(ClassdefContext ctx) {
		String name = getName(ctx.name());
		TypeEntity type = context.foundNewType(name);
		List<String> baseClasses = getArgList(ctx.arglist());
		baseClasses.forEach(base -> type.addExtends(GenericName.build(base)));

		super.enterClassdef(ctx);
	}


	@Override
	public void exitClassdef(ClassdefContext ctx) {
		context.exitLastedEntity();
		super.exitClassdef(ctx);
	}
	
	private List<String> getParameterList(List<Def_parametersContext> def_parameters) {
		List<String> result = new ArrayList<>();
		for (Def_parametersContext params:def_parameters) {
			for (Def_parameterContext param:params.def_parameter()) {
				String p = getName( param.named_parameter().name());
				result.add(p);
			}
		
		}
		return result;
	}
	private String getName(NameContext name) {
		return name.getText();
	}
	
	private String getName(Dotted_nameContext dotted_name) {
		return dotted_name.getText();
	}
	
	private String getDecoratedName(Class_or_func_def_stmtContext ctx) {
		if (ctx.classdef()!=null) {
			return getName(ctx.classdef().name());
		}else if (ctx.funcdef()!=null) {
			return getName(ctx.funcdef().name());
		}
		return null;
	}
	
	private List<String> getArgList(ArglistContext arglist) {
		List<String> r = new ArrayList<>();
		if (arglist==null) return r;
		if (arglist.argument() == null) return r;
		if (arglist.argument().isEmpty()) return r;
		arglist.argument().forEach(arg->r.add(arg.getText()));
		return r;
	}
	
	
	/**
	 * class_or_func_def_stmt: decorator+ (classdef | funcdef);
	 */
	@Override
	public void exitClass_or_func_def_stmt(Class_or_func_def_stmtContext ctx) {
		String decoratedName = getDecoratedName(ctx);
		if (decoratedName!=null) {
			Entity entity = context.foundEntityWithName(GenericName.build(decoratedName));
			if (entity instanceof DecoratedEntity) {
				for (DecoratorContext decorator: ctx.decorator()) {
					String decoratorName = getName(decorator.dotted_name());
					((DecoratedEntity) entity).addAnnotation(GenericName.build(decoratorName));
				}
			}
		}
		super.exitClass_or_func_def_stmt(ctx);
	}
	
	
	@Override
	public void enterGlobal_stmt(Global_stmtContext ctx) {
		for (NameContext name:ctx.name()){
			context.foundGlobalVarDefinition(context.currentFile(),name.getText());
		}
		super.enterGlobal_stmt(ctx);
	}
	
	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterEveryRule(ctx);
	}
	@Override
	public void enterExpr_stmt(Expr_stmtContext ctx) {
		expressionUsage.startExpr();
		super.enterExpr_stmt(ctx);
	}
	@Override
	public void exitExpr_stmt(Expr_stmtContext ctx) {
		expressionUsage.stopExpr();
		super.exitExpr_stmt(ctx);
	}
	@Override
	public void enterDel_stmt(Del_stmtContext ctx) {
		expressionUsage.startExpr();
		super.enterDel_stmt(ctx);
	}
	@Override
	public void exitDel_stmt(Del_stmtContext ctx) {
		expressionUsage.stopExpr();
		super.exitDel_stmt(ctx);
	}
	@Override
	public void enterReturn_stmt(Return_stmtContext ctx) {
		expressionUsage.startExpr();
		super.enterReturn_stmt(ctx);
	}
	@Override
	public void exitReturn_stmt(Return_stmtContext ctx) {
		expressionUsage.stopExpr();
		super.exitReturn_stmt(ctx);
	}
	@Override
	public void enterRaise_stmt(Raise_stmtContext ctx) {
		expressionUsage.startExpr();
		super.enterRaise_stmt(ctx);
	}
	@Override
	public void exitRaise_stmt(Raise_stmtContext ctx) {
		expressionUsage.stopExpr();
		super.exitRaise_stmt(ctx);
	}
	@Override
	public void enterYield_stmt(Yield_stmtContext ctx) {
		expressionUsage.startExpr();
		super.enterYield_stmt(ctx);
	}
	@Override
	public void exitYield_stmt(Yield_stmtContext ctx) {
		expressionUsage.stopExpr();
		super.exitYield_stmt(ctx);
	}
	@Override
	public void enterAssert_stmt(Assert_stmtContext ctx) {
		expressionUsage.startExpr();
		super.enterAssert_stmt(ctx);
	}
	@Override
	public void exitAssert_stmt(Assert_stmtContext ctx) {
		expressionUsage.stopExpr();
		super.exitAssert_stmt(ctx);
	}
	public void done() {
		context.done();
	}
}
