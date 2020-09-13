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

package depends.extractor.cpp.cdt;

import depends.entity.*;
import depends.entity.repo.EntityRepo;
import depends.entity.repo.IdGenerator;
import depends.extractor.cpp.CppHandlerContext;
import depends.importtypes.ExactMatchImport;
import depends.importtypes.FileImport;
import depends.importtypes.PackageWildCardImport;
import depends.relations.Inferer;
import org.codehaus.plexus.util.StringUtils;
import org.eclipse.cdt.core.dom.ast.*;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.IASTEnumerator;
import org.eclipse.cdt.core.dom.ast.cpp.*;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier.ICPPASTBaseSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CppVisitor  extends ASTVisitor {
	private static final Logger logger = LoggerFactory.getLogger(CppVisitor.class);
	private CppHandlerContext context;
	private IdGenerator idGenerator;
	private PreprocessorHandler preprocessorHandler;
	Inferer inferer;
	private ExpressionUsage expressionUsage;
	HashSet<String> file;
	public CppVisitor(String fileFullPath, EntityRepo entityRepo, PreprocessorHandler preprocessorHandler,Inferer inferer) {
		super(true);
		this.shouldVisitAmbiguousNodes = true;
		this.shouldVisitImplicitNames = true;
		this.includeInactiveNodes = true;
		this.context = new CppHandlerContext(entityRepo,inferer);
		idGenerator = entityRepo;
		this.inferer = inferer;
		this.preprocessorHandler = preprocessorHandler;
		expressionUsage = new ExpressionUsage(context,entityRepo);
		file = new HashSet<>();
		context.startFile(fileFullPath);
		file.add(this.context.currentFile().getQualifiedName());
	}

	@Override
	public int visit(IASTTranslationUnit tu) {
		for (String incl:preprocessorHandler.getDirectIncludedFiles(tu.getAllPreprocessorStatements(),context.currentFile().getQualifiedName())) {
			context.foundNewImport(new FileImport(incl));
		}
		MacroExtractor macroExtractor = new MacroExtractor(tu.getAllPreprocessorStatements(),context.currentFile().getQualifiedName());
		macroExtractor.extract(context);

		for (IASTNode child:tu.getChildren()) {
			if (notLocalFile(child)) continue;
			child.accept(this);
		}
		return ASTVisitor.PROCESS_SKIP;
	}
	
	
	@Override
	public int visit(IASTProblem problem) {
		if (notLocalFile(problem)) return ASTVisitor.PROCESS_SKIP;
		System.out.println("warning: parse error " + problem.getOriginalNode().getRawSignature() + problem.getMessageWithLocation());
		return super.visit(problem);
	}

	private boolean notLocalFile(IASTNode node) {
		if (file.contains(node.getFileLocation().getFileName())) {
			return false;
		}
		return true;
	}

	// PACKAGES
	@Override
	public int visit(ICPPASTNamespaceDefinition namespaceDefinition) {
		if (notLocalFile(namespaceDefinition)) return ASTVisitor.PROCESS_SKIP;
		String ns = namespaceDefinition.getName().toString().replace("::", ".");
		logger.trace("enter ICPPASTNamespaceDefinition  " + ns);
		Entity pkg = context.foundNamespace(ns,namespaceDefinition.getFileLocation().getStartingLineNumber());
		context.foundNewImport(new PackageWildCardImport(ns));
		return super.visit(namespaceDefinition);
	}

	@Override
	public int leave(ICPPASTNamespaceDefinition namespaceDefinition) {
		if (notLocalFile(namespaceDefinition)) return ASTVisitor.PROCESS_SKIP;
		context.exitLastedEntity();
		return super.leave(namespaceDefinition);
	}

	// Types
	@Override
	public int visit(IASTDeclSpecifier declSpec) {
		if (notLocalFile(declSpec)) return ASTVisitor.PROCESS_SKIP;
		logger.trace("enter IASTDeclSpecifier  " + declSpec.getClass().getSimpleName());
		if (declSpec instanceof IASTCompositeTypeSpecifier) {
			IASTCompositeTypeSpecifier type = (IASTCompositeTypeSpecifier)declSpec;
			String name = ASTStringUtilExt.getName(type);
			List<GenericName> param = ASTStringUtilExt.getTemplateParameters(type);
			TypeEntity typeEntity = context.foundNewType(name, type.getFileLocation().getStartingLineNumber());
			if (declSpec instanceof ICPPASTCompositeTypeSpecifier) {
				ICPPASTBaseSpecifier[] baseSpecififers = ((ICPPASTCompositeTypeSpecifier)declSpec).getBaseSpecifiers();
				for (ICPPASTBaseSpecifier baseSpecififer:baseSpecififers) {
					String extendName = ASTStringUtilExt.getName(baseSpecififer.getNameSpecifier());
					context.foundExtends(extendName);
				}
			}
		}
		else if (declSpec instanceof  IASTEnumerationSpecifier) {
			context.foundNewType(ASTStringUtilExt.getName(declSpec), declSpec.getFileLocation().getStartingLineNumber());
		}else {
			//we do not care other types
		}
		return super.visit(declSpec);
	}
	
	@Override
	public int leave(IASTDeclSpecifier declSpec) {
		if (notLocalFile(declSpec)) return ASTVisitor.PROCESS_SKIP;
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
		if (notLocalFile(declarator)) return ASTVisitor.PROCESS_SKIP;
		logger.trace("enter IASTDeclarator  " + declarator.getClass().getSimpleName());
		if (declarator instanceof IASTFunctionDeclarator){
			GenericName returnType = null;
			if ( declarator.getParent() instanceof IASTSimpleDeclaration) {
				IASTSimpleDeclaration decl = (IASTSimpleDeclaration)(declarator.getParent());
				returnType = buildGenericNameFromDeclSpecifier(decl.getDeclSpecifier());
				String rawName = ASTStringUtilExt.getName(declarator);
				List<Entity> namedEntity = context.currentFile().lookupFunctionInVisibleScope(GenericName.build(rawName));
				if (namedEntity!=null) {
					rawName = namedEntity.get(0).getQualifiedName();
				}
				returnType = reMapIfConstructDeconstruct(rawName,returnType);
				context.foundMethodDeclaratorProto(rawName, returnType,decl.getFileLocation().getStartingLineNumber());
			}
			else if ( declarator.getParent() instanceof IASTFunctionDefinition) {
				IASTFunctionDefinition decl = (IASTFunctionDefinition)declarator.getParent();
				returnType = buildGenericNameFromDeclSpecifier(decl.getDeclSpecifier());
				String rawName = ASTStringUtilExt.getName(declarator);
				List<Entity> namedEntity = context.currentFile().lookupFunctionInVisibleScope(GenericName.build(rawName));
				if (namedEntity!=null) {
					rawName = namedEntity.get(0).getQualifiedName();
				}
				returnType = reMapIfConstructDeconstruct(rawName,returnType);
				context.foundMethodDeclaratorImplementation(rawName, returnType,decl.getFileLocation().getStartingLineNumber());
			}
		}
		return super.visit(declarator);
	}
	
	private GenericName buildGenericNameFromDeclSpecifier(IASTDeclSpecifier decl) {
		String name = ASTStringUtilExt.getName(decl);
		List<GenericName> templateParams = ASTStringUtilExt.getTemplateParameters(decl);
		if (name==null)
			return null;
		return new GenericName(name,templateParams);
	}

	/**
	 * In case of return type is empty, it maybe a construct/deconstruct function
	 * @param functionname
	 * @param returnType
	 * @return
	 */
	private GenericName reMapIfConstructDeconstruct(String functionname, GenericName returnType) {
		if (returnType!=null && returnType.uniqName().length()>0)
			return returnType;
		if (functionname.contains("::")) {
			return new GenericName(functionname.substring(0, functionname.indexOf("::")));
		}else {
			return new GenericName(functionname);
		}
	}

	@Override
	public int leave(IASTDeclarator declarator) {
		if (notLocalFile(declarator)) return ASTVisitor.PROCESS_SKIP;
		if (declarator instanceof IASTFunctionDeclarator){
			if ( declarator.getParent() instanceof IASTSimpleDeclaration) {
				String rawName = ASTStringUtilExt.getName(declarator);
				if (rawName.equals(context.lastContainer().getRawName().getName())) {
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
		if (notLocalFile(declaration)) return ASTVisitor.PROCESS_SKIP;
		if ( declaration instanceof IASTFunctionDefinition) {
			context.exitLastedEntity();
		}
		return super.leave(declaration);
	}

	// Variables
	@Override
	public int visit(IASTDeclaration declaration) {
		if (notLocalFile(declaration)) return ASTVisitor.PROCESS_SKIP;
		logger.trace("enter IASTDeclaration  " + declaration.getClass().getSimpleName());
		
		if (declaration instanceof ICPPASTUsingDeclaration) {
			String ns = ASTStringUtilExt.getName((ICPPASTUsingDeclaration)declaration);
			context.foundNewImport(new PackageWildCardImport(ns));
		}
		else if (declaration instanceof ICPPASTUsingDirective) {
			String ns = ((ICPPASTUsingDirective)declaration).getQualifiedName().toString().replace("::", ".");
			context.foundNewImport(new ExactMatchImport(ns));
		}
		else if (declaration instanceof IASTSimpleDeclaration ) {
			for (IASTDeclarator declarator:((IASTSimpleDeclaration) declaration).getDeclarators()) {
				IASTDeclSpecifier declSpecifier = ((IASTSimpleDeclaration) declaration).getDeclSpecifier();
				//Found new typedef definition
				if (declSpecifier.getStorageClass()==IASTDeclSpecifier.sc_typedef) {
					context.foundNewAlias(ASTStringUtilExt.getName(declarator),ASTStringUtilExt.getName(declSpecifier));
				}else if (!(declarator instanceof IASTFunctionDeclarator)) {
					String varType = ASTStringUtilExt.getName(declSpecifier);
					String varName = ASTStringUtilExt.getName(declarator);
					if (!StringUtils.isEmpty(varType)) {
						context.foundVarDefinition(varName, GenericName.build(varType), ASTStringUtilExt.getTemplateParameters(declSpecifier),declarator.getFileLocation().getStartingLineNumber());
					}else {
						expressionUsage.foundCallExpressionOfFunctionStyle(varName,declarator);
					}
					
				}
			}
		}else if (declaration instanceof IASTFunctionDefinition){
			//handled in declarator
		}else  if (declaration instanceof CPPASTVisibilityLabel){
			//we ignore the visibility in dependency check
		}else if (declaration instanceof CPPASTLinkageSpecification){
			
		}else if (declaration instanceof CPPASTTemplateDeclaration){
			
		}else if (declaration instanceof CPPASTProblemDeclaration){
			System.err.println("parsing error \n" + declaration.getRawSignature());
		}else if (declaration instanceof ICPPASTAliasDeclaration){
			IASTName name = ((ICPPASTAliasDeclaration)declaration).getAlias();
			String alias = ASTStringUtilExt.getSimpleName(name).replace("::", ".");
			ICPPASTTypeId mapped = ((ICPPASTAliasDeclaration)declaration).getMappingTypeId();
			String originalName1 = ASTStringUtilExt.getTypeIdString(mapped);
			context.foundNewAlias(alias, originalName1);
		}else if (declaration instanceof CPPASTNamespaceAlias){
			IASTName name = ((CPPASTNamespaceAlias)declaration).getAlias();
			String alias = ASTStringUtilExt.getSimpleName(name).replace("::", ".");
			IASTName mapped = ((CPPASTNamespaceAlias)declaration).getMappingName();
			String originalName = ASTStringUtilExt.getName(mapped);
			context.foundNewAlias(alias, originalName);
		}
		else if(declaration instanceof CPPASTStaticAssertionDeclaration)
		{
			
		}else if (declaration instanceof CPPASTTemplateSpecialization) {
		}
		else{
			System.out.println("not handled type: " + declaration.getClass().getName());
			System.out.println(declaration.getRawSignature());
		}
		return super.visit(declaration);
	}	
	
	@Override
	public int visit(IASTEnumerator enumerator) {
		if (notLocalFile(enumerator)) return ASTVisitor.PROCESS_SKIP;
		logger.trace("enter IASTEnumerator  " + enumerator.getClass().getSimpleName());
		VarEntity var = context.foundVarDefinition(enumerator.getName().toString(), context.currentType().getRawName(), new ArrayList<>(),enumerator.getFileLocation().getStartingLineNumber());
		return super.visit(enumerator);
	}
	
	@Override
	public int visit(IASTExpression expression) {
		if (notLocalFile(expression)) return ASTVisitor.PROCESS_SKIP;
		Expression expr = expressionUsage.foundExpression(expression);
		expr.setLine(expression.getFileLocation().getStartingLineNumber());
		return super.visit(expression);
	}

	@Override
	public int visit(IASTParameterDeclaration parameterDeclaration) {
		if (notLocalFile(parameterDeclaration)) return ASTVisitor.PROCESS_SKIP;

		logger.trace("enter IASTParameterDeclaration  " + parameterDeclaration.getClass().getSimpleName());
		String parameterName = ASTStringUtilExt.getName(parameterDeclaration.getDeclarator());
		String parameterType = ASTStringUtilExt.getName(parameterDeclaration.getDeclSpecifier());
		if (context.currentFunction()!=null) {
			VarEntity var = new VarEntity(GenericName.build(parameterName),GenericName.build(parameterType),context.currentFunction(),idGenerator.generateId());
			context.currentFunction().addParameter(var );
		}else {
			//System.out.println("** parameterDeclaration = " + parameter);
		}
		return super.visit(parameterDeclaration);
	}

	public void done() {
		context.done();
	}
}