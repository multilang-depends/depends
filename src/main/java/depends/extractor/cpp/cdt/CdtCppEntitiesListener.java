package depends.extractor.cpp.cdt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorStatement;
import org.eclipse.cdt.core.dom.ast.IASTProblem;
import org.eclipse.cdt.core.dom.ast.IASTStandardFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTTypeId;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.gnu.c.ICASTKnRFunctionDeclarator;
import org.eclipse.cdt.internal.core.model.ASTStringUtil;

import depends.entity.IdGenerator;
import depends.entity.repo.EntityRepo;
import depends.extractor.HandlerContext;

public class CdtCppEntitiesListener  extends ASTVisitor {
	private HandlerContext context;
	private IdGenerator idGenerator;
	private List<String> includeSearchPath = new ArrayList<>();
	private PreprocessorHandler preprocessorHandler = new PreprocessorHandler();
	private String filePath;
	
	public CdtCppEntitiesListener(String fileFullPath, EntityRepo entityRepo, List<String> includeSearchPath) {
		super(true);
		this.context = new HandlerContext(entityRepo);
		idGenerator = entityRepo;
		context.startFile(fileFullPath);
		this.filePath = fileFullPath;
		this.includeSearchPath  = includeSearchPath;
	}
	
	@Override
	public int visit(IASTExpression expression) {
		if (expression instanceof IASTFunctionCallExpression) {
			IASTFunctionCallExpression callExpression = (IASTFunctionCallExpression)expression;
		}
		return super.visit(expression);
	}


	@Override
	public int visit(IASTName name) {
		return super.visit(name);
	}


	@Override
	public int visit(IASTParameterDeclaration parameterDeclaration) {
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
		preprocessorHandler.handlePreprocessors(tu.getAllPreprocessorStatements(),this.filePath);
		for (String incl:preprocessorHandler.getIncludedFullPathNames()) {
			context.foundNewImport(incl,true);
		}
		return super.visit(tu);
	}



	@Override
	public int leave(IASTDeclaration declaration) {
		if (declaration instanceof IASTFunctionDefinition) {
			context.exitLastedEntity();
		}
		return super.leave(declaration);
	}


	@Override
	public int leave(IASTDeclarator declarator) {
		// TODO Auto-generated method stub
		return super.leave(declarator);
	}


	@Override
	public int visit(IASTDeclaration declaration) {
		if (declaration instanceof IASTFunctionDefinition) {
			MethodContext method = new MethodContext((IASTFunctionDefinition)declaration,context.lastContainer(),this.idGenerator);
			context.foundMethodDeclarator(method.methodName, method.parameters, method.returnType, method.throwedType);
		}
		return super.visit(declaration);
	}

	
	@Override
	public int visit(IASTTypeId typeId) {
		// TODO Auto-generated method stub
		return super.visit(typeId);
	}


	@Override
	public int leave(IASTDeclSpecifier declSpec) {
		
		return super.leave(declSpec);
	}


	@Override
	public int visit(IASTDeclSpecifier declSpec) {
		if (declSpec instanceof ICPPASTCompositeTypeSpecifier) {
			ICPPASTCompositeTypeSpecifier etype = (ICPPASTCompositeTypeSpecifier)declSpec;
			context.foundNewType(etype.getName().toString());
		}
		return super.visit(declSpec);
	}


	@Override
	public int visit(IASTDeclarator declarator) {
		// TODO Auto-generated method stub
		return super.visit(declarator);
	}
	
	
	
}