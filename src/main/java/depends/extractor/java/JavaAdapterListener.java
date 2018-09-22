package depends.extractor.java;

import java.util.ArrayList;
import java.util.Collection;

import depends.entity.repo.EntityRepo;
import depends.extractor.java.context.ClassTypeContextHelper;
import depends.extractor.java.context.FormalParameterListContextHelper;
import depends.extractor.java.context.UnannTypeContextHelper;
import depends.extractor.java.context.VariableDeclaratorContextHelper;
import depends.javaextractor.Java9BaseListener;
import depends.javaextractor.Java9Parser.AnnotationTypeDeclarationContext;
import depends.javaextractor.Java9Parser.AnnotationTypeElementDeclarationContext;
import depends.javaextractor.Java9Parser.BlockContext;
import depends.javaextractor.Java9Parser.ClassDeclarationContext;
import depends.javaextractor.Java9Parser.ClassTypeContext;
import depends.javaextractor.Java9Parser.ConstantDeclarationContext;
import depends.javaextractor.Java9Parser.EnhancedForStatementContext;
import depends.javaextractor.Java9Parser.EnumDeclarationContext;
import depends.javaextractor.Java9Parser.FieldDeclarationContext;
import depends.javaextractor.Java9Parser.InterfaceMethodDeclarationContext;
import depends.javaextractor.Java9Parser.InterfaceTypeContext;
import depends.javaextractor.Java9Parser.LocalVariableDeclarationContext;
import depends.javaextractor.Java9Parser.MethodDeclarationContext;
import depends.javaextractor.Java9Parser.MethodDeclaratorContext;
import depends.javaextractor.Java9Parser.NormalClassDeclarationContext;
import depends.javaextractor.Java9Parser.NormalInterfaceDeclarationContext;
import depends.javaextractor.Java9Parser.PackageDeclarationContext;
import depends.javaextractor.Java9Parser.ResourceContext;
import depends.javaextractor.Java9Parser.ResultContext;
import depends.javaextractor.Java9Parser.SingleStaticImportDeclarationContext;
import depends.javaextractor.Java9Parser.SingleTypeImportDeclarationContext;
import depends.javaextractor.Java9Parser.StaticImportOnDemandDeclarationContext;
import depends.javaextractor.Java9Parser.SuperclassContext;
import depends.javaextractor.Java9Parser.SuperinterfacesContext;
import depends.javaextractor.Java9Parser.TypeImportOnDemandDeclarationContext;

public class JavaAdapterListener extends Java9BaseListener{
	private JavaHandler handler;

	public JavaAdapterListener(String fileFullPath, EntityRepo entityRepo) {
        this.handler = new JavaHandler(entityRepo);
        handler.startFile(fileFullPath);
	}

	////////////////////////
    // Package
	@Override
	public void enterPackageDeclaration(PackageDeclarationContext ctx) {
		handler.foundPackageDeclaration(ctx.packageName().getText());
		super.enterPackageDeclaration(ctx);
	}

	///////////////////////
	// Class or Interface
	
	@Override
	public void exitClassDeclaration(ClassDeclarationContext ctx) {
		handler.exitLastedEntity();
		super.exitClassDeclaration(ctx);
	}
	
	@Override
	public void exitMethodDeclaration(MethodDeclarationContext ctx) {
		System.out.println(ctx.getText());
		handler.exitLastedEntity();
		super.exitMethodDeclaration(ctx);
	}

	@Override
	public void exitInterfaceMethodDeclaration(InterfaceMethodDeclarationContext ctx) {
		handler.exitLastedEntity();
		super.exitInterfaceMethodDeclaration(ctx);
	}
	
	@Override
	public void enterNormalClassDeclaration(NormalClassDeclarationContext ctx) {
		handler.foundClassOrInterface(ctx.identifier().getText());
    	super.enterNormalClassDeclaration(ctx);
	}
	

	@Override
	public void enterEnumDeclaration(EnumDeclarationContext ctx) {
		handler.foundClassOrInterface(ctx.identifier().getText());
    	super.enterEnumDeclaration(ctx);
	}


	@Override
	public void enterAnnotationTypeDeclaration(AnnotationTypeDeclarationContext ctx) {
		handler.foundClassOrInterface(ctx.identifier().getText());
    	super.enterAnnotationTypeDeclaration(ctx);
	}
	
	@Override
	public void enterNormalInterfaceDeclaration(NormalInterfaceDeclarationContext ctx) {
		handler.foundClassOrInterface(ctx.identifier().getText());
		super.enterNormalInterfaceDeclaration(ctx);
	}

	////////////////////////
	// Import
	@Override
	public void enterSingleTypeImportDeclaration(SingleTypeImportDeclarationContext ctx) {
		handler.foundImport(ctx.typeName().getText());
		super.enterSingleTypeImportDeclaration(ctx);
	}


	@Override
	public void enterTypeImportOnDemandDeclaration(TypeImportOnDemandDeclarationContext ctx) {
		handler.foundImport(ctx.packageOrTypeName().getText());
		super.enterTypeImportOnDemandDeclaration(ctx);
	}


	@Override
	public void enterSingleStaticImportDeclaration(SingleStaticImportDeclarationContext ctx) {
		handler.foundImport(ctx.typeName().getText());
		super.enterSingleStaticImportDeclaration(ctx);
	}


	@Override
	public void enterStaticImportOnDemandDeclaration(StaticImportOnDemandDeclarationContext ctx) {
		handler.foundImport(ctx.typeName().getText());
		super.enterStaticImportOnDemandDeclaration(ctx);
	}

	@Override
	public void enterSuperclass(SuperclassContext ctx) {
		ClassTypeContext classType = ctx.classType();
		handler.foundExtends(ClassTypeContextHelper.getClassName(classType));
		super.enterSuperclass(ctx);
	}


	@Override
	public void enterSuperinterfaces(SuperinterfacesContext ctx) {
		for(InterfaceTypeContext itf:ctx.interfaceTypeList().interfaceType()) {
			handler.foundImplements(ClassTypeContextHelper.getClassName(itf.classType()));
		}
		super.enterSuperinterfaces(ctx);
	}
	
	/////////////////////////////////////////////////////////
	// Method Parameters
	@Override
	public void enterMethodDeclarator(MethodDeclaratorContext ctx) {
		Collection<String> parameterTypes  = new ArrayList<>();
		if (ctx.formalParameterList()!=null) {
			parameterTypes = new FormalParameterListContextHelper(ctx.formalParameterList()).extractParameterTypeList();
		}
		handler.foundMethodDeclarator(ctx.identifier().getText(),parameterTypes);
		super.enterMethodDeclarator(ctx);
	}

	/////////////////////////////////////////////////////////
	// Field 
	@Override
	public void enterFieldDeclaration(FieldDeclarationContext ctx) {
		handler.foundVarDefintion(new UnannTypeContextHelper().calculateType(ctx.unannType()),new VariableDeclaratorContextHelper().extractVarList(ctx.variableDeclaratorList()));
		super.enterFieldDeclaration(ctx);
	}

	@Override
	public void enterConstantDeclaration(ConstantDeclarationContext ctx) {
		handler.foundVarDefintion(new UnannTypeContextHelper().calculateType(ctx.unannType()),new VariableDeclaratorContextHelper().extractVarList(ctx.variableDeclaratorList()));
		super.enterConstantDeclaration(ctx);
	}


	@Override
	public void enterAnnotationTypeElementDeclaration(AnnotationTypeElementDeclarationContext ctx) {
		handler.foundVarDefintion(new UnannTypeContextHelper().calculateType(ctx.unannType()),ctx.identifier().getText());
		super.enterAnnotationTypeElementDeclaration(ctx);
	}


	@Override
	public void enterLocalVariableDeclaration(LocalVariableDeclarationContext ctx) {
		handler.foundVarDefintion(new UnannTypeContextHelper().calculateType(ctx.unannType()),new VariableDeclaratorContextHelper().extractVarList(ctx.variableDeclaratorList()));
		super.enterLocalVariableDeclaration(ctx);
	}

	@Override
	public void enterEnhancedForStatement(EnhancedForStatementContext ctx) {
		handler.foundVarDefintion(new UnannTypeContextHelper().calculateType(ctx.unannType()),ctx.variableDeclaratorId().identifier().getText());
		super.enterEnhancedForStatement(ctx);
	}

	@Override
	public void enterResource(ResourceContext ctx) {
		handler.foundVarDefintion(new UnannTypeContextHelper().calculateType(ctx.unannType()),ctx.variableDeclaratorId().identifier().getText());
		super.enterResource(ctx);
	}
	
	@Override
	public void enterBlock(BlockContext ctx) {
		// TODO Auto-generated method stub
		super.enterBlock(ctx);
	}

	@Override
	public void exitBlock(BlockContext ctx) {
		// TODO Auto-generated method stub
		super.exitBlock(ctx);
	}

	@Override
	public void enterResult(ResultContext ctx) {
		// TODO Auto-generated method stub
		super.enterResult(ctx);
	}
}


// relations to be clarified:
// 1. functionName(ParametersList) throws ExceptionTypeList <-- exceptionTypeList should be parameter?
// 2. class defines members <-- what's the relation between them? 
// 3. 'Contains' does not defined in the code. Please clarify
// 4. (TODO): the arguments of generic type have not be calculated yet
// 5. Parameter is not about method - > var for java; it is method ( parameter type

