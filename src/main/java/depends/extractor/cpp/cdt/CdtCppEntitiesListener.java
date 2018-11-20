package depends.extractor.cpp.cdt;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.IASTEnumerator;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTProblem;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTNamespaceDefinition;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTUsingDeclaration;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTUsingDirective;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLinkageSpecification;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTProblemDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTVisibilityLabel;

import depends.entity.IdGenerator;
import depends.entity.repo.EntityRepo;
import depends.entity.types.FunctionEntity;
import depends.entity.types.VarEntity;
import depends.util.Tuple;

public class CdtCppEntitiesListener  extends ASTVisitor {

	private CppHandlerContext context;
	private IdGenerator idGenerator;
	private PreprocessorHandler preprocessorHandler;
	private EntityRepo entityRepo;
	private ExpressionUsage expressionUsage;
	public CdtCppEntitiesListener(String fileFullPath, EntityRepo entityRepo, PreprocessorHandler preprocessorHandler) {
		super(true);
		this.context = new CppHandlerContext(entityRepo);
		idGenerator = entityRepo;
		this.entityRepo = entityRepo;
		context.startFile(fileFullPath);
		this.preprocessorHandler = preprocessorHandler;
		expressionUsage = new ExpressionUsage(context);
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
		context.foundNewImport(namespaceDefinition.getName().toString().replace("::", "."),false);
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
				String rawName = declarator.getName().toString();
				FunctionEntity namedEntity = entityRepo.resolveFunctionBindings(context.currentFile(), rawName);
				if (namedEntity!=null) {
					rawName = namedEntity.getQualifiedName();
				}
				context.foundMethodDeclarator(rawName, returnType, new ArrayList<>());
			}
			else if ( declarator.getParent() instanceof IASTFunctionDefinition) {
				IASTFunctionDefinition decl = (IASTFunctionDefinition)declarator.getParent();
				returnType= ASTStringUtil.getReturnTypeString(decl.getDeclSpecifier(), decl.getDeclarator());
				String rawName = declarator.getName().toString();
				FunctionEntity namedEntity = entityRepo.resolveFunctionBindings(context.currentFile(), rawName);
				if (namedEntity!=null) {
					rawName = namedEntity.getQualifiedName();
				}
				context.foundMethodDeclarator(rawName, returnType, new ArrayList<>());
			}
		}
		return super.visit(declarator);
	}
	
	@Override
	public int leave(IASTDeclarator declarator) {
		if (declarator instanceof IASTFunctionDeclarator){
			if ( declarator.getParent() instanceof IASTSimpleDeclaration) {
				String rawName = declarator.getName().toString();
				if (rawName.equals(context.lastContainer().getRawName())) {
					context.exitLastedEntity();
				}else {
					System.err.println("unexpected symbol");
				}
			}
		}
		return super.leave(declarator);
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
			context.foundNewImport(((ICPPASTUsingDeclaration)declaration).getName().toString().replace("::", "."), false);
		}
		else if (declaration instanceof ICPPASTUsingDirective) {
			context.foundNewImport(((ICPPASTUsingDirective)declaration).getQualifiedName().toString().replace("::", "."), false);
		}
		else if (declaration instanceof IASTSimpleDeclaration ) {
			for (IASTDeclarator declarator:((IASTSimpleDeclaration) declaration).getDeclarators()) {
				IASTDeclSpecifier declSpecifier = ((IASTSimpleDeclaration) declaration).getDeclSpecifier();
				//Found new typedef definition
				if (declSpecifier.getStorageClass()==IASTDeclSpecifier.sc_typedef) {
					context.foundNewTypeAlias(declarator.getName().toString(),ASTStringUtil.getName(declSpecifier));
				}else if (!(declarator instanceof IASTFunctionDeclarator)) {
					Tuple<String, String> var = new Tuple<String, String>();
					var.x = ASTStringUtil.getName(declSpecifier);
					var.y = declarator.getName().toString();
					context.foundVarDefintion(var.y, var.x);
				}
			}
		}else if (declaration instanceof IASTFunctionDefinition){
			//handled in declarator
		}else  if (declaration instanceof CPPASTVisibilityLabel){
			//we ignore the visibility in dependency check
		}else if (declaration instanceof CPPASTLinkageSpecification){
			
		}else if (declaration instanceof CPPASTProblemDeclaration){
			System.err.println("parsing error \n" + declaration.getRawSignature());
		}else {
			System.out.println(declaration.getClass().getName());
			System.out.println(declaration.getRawSignature());
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
		expressionUsage.foundExpression(expression);
		return super.visit(expression);
	}

	@Override
	public int visit(IASTParameterDeclaration parameterDeclaration) {
		Tuple<String, String> parameter = new Tuple<String, String>();
		parameter.x = ASTStringUtil.getName(parameterDeclaration.getDeclSpecifier());
		parameter.y = parameterDeclaration.getDeclarator().getName().toString();
		if (context.currentFunction()!=null) {
			VarEntity var = new VarEntity(parameter.y,parameter.x,context.currentFunction(),idGenerator.generateId());
			context.currentFunction().addParameter(var );
		}else {
			System.out.println("** parameterDeclaration = " + parameter);
		}
		return super.visit(parameterDeclaration);
	}
}