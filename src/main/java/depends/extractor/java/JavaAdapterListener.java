package depends.extractor.java;

import java.util.Collection;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTreeWalker;

import depends.entity.repo.EntityRepo;
import depends.extractor.java.context.ClassTypeContextHelper;
import depends.extractor.java.context.ExpressionNameContextHelper;
import depends.extractor.java.context.FormalParameterListContextHelper;
import depends.extractor.java.context.PostfixExpressionContextHelper;
import depends.extractor.java.context.UnannTypeContextHelper;
import depends.extractor.java.context.VariableDeclaratorContextHelper;
import depends.javaextractor.Java9BaseListener;
import depends.javaextractor.Java9Parser.AnnotationTypeDeclarationContext;
import depends.javaextractor.Java9Parser.AnnotationTypeElementDeclarationContext;
import depends.javaextractor.Java9Parser.AssignmentContext;
import depends.javaextractor.Java9Parser.BlockContext;
import depends.javaextractor.Java9Parser.ClassDeclarationContext;
import depends.javaextractor.Java9Parser.ClassTypeContext;
import depends.javaextractor.Java9Parser.ConstantDeclarationContext;
import depends.javaextractor.Java9Parser.EnhancedForStatementContext;
import depends.javaextractor.Java9Parser.EnumDeclarationContext;
import depends.javaextractor.Java9Parser.FieldAccessContext;
import depends.javaextractor.Java9Parser.FieldDeclarationContext;
import depends.javaextractor.Java9Parser.InterfaceMethodDeclarationContext;
import depends.javaextractor.Java9Parser.InterfaceTypeContext;
import depends.javaextractor.Java9Parser.LocalVariableDeclarationContext;
import depends.javaextractor.Java9Parser.MethodDeclarationContext;
import depends.javaextractor.Java9Parser.MethodDeclaratorContext;
import depends.javaextractor.Java9Parser.MethodHeaderContext;
import depends.javaextractor.Java9Parser.NormalClassDeclarationContext;
import depends.javaextractor.Java9Parser.NormalInterfaceDeclarationContext;
import depends.javaextractor.Java9Parser.PackageDeclarationContext;
import depends.javaextractor.Java9Parser.PostfixExpressionContext;
import depends.javaextractor.Java9Parser.ResourceContext;
import depends.javaextractor.Java9Parser.ResultContext;
import depends.javaextractor.Java9Parser.SingleStaticImportDeclarationContext;
import depends.javaextractor.Java9Parser.SingleTypeImportDeclarationContext;
import depends.javaextractor.Java9Parser.StaticImportOnDemandDeclarationContext;
import depends.javaextractor.Java9Parser.SuperclassContext;
import depends.javaextractor.Java9Parser.SuperinterfacesContext;
import depends.javaextractor.Java9Parser.TypeImportOnDemandDeclarationContext;
import depends.util.Tuple;

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
	public void enterMethodHeader(MethodHeaderContext ctx) {
		MethodDeclaratorContext declarator = ctx.methodDeclarator();
		FormalParameterListContextHelper helper = new FormalParameterListContextHelper(declarator.formalParameterList());
		handler.foundMethodDeclarator(declarator.identifier().getText(),helper.getParameterTypeList());
		
		ResultContext result = ctx.result();
		handler.foundReturn(new UnannTypeContextHelper().calculateType(result.unannType()));
		super.enterMethodHeader(ctx);
		
		Collection<Tuple<String, String>> varList = helper.getVarList();
		for (Tuple<String, String> var:varList) {
			handler.foundVarDefintion(var.x,var.y,false);
		}
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
		handler.foundVarDefintion(new UnannTypeContextHelper().calculateType(ctx.unannType()),ctx.identifier().getText(),true);
		super.enterAnnotationTypeElementDeclaration(ctx);
	}


	@Override
	public void enterLocalVariableDeclaration(LocalVariableDeclarationContext ctx) {
		handler.foundVarDefintion(new UnannTypeContextHelper().calculateType(ctx.unannType()),new VariableDeclaratorContextHelper().extractVarList(ctx.variableDeclaratorList()));
		super.enterLocalVariableDeclaration(ctx);
	}

	@Override
	public void enterEnhancedForStatement(EnhancedForStatementContext ctx) {
		handler.foundVarDefintion(new UnannTypeContextHelper().calculateType(ctx.unannType()),ctx.variableDeclaratorId().identifier().getText(),true);
		super.enterEnhancedForStatement(ctx);
	}

	@Override
	public void enterResource(ResourceContext ctx) {
		handler.foundVarDefintion(new UnannTypeContextHelper().calculateType(ctx.unannType()),ctx.variableDeclaratorId().identifier().getText(),true);
		super.enterResource(ctx);
	}
	
	/////////////////////////////////////////
	// Assignment or In(De)Cremental
	@Override
	public void enterAssignment(AssignmentContext ctx) {
		if (ctx.leftHandSide().expressionName()!=null) {
			handler.foundVariableSet((new ExpressionNameContextHelper()).getVarName(ctx.leftHandSide().expressionName()));
		}
		if (ctx.leftHandSide().fieldAccess()!=null) {
			System.out.println(ctx.leftHandSide().fieldAccess().getText());
			lookupVarSetInFieldAccessContext(ctx.leftHandSide().fieldAccess());
		}
		if (ctx.leftHandSide().arrayAccess()!=null) {
			List<String> varName = new ExpressionNameContextHelper().getVarName(ctx.leftHandSide().arrayAccess().expressionName());
			handler.foundVariableSet(varName);
		}
		super.enterAssignment(ctx);
	}


	//TODO: should be move
	/**
		fieldAccess
			:	primary '.' identifier
			|	'super' '.' identifier
			|	typeName '.' 'super' '.' identifier
	 */
	private void lookupVarSetInFieldAccessContext(FieldAccessContext ctx) {
		if (ctx.primary()!=null) {
			System.out.println(ctx.primary().getText());
		    ParseTreeWalker walker = new ParseTreeWalker();
		    NewClassInstanceListener newClassInstanceListener = new NewClassInstanceListener();
			walker.walk(newClassInstanceListener, ctx);
			if (!newClassInstanceListener.getTypeName().isEmpty()) {
				String typeNames =newClassInstanceListener.getTypeName();
				String var = ctx.identifier()==null?
					     "":ctx.identifier().getText();
				handler.foundVariableSetOfTypes(typeNames,var);
			}else {
				ArrayAccessListener arrayAccessListener = new ArrayAccessListener();
				walker.walk(arrayAccessListener, ctx);
				if (arrayAccessListener.getVarName().size()>0) {
					System.out.println(arrayAccessListener.getVarName());
					handler.foundVariableSet(arrayAccessListener.getVarName());
				}
			}
		}
		if (ctx.typeName()!=null) {

		}
		if (ctx.getText().indexOf("super")>0) {
			
		}
		
	}
	
	@Override
	public void enterPostfixExpression(PostfixExpressionContext ctx) {
		String varName = new PostfixExpressionContextHelper().getVariable(ctx);
		if (varName !=null)
			handler.foundVariableSet(varName );
		super.enterPostfixExpression(ctx);
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

}


/**
unaryExpressionNotPlusMinus
	:	postfixExpression
	|	'~' unaryExpression
	|	'!' unaryExpression
	|	castExpression
*/
