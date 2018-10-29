package depends.extractor.java;

import java.util.ArrayList;
import java.util.List;

import depends.entity.IdGenerator;
import depends.entity.repo.EntityRepo;
import depends.extractor.HandlerContext;
import depends.extractor.java.context.AnnotationProcessor;
import depends.extractor.java.context.ClassTypeContextHelper;
import depends.extractor.java.context.ExpressionUsage;
import depends.extractor.java.context.FormalParameterListContextHelper;
import depends.extractor.java.context.IdentifierContextHelper;
import depends.extractor.java.context.QualitiedNameContextHelper;
import depends.extractor.java.context.VariableDeclaratorsContextHelper;
import depends.javaextractor.JavaParser.AnnotationConstantRestContext;
import depends.javaextractor.JavaParser.AnnotationMethodRestContext;
import depends.javaextractor.JavaParser.AnnotationTypeDeclarationContext;
import depends.javaextractor.JavaParser.BlockContext;
import depends.javaextractor.JavaParser.ClassDeclarationContext;
import depends.javaextractor.JavaParser.ConstDeclarationContext;
import depends.javaextractor.JavaParser.ConstructorDeclarationContext;
import depends.javaextractor.JavaParser.EnhancedForControlContext;
import depends.javaextractor.JavaParser.EnumConstantContext;
import depends.javaextractor.JavaParser.EnumDeclarationContext;
import depends.javaextractor.JavaParser.ExpressionContext;
import depends.javaextractor.JavaParser.FieldDeclarationContext;
import depends.javaextractor.JavaParser.ImportDeclarationContext;
import depends.javaextractor.JavaParser.InterfaceDeclarationContext;
import depends.javaextractor.JavaParser.InterfaceMethodDeclarationContext;
import depends.javaextractor.JavaParser.LocalVariableDeclarationContext;
import depends.javaextractor.JavaParser.MethodDeclarationContext;
import depends.javaextractor.JavaParser.PackageDeclarationContext;
import depends.javaextractor.JavaParser.ResourceContext;
import depends.javaextractor.JavaParser.TypeParameterContext;
import depends.javaextractor.JavaParser.TypeParametersContext;
import depends.javaextractor.JavaParserBaseListener;

public class JavaEntitiesListener extends JavaParserBaseListener {
	private HandlerContext context;
	private AnnotationProcessor annotationProcessor;
	private ExpressionUsage expressionUsage;
	private IdGenerator idGenerator;
	public JavaEntitiesListener(String fileFullPath, EntityRepo entityRepo) {
		this.context = new HandlerContext(entityRepo);
		idGenerator = entityRepo;
		annotationProcessor = new AnnotationProcessor(context);
		expressionUsage = new ExpressionUsage(context);
		context.startFile(fileFullPath);
	}

	////////////////////////
	// Package
	@Override
	public void enterPackageDeclaration(PackageDeclarationContext ctx) {
		context.foundNewPackage(QualitiedNameContextHelper.getName(ctx.qualifiedName()));
		super.enterPackageDeclaration(ctx);
	}

	////////////////////////
	// Import
	@Override
	public void enterImportDeclaration(ImportDeclarationContext ctx) {
		context.foundNewImport(ctx.qualifiedName().getText());
		super.enterImportDeclaration(ctx);
	}

	///////////////////////
	// Class or Interface
	// classDeclaration | enumDeclaration | interfaceDeclaration |
	/////////////////////// annotationTypeDeclaration
	@Override
	public void enterClassDeclaration(ClassDeclarationContext ctx) {
		context.foundNewType(ctx.IDENTIFIER().getText());
		// implements
		if (ctx.typeList() != null) {
			for (int i = 0; i < ctx.typeList().typeType().size(); i++) {
				context.foundImplements(ClassTypeContextHelper.getClassName(ctx.typeList().typeType().get(i)));
			}
		}
		// extends relation
		if (ctx.typeType() != null) {
			context.foundExtends(ClassTypeContextHelper.getClassName(ctx.typeType()));
		}

		if (ctx.typeParameters() != null) {
			foundTypeParametersUse(ctx.typeParameters());
		}
		annotationProcessor.processAnnotationModifier(ctx, "classOrInterfaceModifier");
		super.enterClassDeclaration(ctx);
	}

	@Override
	public void exitClassDeclaration(ClassDeclarationContext ctx) {
		exitLastEntity();
		super.exitClassDeclaration(ctx);
	}

	@Override
	public void enterEnumDeclaration(EnumDeclarationContext ctx) {
		context.foundNewType(ctx.IDENTIFIER().getText());
		annotationProcessor.processAnnotationModifier(ctx, "classOrInterfaceModifier");
		super.enterEnumDeclaration(ctx);
	}

	@Override
	public void exitEnumDeclaration(EnumDeclarationContext ctx) {
		exitLastEntity();
		super.exitEnumDeclaration(ctx);
	}

	/**
	 * interfaceDeclaration : INTERFACE IDENTIFIER typeParameters? (EXTENDS
	 * typeList)? interfaceBody ;
	 */
	@Override
	public void enterInterfaceDeclaration(InterfaceDeclarationContext ctx) {
		context.foundNewType(ctx.IDENTIFIER().getText());
		// type parameters
		if (ctx.typeParameters() != null) {
			foundTypeParametersUse(ctx.typeParameters());
		}
		// extends relation
		if (ctx.typeList() != null) {
			for (int i = 0; i < ctx.typeList().typeType().size(); i++) {
				context.foundExtends(ClassTypeContextHelper.getClassName(ctx.typeList().typeType().get(i)));
			}
		}
		annotationProcessor.processAnnotationModifier(ctx, "classOrInterfaceModifier");
		super.enterInterfaceDeclaration(ctx);
	}

	@Override
	public void exitInterfaceDeclaration(InterfaceDeclarationContext ctx) {
		exitLastEntity();
		super.exitInterfaceDeclaration(ctx);
	}

	@Override
	public void enterAnnotationTypeDeclaration(AnnotationTypeDeclarationContext ctx) {
		context.foundNewType(ctx.IDENTIFIER().getText());
		annotationProcessor.processAnnotationModifier(ctx, "classOrInterfaceModifier");
		super.enterAnnotationTypeDeclaration(ctx);
	}

	@Override
	public void exitAnnotationTypeDeclaration(AnnotationTypeDeclarationContext ctx) {
		exitLastEntity();
		super.exitAnnotationTypeDeclaration(ctx);
	}

	/////////////////////////
	// Method
	@Override
	public void enterMethodDeclaration(MethodDeclarationContext ctx) {
		List<String> throwedType = QualitiedNameContextHelper.getNames(ctx.qualifiedNameList());
		String methodName = ctx.IDENTIFIER().getText();
		String returnedType = ClassTypeContextHelper.getClassName(ctx.typeTypeOrVoid());
		FormalParameterListContextHelper helper = new FormalParameterListContextHelper(
				ctx.formalParameters(),context.typeOrFileContainer(),idGenerator);

		context.foundMethodDeclarator(methodName, 
				helper.getParameterList(),
				returnedType,
				throwedType);
		
		annotationProcessor.processAnnotationModifier(ctx, "classOrInterfaceModifier");
		super.enterMethodDeclaration(ctx);
	}

	@Override
	public void exitMethodDeclaration(MethodDeclarationContext ctx) {
		exitLastEntity();
		super.exitMethodDeclaration(ctx);
	}

	private void exitLastEntity() {
		context.exitLastedEntity();
	}

//	interfaceMethodDeclaration
//    : interfaceMethodModifier* (typeTypeOrVoid | typeParameters annotation* typeTypeOrVoid)
//      IDENTIFIER formalParameters ('[' ']')* (THROWS qualifiedNameList)? methodBody

	@Override
	public void enterInterfaceMethodDeclaration(InterfaceMethodDeclarationContext ctx) {
		FormalParameterListContextHelper helper = new FormalParameterListContextHelper(ctx.formalParameters(),context.typeOrFileContainer(),idGenerator);
		List<String> throwedType = QualitiedNameContextHelper.getNames(ctx.qualifiedNameList());

		context.foundMethodDeclarator(ctx.IDENTIFIER().getText(), helper.getParameterList(),
				ClassTypeContextHelper.getClassName(ctx.typeTypeOrVoid()), throwedType);
		if (ctx.typeParameters() != null) {
			foundTypeParametersUse(ctx.typeParameters());
		}
		annotationProcessor.processAnnotationModifier(ctx, "interfaceMethodModifier");
		super.enterInterfaceMethodDeclaration(ctx);
	}

	@Override
	public void exitInterfaceMethodDeclaration(InterfaceMethodDeclarationContext ctx) {
		exitLastEntity();
		super.exitInterfaceMethodDeclaration(ctx);
	}

	@Override
	public void enterConstructorDeclaration(ConstructorDeclarationContext ctx) {
		FormalParameterListContextHelper helper = new FormalParameterListContextHelper(ctx.formalParameters(),context.typeOrFileContainer(),idGenerator);
		List<String> throwedType = QualitiedNameContextHelper.getNames(ctx.qualifiedNameList());
		context.foundMethodDeclarator(ctx.IDENTIFIER().getText(), helper.getParameterList(),ctx.IDENTIFIER().getText(), throwedType);
		annotationProcessor.processAnnotationModifier(ctx, "classBodyDeclaration");
		super.enterConstructorDeclaration(ctx);
	}

	@Override
	public void exitConstructorDeclaration(ConstructorDeclarationContext ctx) {
		exitLastEntity();
		super.exitConstructorDeclaration(ctx);
	}

	/////////////////////////////////////////////////////////
	// Field
	@Override
	public void enterFieldDeclaration(FieldDeclarationContext ctx) {
		 List<String> varNames = VariableDeclaratorsContextHelper.getVariables(ctx.variableDeclarators());
		 String type = ClassTypeContextHelper.getClassName(ctx.typeType());
		context.foundVarDefinition(varNames, type);
		annotationProcessor.processAnnotationModifier(ctx, "classBodyDeclaration");
		super.enterFieldDeclaration(ctx);
	}

	@Override
	public void enterConstDeclaration(ConstDeclarationContext ctx) {
		context.foundVarDefinition(VariableDeclaratorsContextHelper.getVariables(ctx.constantDeclarator()),
				ClassTypeContextHelper.getClassName(ctx.typeType()));
		annotationProcessor.processAnnotationModifier(ctx, "interfaceBodyDeclaration");
		super.enterConstDeclaration(ctx);
	}
	
	@Override
	public void enterEnumConstant(EnumConstantContext ctx) {
		if (ctx.IDENTIFIER()!=null)
		context.foundEnumConstDefinition(ctx.IDENTIFIER().getText());
		super.enterEnumConstant(ctx);
	}

	@Override
	public void enterAnnotationMethodRest(AnnotationMethodRestContext ctx) {
		context.foundMethodDeclarator(ctx.IDENTIFIER().getText(), new ArrayList<>(),ClassTypeContextHelper.getClassName(ctx.typeType()), new ArrayList<>());
		super.enterAnnotationMethodRest(ctx);
	}

	@Override
	public void exitAnnotationMethodRest(AnnotationMethodRestContext ctx) {
		exitLastEntity();
		super.exitAnnotationMethodRest(ctx);
	}

	@Override
	public void enterAnnotationConstantRest(AnnotationConstantRestContext ctx) {
		// TODO: no variable type defined in annotation const？
		context.foundVarDefinition(VariableDeclaratorsContextHelper.getVariables(ctx.variableDeclarators()), "");
		super.enterAnnotationConstantRest(ctx);
	}

	///////////////////////////////////////////
	// variables
	// TODO: all modifier have not processed yet.
	@Override
	public void enterLocalVariableDeclaration(LocalVariableDeclarationContext ctx) {
		context.foundVarDefinition(VariableDeclaratorsContextHelper.getVariables((ctx.variableDeclarators())),
				ClassTypeContextHelper.getClassName(ctx.typeType()));
		super.enterLocalVariableDeclaration(ctx);
	}

	public void enterEnhancedForControl(EnhancedForControlContext ctx) {
		context.foundVarDefinition(VariableDeclaratorsContextHelper.getVariable((ctx.variableDeclaratorId())),
				ClassTypeContextHelper.getClassName(ctx.typeType()));
		super.enterEnhancedForControl(ctx);
	}

//	resource
//    : variableModifier* classOrInterfaceType variableDeclaratorId '=' expression
//    ;
	@Override
	public void enterResource(ResourceContext ctx) {
		context.foundVarDefintion(ctx.variableDeclaratorId().IDENTIFIER().getText(),
				IdentifierContextHelper.getName(ctx.classOrInterfaceType().IDENTIFIER()));
		super.enterResource(ctx);
	}

	@Override
	public void enterExpression(ExpressionContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterExpression(ctx);
	}

	/////////////////////////////////////////////
	// Block
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

	/* type parameters <T> <T1,T2>, <> treat as USE */
	private void foundTypeParametersUse(TypeParametersContext typeParameters) {
		for (int i = 0; i < typeParameters.typeParameter().size(); i++) {
			TypeParameterContext typeParam = typeParameters.typeParameter(i);
			if (typeParam.typeBound() != null) {
				for (int j = 0; j < typeParam.typeBound().typeType().size(); j++) {
					context.foundTypeParametes(ClassTypeContextHelper.getClassName(typeParam.typeBound().typeType(j)));
				}
			}
		}
	}


	

}

