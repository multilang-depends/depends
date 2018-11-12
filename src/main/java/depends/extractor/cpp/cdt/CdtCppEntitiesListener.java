package depends.extractor.cpp.cdt;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.IASTEnumerator;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTProblem;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTNamespaceDefinition;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTUsingDeclaration;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTUsingDirective;

import depends.entity.IdGenerator;
import depends.entity.repo.EntityRepo;
import depends.extractor.HandlerContext;
import depends.util.Tuple;

public class CdtCppEntitiesListener  extends ASTVisitor {

	private HandlerContext context;
	private IdGenerator idGenerator;
	private PreprocessorHandler preprocessorHandler;
	private EntityRepo entityRepo;
	public CdtCppEntitiesListener(String fileFullPath, EntityRepo entityRepo, PreprocessorHandler preprocessorHandler) {
		super(true);
		this.context = new HandlerContext(entityRepo);
		idGenerator = entityRepo;
		this.entityRepo = entityRepo;
		context.startFile(fileFullPath);
		this.preprocessorHandler = preprocessorHandler;
	}

	@Override
	public int visit(IASTTranslationUnit tu) {
		for (String incl:preprocessorHandler.getDirectIncludedFiles(tu.getAllPreprocessorStatements())) {
			context.foundNewImport(incl,true);
			CdtCppFileParser importedParser = new CdtCppFileParser(incl, entityRepo, preprocessorHandler);
			importedParser.parse(false);
		}
		return super.visit(tu);
	}
	
	@Override
	public int visit(IASTName name) {
		return super.visit(name);
	}


	@Override
	public int visit(IASTProblem problem) {
		System.out.println("warning: parse error" + problem.getOriginalNode() + problem.getMessageWithLocation());
		return super.visit(problem);
	}

	
	@Override
	public int visit(IASTExpression expression) {
		if (expression instanceof IASTFunctionCallExpression) {
			IASTFunctionCallExpression callExpression = (IASTFunctionCallExpression)expression;
			System.out.println("**call " + callExpression.getFunctionNameExpression().toString());
		}
		return super.visit(expression);
	}
	
	//************************ LEAVE HANDLERS  ***********************************
	
	@Override
	public int visit(ICPPASTNamespaceDefinition namespaceDefinition) {
		System.out.println("**found namespace definition -->>" + namespaceDefinition.getName());
		System.out.println("**" + namespaceDefinition.getFileLocation().getStartingLineNumber());
		return super.visit(namespaceDefinition);
	}
	
	@Override
	public int visit(IASTDeclaration declaration) {
		if (declaration instanceof ICPPASTUsingDeclaration) {
			System.out.println("**found using declaration -->>" + ((ICPPASTUsingDeclaration)declaration).getName());
			System.out.println("**" + declaration.getFileLocation().getStartingLineNumber());
		}
		else if (declaration instanceof ICPPASTUsingDirective) {
			System.out.println("**found using directive -->>" + ((ICPPASTUsingDirective)declaration).getQualifiedName());
			System.out.println("**" + declaration.getFileLocation().getStartingLineNumber());
		}
		else if (declaration instanceof IASTSimpleDeclaration ) {
			for (IASTDeclarator declarator:((IASTSimpleDeclaration) declaration).getDeclarators()) {
				if (!(declarator instanceof IASTFunctionDeclarator)) {
					Tuple<String, String> var = new Tuple<String, String>();
					var.x = ASTStringUtil.getName(((IASTSimpleDeclaration) declaration).getDeclSpecifier());
					var.y = declarator.getName().toString();
					System.out.println("** field = " +var);
				}
			}
		}
		return super.visit(declaration);
	}
	
	
	@Override
	public int visit(IASTDeclSpecifier declSpec) {
		if (declSpec instanceof IASTCompositeTypeSpecifier) {
			IASTCompositeTypeSpecifier type = (IASTCompositeTypeSpecifier)declSpec;
			System.out.println("**found type definition -->>" + type.getName());
			System.out.println("**" + type.getFileLocation().getStartingLineNumber());
		}
		if (declSpec instanceof  IASTEnumerationSpecifier) {
			IASTEnumerationSpecifier type = (IASTEnumerationSpecifier)declSpec;
			System.out.println("**found enum type definition -->>" + type.getName());
			System.out.println("**" + type.getFileLocation().getStartingLineNumber());
		
		}
		return super.visit(declSpec);
	}
	
	@Override
	public int visit(IASTEnumerator enumerator) {
		System.out.println("**found enum value definition -->>" + enumerator.getName());
		System.out.println("**" + enumerator.getFileLocation().getStartingLineNumber());
		return super.visit(enumerator);
	}
	
	@Override
	public int visit(IASTDeclarator declarator) {
		if (declarator instanceof IASTFunctionDeclarator){
			System.out.println("**found IASTFunctionDeclarator -->>" + declarator.getName());
			System.out.println("**" + declarator.getFileLocation().getStartingLineNumber());
			String returnType = null;
			if ( declarator.getParent() instanceof IASTSimpleDeclaration) {
				IASTSimpleDeclaration decl = (IASTSimpleDeclaration)(declarator.getParent());
				returnType = ASTStringUtil.getName(decl.getDeclSpecifier());
			}
			else if ( declarator.getParent() instanceof IASTFunctionDefinition) {
				IASTFunctionDefinition decl = (IASTFunctionDefinition)declarator.getParent();
				returnType= ASTStringUtil.getReturnTypeString(decl.getDeclSpecifier(), decl.getDeclarator());
			}
			System.out.println("** return type = " +returnType);
		}
		
		return super.visit(declarator);
	}


	
	@Override
	public int visit(IASTParameterDeclaration parameterDeclaration) {
		Tuple<String, String> parameter = new Tuple<String, String>();
		parameter.x = ASTStringUtil.getName(parameterDeclaration.getDeclSpecifier());
		parameter.y = parameterDeclaration.getDeclarator().getName().toString();
		System.out.println("** parameterDeclaration = " + parameter);
		return super.visit(parameterDeclaration);
	}
	
}