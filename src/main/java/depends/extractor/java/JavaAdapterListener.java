package depends.extractor.java;

import java.util.ArrayList;
import java.util.Collection;


import depends.entity.repo.EntityRepo;
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
import depends.javaextractor.JavaParser.EnumDeclarationContext;
import depends.javaextractor.JavaParser.ExpressionContext;
import depends.javaextractor.JavaParser.FieldDeclarationContext;
import depends.javaextractor.JavaParser.ImportDeclarationContext;
import depends.javaextractor.JavaParser.InterfaceDeclarationContext;
import depends.javaextractor.JavaParser.InterfaceMethodDeclarationContext;
import depends.javaextractor.JavaParser.LocalVariableDeclarationContext;
import depends.javaextractor.JavaParser.MethodDeclarationContext;
import depends.javaextractor.JavaParser.PackageDeclarationContext;
import depends.javaextractor.JavaParser.QualifiedNameContext;
import depends.javaextractor.JavaParser.QualifiedNameListContext;
import depends.javaextractor.JavaParser.ResourceContext;
import depends.javaextractor.JavaParser.TypeParameterContext;
import depends.javaextractor.JavaParser.TypeParametersContext;
import depends.javaextractor.JavaParserBaseListener;
import depends.util.Tuple;

public class JavaAdapterListener extends JavaParserBaseListener {
	private JavaHandler handler;
	private AnnotationProcessor annotationProcessor;
	private ExpressionUsage expressionUsage;

	public JavaAdapterListener(String fileFullPath, EntityRepo entityRepo) {
		this.handler = new JavaHandler(entityRepo);
		annotationProcessor = new AnnotationProcessor(handler);
		expressionUsage = new ExpressionUsage(handler);

		handler.startFile(fileFullPath);
	}

	////////////////////////
	// Package
	@Override
	public void enterPackageDeclaration(PackageDeclarationContext ctx) {
		handler.foundPackageDeclaration(QualitiedNameContextHelper.getName(ctx.qualifiedName()));
		super.enterPackageDeclaration(ctx);
	}

	////////////////////////
	// Import
	@Override
	public void enterImportDeclaration(ImportDeclarationContext ctx) {
		handler.foundImport(ctx.qualifiedName().getText());
		super.enterImportDeclaration(ctx);
	}

	///////////////////////
	// Class or Interface
	// classDeclaration | enumDeclaration | interfaceDeclaration |
	/////////////////////// annotationTypeDeclaration
	@Override
	public void enterClassDeclaration(ClassDeclarationContext ctx) {
		handler.foundClassOrInterface(ctx.IDENTIFIER().getText());
		// implements
		if (ctx.typeList() != null) {
			for (int i = 0; i < ctx.typeList().typeType().size(); i++) {
				handler.foundImplements(ClassTypeContextHelper.getClassName(ctx.typeList().typeType().get(i)));
			}
		}
		// extends relation
		if (ctx.typeType() != null) {
			handler.foundExtends(ClassTypeContextHelper.getClassName(ctx.typeType()));
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
		handler.foundClassOrInterface(ctx.IDENTIFIER().getText());
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
		handler.foundClassOrInterface(ctx.IDENTIFIER().getText());
		// type parameters
		if (ctx.typeParameters() != null) {
			foundTypeParametersUse(ctx.typeParameters());
		}
		// extends relation
		if (ctx.typeList() != null) {
			for (int i = 0; i < ctx.typeList().typeType().size(); i++) {
				handler.foundExtends(ClassTypeContextHelper.getClassName(ctx.typeList().typeType().get(i)));
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
		handler.foundClassOrInterface(ctx.IDENTIFIER().getText());
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
		FormalParameterListContextHelper helper = new FormalParameterListContextHelper(ctx.formalParameters());
		handler.foundMethodDeclarator(ctx.IDENTIFIER().getText(), helper.getParameterTypeList(),
				ClassTypeContextHelper.getClassName(ctx.typeTypeOrVoid()));
		
		Collection<Tuple<String, String>> varList = helper.getVarList();
		handler.foundVarDefintion(varList, false);
		processThrows(ctx.qualifiedNameList());
		annotationProcessor.processAnnotationModifier(ctx, "classOrInterfaceModifier");
		super.enterMethodDeclaration(ctx);
	}

	@Override
	public void exitMethodDeclaration(MethodDeclarationContext ctx) {
		exitLastEntity();
		super.exitMethodDeclaration(ctx);
	}

	private void exitLastEntity() {
		handler.commitAllExpressionUsage(handler.context().lastContainer());
		handler.exitLastedEntity();
	}

//	interfaceMethodDeclaration
//    : interfaceMethodModifier* (typeTypeOrVoid | typeParameters annotation* typeTypeOrVoid)
//      IDENTIFIER formalParameters ('[' ']')* (THROWS qualifiedNameList)? methodBody

	@Override
	public void enterInterfaceMethodDeclaration(InterfaceMethodDeclarationContext ctx) {
		FormalParameterListContextHelper helper = new FormalParameterListContextHelper(ctx.formalParameters());
		handler.foundMethodDeclarator(ctx.IDENTIFIER().getText(), helper.getParameterTypeList(),
				ClassTypeContextHelper.getClassName(ctx.typeTypeOrVoid()));
		if (ctx.typeParameters() != null) {
			foundTypeParametersUse(ctx.typeParameters());
		}
		Collection<Tuple<String, String>> varList = helper.getVarList();
		handler.foundVarDefintion(varList, false);
		annotationProcessor.processAnnotationModifier(ctx, "interfaceMethodModifier");
		processThrows(ctx.qualifiedNameList());

		super.enterInterfaceMethodDeclaration(ctx);
	}

	@Override
	public void exitInterfaceMethodDeclaration(InterfaceMethodDeclarationContext ctx) {
		exitLastEntity();
		super.exitInterfaceMethodDeclaration(ctx);
	}

	@Override
	public void enterConstructorDeclaration(ConstructorDeclarationContext ctx) {
		FormalParameterListContextHelper helper = new FormalParameterListContextHelper(ctx.formalParameters());
		handler.foundMethodDeclarator(ctx.IDENTIFIER().getText(), helper.getParameterTypeList(),ctx.IDENTIFIER().getText());
		Collection<Tuple<String, String>> varList = helper.getVarList();
		handler.foundVarDefintion(varList, false);
		annotationProcessor.processAnnotationModifier(ctx, "classBodyDeclaration");
		processThrows(ctx.qualifiedNameList());
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
		handler.foundVarDefintion(ClassTypeContextHelper.getClassName(ctx.typeType()),
				VariableDeclaratorsContextHelper.getVariables(ctx.variableDeclarators()));
		annotationProcessor.processAnnotationModifier(ctx, "classBodyDeclaration");
		super.enterFieldDeclaration(ctx);
	}

	@Override
	public void enterConstDeclaration(ConstDeclarationContext ctx) {
		handler.foundVarDefintion(ClassTypeContextHelper.getClassName(ctx.typeType()),
				VariableDeclaratorsContextHelper.getVariables(ctx.constantDeclarator()));
		annotationProcessor.processAnnotationModifier(ctx, "interfaceBodyDeclaration");
		super.enterConstDeclaration(ctx);
	}

	@Override
	public void enterAnnotationMethodRest(AnnotationMethodRestContext ctx) {
		handler.foundMethodDeclarator(ctx.IDENTIFIER().getText(), new ArrayList<>(),"void"); //TODO: what's the return type of annotcation method?
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
		handler.foundVarDefintion("tbc", VariableDeclaratorsContextHelper.getVariables(ctx.variableDeclarators()));
		super.enterAnnotationConstantRest(ctx);
	}

	///////////////////////////////////////////
	// variables
	// TODO: all modifier have not processed yet.
	@Override
	public void enterLocalVariableDeclaration(LocalVariableDeclarationContext ctx) {
		handler.foundVarDefintion(ClassTypeContextHelper.getClassName(ctx.typeType()),
				VariableDeclaratorsContextHelper.getVariables((ctx.variableDeclarators())));
		super.enterLocalVariableDeclaration(ctx);
	}

	public void enterEnhancedForControl(EnhancedForControlContext ctx) {
		handler.foundVarDefintion(ClassTypeContextHelper.getClassName(ctx.typeType()),
				VariableDeclaratorsContextHelper.getVariable((ctx.variableDeclaratorId())));
		super.enterEnhancedForControl(ctx);
	}

//	resource
//    : variableModifier* classOrInterfaceType variableDeclaratorId '=' expression
//    ;
	@Override
	public void enterResource(ResourceContext ctx) {
		handler.foundVarDefintion(IdentifierContextHelper.getName(ctx.classOrInterfaceType().IDENTIFIER()),
				ctx.variableDeclaratorId().IDENTIFIER().getText(), true);
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
					handler.foundTypeParametes(ClassTypeContextHelper.getClassName(typeParam.typeBound().typeType(j)));
				}
			}
		}
	}

	private void processThrows(QualifiedNameListContext qualifiedNameList) {
		if (qualifiedNameList == null)
			return;
		for (QualifiedNameContext item : qualifiedNameList.qualifiedName()) {
			handler.foundThrows(QualitiedNameContextHelper.getName(item));
		}
	}
}

/**
 * unaryExpressionNotPlusMinus : postfixExpression | '~' unaryExpression | '!'
 * unaryExpression | castExpression
 */
