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
import org.eclipse.cdt.core.dom.ast.IASTProblem;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTTypeId;

import depends.entity.IdGenerator;
import depends.entity.repo.EntityRepo;
import depends.extractor.HandlerContext;

public class CdtCppEntitiesListener  extends ASTVisitor {
	private HandlerContext context;
	private IdGenerator idGenerator;
	private List<String> includeSearchPath = new ArrayList<>();
	private PreprocessorHandler preprocessorHandler ;
	private String filePath;
	private Object fileI;
	private FileIndex fileIndex;
	
	public CdtCppEntitiesListener(String fileFullPath, EntityRepo entityRepo, List<String> includeSearchPath, FileIndex fileIndex) {
		super(true);
		this.context = new HandlerContext(entityRepo);
		idGenerator = entityRepo;
		context.startFile(fileFullPath);
		this.filePath = fileFullPath;
		this.includeSearchPath  = includeSearchPath;
		this.fileIndex = fileIndex;
		preprocessorHandler = new PreprocessorHandler(includeSearchPath,fileIndex);
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
		System.out.println("warning: parse error" + problem.getOriginalNode() + problem.getMessageWithLocation());
		return super.visit(problem);
	}



	@Override
	public int visit(IASTTranslationUnit tu) {
//		IASTPreprocessorStatement[] i = tu.getAllPreprocessorStatements();
//		preprocessorHandler.setIncludePath(this.includeSearchPath);
//		preprocessorHandler.handlePreprocessors(tu.getAllPreprocessorStatements(),this.filePath);
//		for (String incl:preprocessorHandler.getIncludedFullPathNames()) {
//			context.foundNewImport(incl,true);
//		}
		return super.visit(tu);
	}



	@Override
	public int leave(IASTDeclaration declaration) {
//		if (declaration instanceof IASTFunctionDefinition) {
//			context.exitLastedEntity();
//		}
		return super.leave(declaration);
	}


	@Override
	public int leave(IASTDeclarator declarator) {
		// TODO Auto-generated method stub
		return super.leave(declarator);
	}

	@Override
	public int visit(IASTDeclSpecifier declSpec) {
//		if (declSpec instanceof ICPPASTCompositeTypeSpecifier) {
//			ICPPASTCompositeTypeSpecifier etype = (ICPPASTCompositeTypeSpecifier)declSpec;
//			context.foundNewType(etype.getName().toString());
//		}
		return super.visit(declSpec);
	}

	@Override
	public int leave(IASTDeclSpecifier declSpec) {
		
		return super.leave(declSpec);
	}

	
	@Override
	public int visit(IASTDeclaration declaration) {
		if (declaration instanceof IASTFunctionDefinition) {
			IASTFunctionDefinition function = (IASTFunctionDefinition)declaration;
			CdtDeclaratorContext c = new CdtDeclaratorContext(function.getDeclarator());
//			System.out.println(c.getName());
						//MethodContext method = new MethodContext((IASTFunctionDefinition)declaration,context.lastContainer(),this.idGenerator);
//			FunctionEntity funcEntity = context.foundMethodDeclarator(method.methodName,  method.returnType, method.throwedType);
//			method.addParameters(funcEntity);
		}
		return super.visit(declaration);
	}

	@Override
	public int visit(IASTTypeId typeId) {
		// TODO Auto-generated method stub
		return super.visit(typeId);
	}




	


	@Override
	public int visit(IASTDeclarator declarator) {
		CdtDeclaratorContext c = new CdtDeclaratorContext(declarator);
		//System.out.println(c.getName());
		//System.out.println("---");
		if (declarator.getParent() instanceof IASTSimpleDeclaration) {
			
		}
		else if (declarator.getParent() instanceof IASTParameterDeclaration) {
			
		}
		else if (declarator.getParent() instanceof IASTFunctionDefinition) {
			
		}
		else if (declarator.getParent() instanceof IASTFunctionDeclarator){
			
		}else if (declarator.getParent() instanceof IASTTypeId) {
			
		}else {
			System.out.println("**" + declarator.getParent().getClass().getName());
			System.out.println(declarator.getParent().getRawSignature());
		}
		return super.visit(declarator);
	}
	
	
	
}