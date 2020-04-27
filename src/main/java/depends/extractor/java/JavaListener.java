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

package depends.extractor.java;

import java.util.ArrayList;
import java.util.List;

import depends.entity.FunctionEntity;
import depends.entity.GenericName;
import depends.entity.VarEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.java.JavaParser.AnnotationConstantRestContext;
import depends.extractor.java.JavaParser.AnnotationMethodRestContext;
import depends.extractor.java.JavaParser.AnnotationTypeDeclarationContext;
import depends.extractor.java.JavaParser.BlockContext;
import depends.extractor.java.JavaParser.ClassBodyDeclarationContext;
import depends.extractor.java.JavaParser.ClassDeclarationContext;
import depends.extractor.java.JavaParser.ConstDeclarationContext;
import depends.extractor.java.JavaParser.ConstructorDeclarationContext;
import depends.extractor.java.JavaParser.EnhancedForControlContext;
import depends.extractor.java.JavaParser.EnumConstantContext;
import depends.extractor.java.JavaParser.EnumDeclarationContext;
import depends.extractor.java.JavaParser.ExpressionContext;
import depends.extractor.java.JavaParser.FieldDeclarationContext;
import depends.extractor.java.JavaParser.ImportDeclarationContext;
import depends.extractor.java.JavaParser.InterfaceBodyDeclarationContext;
import depends.extractor.java.JavaParser.InterfaceDeclarationContext;
import depends.extractor.java.JavaParser.InterfaceMethodDeclarationContext;
import depends.extractor.java.JavaParser.LocalVariableDeclarationContext;
import depends.extractor.java.JavaParser.MethodDeclarationContext;
import depends.extractor.java.JavaParser.PackageDeclarationContext;
import depends.extractor.java.JavaParser.ResourceContext;
import depends.extractor.java.JavaParser.TypeDeclarationContext;
import depends.extractor.java.JavaParser.TypeParameterContext;
import depends.extractor.java.JavaParser.TypeParametersContext;
import depends.extractor.java.context.AnnotationProcessor;
import depends.extractor.java.context.ClassTypeContextHelper;
import depends.extractor.java.context.ExpressionUsage;
import depends.extractor.java.context.FormalParameterListContextHelper;
import depends.extractor.java.context.IdentifierContextHelper;
import depends.extractor.java.context.QualitiedNameContextHelper;
import depends.extractor.java.context.TypeParameterContextHelper;
import depends.extractor.java.context.VariableDeclaratorsContextHelper;
import depends.importtypes.ExactMatchImport;
import depends.relations.Inferer;

public class JavaListener extends JavaParserBaseListener {
	private JavaHandlerContext context;
	private AnnotationProcessor annotationProcessor;
	private ExpressionUsage expressionUsage;
	private EntityRepo entityRepo;

	public JavaListener(String fileFullPath, EntityRepo entityRepo,Inferer inferer) {
		this.context = new JavaHandlerContext(entityRepo,inferer);
		this.entityRepo = entityRepo;
		annotationProcessor = new AnnotationProcessor();
		expressionUsage = new ExpressionUsage(context,entityRepo);
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
		context.foundNewImport(new ExactMatchImport(ctx.qualifiedName().getText()));
		super.enterImportDeclaration(ctx);
	}

	///////////////////////
	// Class or Interface
	// classDeclaration | enumDeclaration | interfaceDeclaration |
	/////////////////////// annotationTypeDeclaration
	@Override
	public void enterClassDeclaration(ClassDeclarationContext ctx) {
		if (ctx.IDENTIFIER()==null) return;
		context.foundNewType(GenericName.build(ctx.IDENTIFIER().getText()));
		// implements
		if (ctx.typeList() != null) {
			for (int i = 0; i < ctx.typeList().typeType().size(); i++) {
				context.foundImplements(GenericName.build(ClassTypeContextHelper.getClassName(ctx.typeList().typeType().get(i))));
			}
		}
		// extends relation
		if (ctx.typeType() != null) {
			context.foundExtends(GenericName.build(ClassTypeContextHelper.getClassName(ctx.typeType())));
		}

		if (ctx.typeParameters() != null) {
			foundTypeParametersUse(ctx.typeParameters());
		}
		annotationProcessor.processAnnotationModifier(ctx, TypeDeclarationContext.class ,"classOrInterfaceModifier.annotation",context.lastContainer());
		super.enterClassDeclaration(ctx);
	}

	@Override
	public void exitClassDeclaration(ClassDeclarationContext ctx) {
		exitLastEntity();
		super.exitClassDeclaration(ctx);
	}

	@Override
	public void enterEnumDeclaration(EnumDeclarationContext ctx) {
		context.foundNewType(GenericName.build(ctx.IDENTIFIER().getText()));
		annotationProcessor.processAnnotationModifier(ctx, TypeDeclarationContext.class ,"classOrInterfaceModifier.annotation",context.lastContainer());
		super.enterEnumDeclaration(ctx);
	}

	@Override
	public void enterAnnotationTypeDeclaration(AnnotationTypeDeclarationContext ctx) {
		context.foundNewType(GenericName.build(ctx.IDENTIFIER().getText()));
		annotationProcessor.processAnnotationModifier(ctx, TypeDeclarationContext.class ,"classOrInterfaceModifier.annotation",context.lastContainer());
		super.enterAnnotationTypeDeclaration(ctx);
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
		context.foundNewType(GenericName.build(ctx.IDENTIFIER().getText()));
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
		annotationProcessor.processAnnotationModifier(ctx, TypeDeclarationContext.class ,"classOrInterfaceModifier.annotation",context.lastContainer());
		super.enterInterfaceDeclaration(ctx);
	}

	@Override
	public void exitInterfaceDeclaration(InterfaceDeclarationContext ctx) {
		exitLastEntity();
		super.exitInterfaceDeclaration(ctx);
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
		FunctionEntity method = context.foundMethodDeclarator(methodName, returnedType, throwedType);
		new FormalParameterListContextHelper(ctx.formalParameters(), method, entityRepo);
		if (ctx.typeParameters() != null) {
			List<GenericName> parameters = TypeParameterContextHelper.getTypeParameters(ctx.typeParameters());
			method.addTypeParameter(parameters);
		}
		annotationProcessor.processAnnotationModifier(ctx, ClassBodyDeclarationContext.class,"modifier.classOrInterfaceModifier.annotation",context.lastContainer());
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
		List<String> throwedType = QualitiedNameContextHelper.getNames(ctx.qualifiedNameList());
		FunctionEntity method = context.foundMethodDeclarator(ctx.IDENTIFIER().getText(),
				ClassTypeContextHelper.getClassName(ctx.typeTypeOrVoid()), throwedType);
		new FormalParameterListContextHelper(ctx.formalParameters(), method, entityRepo);
		if (ctx.typeParameters() != null) {
			foundTypeParametersUse(ctx.typeParameters());
		}
		annotationProcessor.processAnnotationModifier(ctx, InterfaceBodyDeclarationContext.class,"modifier.classOrInterfaceModifier.annotation",context.lastContainer());
		super.enterInterfaceMethodDeclaration(ctx);
	}

	@Override
	public void exitInterfaceMethodDeclaration(InterfaceMethodDeclarationContext ctx) {
		exitLastEntity();
		super.exitInterfaceMethodDeclaration(ctx);
	}

	@Override
	public void enterConstructorDeclaration(ConstructorDeclarationContext ctx) {
		List<String> throwedType = QualitiedNameContextHelper.getNames(ctx.qualifiedNameList());
		FunctionEntity method = context.foundMethodDeclarator(ctx.IDENTIFIER().getText(), ctx.IDENTIFIER().getText(),
				throwedType);
		new FormalParameterListContextHelper(ctx.formalParameters(), method, entityRepo);
		method.addReturnType(context.currentType());
		annotationProcessor.processAnnotationModifier(ctx, ClassBodyDeclarationContext.class,"modifier.classOrInterfaceModifier.annotation",context.lastContainer());
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
		List<GenericName> typeArguments = ClassTypeContextHelper.getTypeArguments(ctx.typeType());
		List<VarEntity> vars = context.foundVarDefinitions(varNames, type,typeArguments);
		annotationProcessor.processAnnotationModifier(ctx, ClassBodyDeclarationContext.class,"modifier.classOrInterfaceModifier.annotation",vars);
		super.enterFieldDeclaration(ctx);
	}
	


	@Override
	public void enterConstDeclaration(ConstDeclarationContext ctx) {
		List<GenericName> typeArguments = ClassTypeContextHelper.getTypeArguments(ctx.typeType());
		List<VarEntity> vars = context.foundVarDefinitions(VariableDeclaratorsContextHelper.getVariables(ctx.constantDeclarator()),
				ClassTypeContextHelper.getClassName(ctx.typeType()),typeArguments);
		annotationProcessor.processAnnotationModifier(ctx, InterfaceBodyDeclarationContext.class,"modifier.classOrInterfaceModifier.annotation",vars);
		super.enterConstDeclaration(ctx);
	}

	@Override
	public void enterEnumConstant(EnumConstantContext ctx) {
		if (ctx.IDENTIFIER() != null)
			context.foundEnumConstDefinition(ctx.IDENTIFIER().getText());
		super.enterEnumConstant(ctx);
	}

	@Override
	public void enterAnnotationMethodRest(AnnotationMethodRestContext ctx) {
		context.foundMethodDeclarator(ctx.IDENTIFIER().getText(), ClassTypeContextHelper.getClassName(ctx.typeType()),
				new ArrayList<>());
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
		context.foundVarDefinitions(VariableDeclaratorsContextHelper.getVariables(ctx.variableDeclarators()), "",new ArrayList<>());
		super.enterAnnotationConstantRest(ctx);
	}

	///////////////////////////////////////////
	// variables
	// TODO: all modifier have not processed yet.
	@Override
	public void enterLocalVariableDeclaration(LocalVariableDeclarationContext ctx) {
		List<GenericName> typeArguments = ClassTypeContextHelper.getTypeArguments(ctx.typeType());
		context.foundVarDefinitions(VariableDeclaratorsContextHelper.getVariables((ctx.variableDeclarators())),
				ClassTypeContextHelper.getClassName(ctx.typeType()),typeArguments);
		super.enterLocalVariableDeclaration(ctx);
	}

	public void enterEnhancedForControl(EnhancedForControlContext ctx) {
		List<GenericName> typeArguments = ClassTypeContextHelper.getTypeArguments(ctx.typeType());
		context.foundVarDefinitions(VariableDeclaratorsContextHelper.getVariable((ctx.variableDeclaratorId())),
				ClassTypeContextHelper.getClassName(ctx.typeType()),typeArguments);
		super.enterEnhancedForControl(ctx);
	}

//	resource
//    : variableModifier* classOrInterfaceType variableDeclaratorId '=' expression
//    ;
	@Override
	public void enterResource(ResourceContext ctx) {
		List<GenericName> typeArguments = ClassTypeContextHelper.getTypeArguments(ctx.classOrInterfaceType());
		context.foundVarDefinition(ctx.variableDeclaratorId().IDENTIFIER().getText(),
				GenericName.build(IdentifierContextHelper.getName(ctx.classOrInterfaceType().IDENTIFIER())),typeArguments);
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
		// TODO support block in java
		super.enterBlock(ctx);
	}

	@Override
	public void exitBlock(BlockContext ctx) {
		// TODO support block in java
		super.exitBlock(ctx);
	}

	/* type parameters <T> <T1,T2>, <> treat as USE */
	private void foundTypeParametersUse(TypeParametersContext typeParameters) {
		for (int i = 0; i < typeParameters.typeParameter().size(); i++) {
			TypeParameterContext typeParam = typeParameters.typeParameter(i);
			if (typeParam.typeBound() != null) {
				for (int j = 0; j < typeParam.typeBound().typeType().size(); j++) {
					context.foundTypeParametes(GenericName.build(ClassTypeContextHelper.getClassName(typeParam.typeBound().typeType(j))));
				}
			}
			context.currentType().addTypeParameter(GenericName.build(typeParam.IDENTIFIER().getText()));
		}
	}

	public void done() {
		context.done();
	}

}
