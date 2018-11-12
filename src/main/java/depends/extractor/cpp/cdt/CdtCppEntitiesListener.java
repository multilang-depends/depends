package depends.extractor.cpp.cdt;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTCastExpression;
import org.eclipse.cdt.core.dom.ast.IASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.IASTEnumerator;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTFieldReference;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTProblem;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTNamespaceDefinition;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTUsingDeclaration;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTUsingDirective;

import depends.entity.IdGenerator;
import depends.entity.repo.EntityRepo;
import depends.util.Tuple;

public class CdtCppEntitiesListener  extends ASTVisitor {

	private CppHandlerContext context;
	private IdGenerator idGenerator;
	private PreprocessorHandler preprocessorHandler;
	private EntityRepo entityRepo;
	public CdtCppEntitiesListener(String fileFullPath, EntityRepo entityRepo, PreprocessorHandler preprocessorHandler) {
		super(true);
		this.context = new CppHandlerContext(entityRepo);
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
	public int visit(IASTProblem problem) {
		System.out.println("warning: parse error" + problem.getOriginalNode() + problem.getMessageWithLocation());
		return super.visit(problem);
	}

	// PACKAGES
	@Override
	public int visit(ICPPASTNamespaceDefinition namespaceDefinition) {
		context.foundNamespace(namespaceDefinition.getName().toString());
		return super.visit(namespaceDefinition);
	}
	
	
	@Override
	public int leave(ICPPASTNamespaceDefinition namespaceDefinition) {
		context.exitLastedEntity();
		return super.leave(namespaceDefinition);
	}

	// Types
	@Override
	public int visit(IASTDeclSpecifier declSpec) {
		if (declSpec instanceof IASTCompositeTypeSpecifier) {
			IASTCompositeTypeSpecifier type = (IASTCompositeTypeSpecifier)declSpec;
			context.foundNewType(type.getName().toString());
		}
		else if (declSpec instanceof  IASTEnumerationSpecifier) {
			IASTEnumerationSpecifier type = (IASTEnumerationSpecifier)declSpec;
			context.foundNewType(type.getName().toString());
		}else {
			//we do not care other types
		}
		return super.visit(declSpec);
	}
	
	@Override
	public int leave(IASTDeclSpecifier declSpec) {
		if (declSpec instanceof IASTCompositeTypeSpecifier) {
			context.exitLastedEntity();
		}
		else if (declSpec instanceof  IASTEnumerationSpecifier) {
			context.exitLastedEntity();
		}else {
			//we do not care other types
		}
		return super.leave(declSpec);
	}
	
	//Function or Methods
	@Override
	public int visit(IASTDeclarator declarator) {
		if (declarator instanceof IASTFunctionDeclarator){
			String returnType = null;
			if ( declarator.getParent() instanceof IASTSimpleDeclaration) {
				IASTSimpleDeclaration decl = (IASTSimpleDeclaration)(declarator.getParent());
				returnType = ASTStringUtil.getName(decl.getDeclSpecifier());
				//TODO: it is just a declaration; should not put it into stack
//				System.out.println("**found IASTFunctionDeclarator -->>" + );
//				System.out.println("**" + declarator.getFileLocation().getStartingLineNumber());
			}
			else if ( declarator.getParent() instanceof IASTFunctionDefinition) {
				IASTFunctionDefinition decl = (IASTFunctionDefinition)declarator.getParent();
				returnType= ASTStringUtil.getReturnTypeString(decl.getDeclSpecifier(), decl.getDeclarator());
				//TODO: should process duplicate names
				context.foundMethodDeclarator(declarator.getName().toString(), returnType, new ArrayList<>());
			}
		}
		return super.visit(declarator);
	}
	
	
	@Override
	public int leave(IASTDeclaration declaration) {
		if ( declaration instanceof IASTFunctionDefinition) {
			context.exitLastedEntity();
		}
		return super.leave(declaration);
	}

	// Variables
	@Override
	public int visit(IASTDeclaration declaration) {
		if (declaration instanceof ICPPASTUsingDeclaration) {
			//TODO: handle using
			System.out.println("**found using declaration -->>" + ((ICPPASTUsingDeclaration)declaration).getName());
			System.out.println("**" + declaration.getFileLocation().getStartingLineNumber());
		}
		else if (declaration instanceof ICPPASTUsingDirective) {
			//TODO: handle using
			System.out.println("**found using directive -->>" + ((ICPPASTUsingDirective)declaration).getQualifiedName());
			System.out.println("**" + declaration.getFileLocation().getStartingLineNumber());
		}
		else if (declaration instanceof IASTSimpleDeclaration ) {
			for (IASTDeclarator declarator:((IASTSimpleDeclaration) declaration).getDeclarators()) {
				if (!(declarator instanceof IASTFunctionDeclarator)) {
					Tuple<String, String> var = new Tuple<String, String>();
					var.x = ASTStringUtil.getName(((IASTSimpleDeclaration) declaration).getDeclSpecifier());
					var.y = declarator.getName().toString();
					context.foundVarDefintion(var.y, var.x);
				}
			}
		}
		return super.visit(declaration);
	}
	
	
	
	
	@Override
	public int visit(IASTEnumerator enumerator) {
		context.foundVarDefintion(enumerator.getName().toString(), context.currentType().getRawName());
		return super.visit(enumerator);
	}
	
	//Expressions
	
	@Override
	public int visit(IASTExpression expression) {
//		if (expression instanceof IASTFunctionCallExpression) {
//			IASTFunctionCallExpression callExpression = (IASTFunctionCallExpression)expression;
//			System.out.println("**call " + callExpression.getFunctionNameExpression().toString());
//		}
//		if (expression instanceof IASTCastExpression) {
//			IASTCastExpression expr = (IASTCastExpression)expression;
//			System.out.println("**cast to " + ASTStringUtil.getName(expr.getTypeId().getDeclSpecifier()));
//		}
//		if (expression instanceof IASTFieldReference) {
//			IASTFieldReference expr = (IASTFieldReference)expression;
//			System.out.println("**access member of  " +expr.getFieldName());
//		}
		return super.visit(expression);
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