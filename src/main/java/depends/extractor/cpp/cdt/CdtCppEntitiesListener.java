package depends.extractor.cpp.cdt;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorStatement;
import org.eclipse.cdt.core.dom.ast.IASTProblem;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import depends.entity.IdGenerator;
import depends.entity.repo.EntityRepo;
import depends.extractor.HandlerContext;

public class CdtCppEntitiesListener  extends ASTVisitor {
	private HandlerContext context;
	private IdGenerator idGenerator;
	public CdtCppEntitiesListener(String fileFullPath, EntityRepo entityRepo) {
		super(true);
		this.context = new HandlerContext(entityRepo);
		idGenerator = entityRepo;
		context.startFile(fileFullPath);
	}
	
	
	@Override
	public int visit(IASTExpression expression) {
		System.out.println("expr" + expression);
		if (expression instanceof IASTFunctionCallExpression) {
			IASTFunctionCallExpression callExpression = (IASTFunctionCallExpression)expression;
			System.out.println("call "+callExpression.getFunctionNameExpression());
		}
		return super.visit(expression);
	}


	@Override
	public int visit(IASTName name) {
		System.out.println("name" + name);
		return super.visit(name);
	}


	@Override
	public int visit(IASTParameterDeclaration parameterDeclaration) {
		System.out.println("parameterDeclaration" + parameterDeclaration);
		return super.visit(parameterDeclaration);
	}


	@Override
	public int visit(IASTProblem problem) {
		System.out.println("problem" + problem.getOriginalNode() + problem.getMessageWithLocation());
		return super.visit(problem);
	}


	@Override
	public int visit(IASTTranslationUnit tu) {
		IASTPreprocessorStatement[] i = tu.getAllPreprocessorStatements();
		for (int t=0;t<i.length;t++) {
			if (i[t] instanceof IASTPreprocessorIncludeStatement)
			{
				IASTPreprocessorIncludeStatement incl = (IASTPreprocessorIncludeStatement)(i[t]);
				System.out.println("incl"+ incl.getPath());
			}
		}
		return super.visit(tu);
	}
	
	
//	@Override
//	public void enterClasshead(ClassheadContext ctx) {
//		if (ctx.classheadname().classname().Identifier()!=null)
//			context.foundNewType(ctx.classheadname().classname().Identifier().getText());
//		super.enterClasshead(ctx);
//
//		//TODO: 
//		ctx.classheadname().classname().simpletemplateid();
//	}
//	@Override
//	public void exitClassspecifier(ClassspecifierContext ctx) {
//		context.exitLastedEntity();
//		super.exitClassspecifier(ctx);
//	}
//	@Override
//	public void enterFunctiondefinition(FunctiondefinitionContext ctx) {
//		FunctiondefinitionContextHelper helper = new FunctiondefinitionContextHelper(ctx);
//		context.foundMethodDeclarator(helper.getFunctionName(),
//				helper.getParameters(),helper.getReturnType(), helper.getThrowedType());
//		super.enterFunctiondefinition(ctx);
//	}
//	
//	@Override
//	public void exitFunctiondefinition(FunctiondefinitionContext ctx)  {
//		context.exitLastedEntity();
//		super.exitFunctiondefinition(ctx);
//	}
	
}