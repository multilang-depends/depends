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

import java.util.ArrayList;

import org.codehaus.plexus.util.StringUtils;
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
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier.ICPPASTBaseSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTNamespaceDefinition;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTUsingDeclaration;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTUsingDirective;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLinkageSpecification;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTProblemDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTVisibilityLabel;

import depends.entity.FunctionEntity;
import depends.entity.VarEntity;
import depends.entity.repo.EntityRepo;
import depends.entity.repo.IdGenerator;
import depends.extractor.cpp.CppHandlerContext;
import depends.importtypes.ExactMatchImport;
import depends.importtypes.FileImport;
import depends.importtypes.PackageWildCardImport;
import depends.relations.Inferer;

public class CppVisitor  extends ASTVisitor {
	private CppHandlerContext context;
	private IdGenerator idGenerator;
	private PreprocessorHandler preprocessorHandler;
	private EntityRepo entityRepo;
	Inferer inferer;
	private ExpressionUsage expressionUsage;
	public CppVisitor(String fileFullPath, EntityRepo entityRepo, PreprocessorHandler preprocessorHandler,Inferer inferer) {
		super(true);
		this.context = new CppHandlerContext(entityRepo,inferer);
		idGenerator = entityRepo;
		this.entityRepo = entityRepo;
		this.inferer = inferer;
		context.startFile(fileFullPath);
		this.preprocessorHandler = preprocessorHandler;
		expressionUsage = new ExpressionUsage(context,entityRepo);
	}

	@Override
	public int visit(IASTTranslationUnit tu) {
		for (String incl:preprocessorHandler.getDirectIncludedFiles(tu.getAllPreprocessorStatements())) {
			context.foundNewImport(new FileImport(incl));
			CdtCppFileParser importedParser = new CdtCppFileParser(incl, entityRepo, preprocessorHandler,inferer);
			importedParser.parse(false);
		}
		MacroExtractor macroExtractor = new MacroExtractor(tu.getAllPreprocessorStatements());
		for (String var:macroExtractor.getMacroVars()) {
			context.foundVarDefinition(var,Inferer.buildInType.getRawName(),new ArrayList<>());
		}
		
		for (String var:macroExtractor.getMacroFuncs()) {
			context.foundMethodDeclarator(var, Inferer.buildInType.getRawName(), new ArrayList<>());
			context.exitLastedEntity();
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
		String ns = namespaceDefinition.getName().toString().replace("::", ".");
		context.foundNamespace(ns);
		context.foundNewImport(new PackageWildCardImport(ns));
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
			if (declSpec instanceof ICPPASTCompositeTypeSpecifier) {
				ICPPASTBaseSpecifier[] baseSpecififers = ((ICPPASTCompositeTypeSpecifier)declSpec).getBaseSpecifiers();
				for (ICPPASTBaseSpecifier baseSpecififer:baseSpecififers) {
					String extendName = new String(baseSpecififer.getNameSpecifier().toCharArray());
					context.foundExtends(extendName);
				}
			}
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
				returnType = ASTStringUtilExt.getName(decl.getDeclSpecifier());
				String rawName = declarator.getName().toString();
				FunctionEntity namedEntity = context.currentFile().lookupFunctionInVisibleScope(rawName);
				if (namedEntity!=null) {
					rawName = namedEntity.getQualifiedName();
				}
				returnType = reMapIfConstructDeconstruct(rawName,returnType);
				context.foundMethodDeclarator(rawName, returnType, new ArrayList<>());
			}
			else if ( declarator.getParent() instanceof IASTFunctionDefinition) {
				IASTFunctionDefinition decl = (IASTFunctionDefinition)declarator.getParent();
				returnType= ASTStringUtilExt.getReturnTypeString(decl.getDeclSpecifier(), decl.getDeclarator());
				String rawName = declarator.getName().toString();
				FunctionEntity namedEntity = context.currentFile().lookupFunctionInVisibleScope(rawName);
				if (namedEntity!=null) {
					rawName = namedEntity.getQualifiedName();
				}
				returnType = reMapIfConstructDeconstruct(rawName,returnType);
				context.foundMethodDeclarator(rawName, returnType, new ArrayList<>());
			}
		}
		return super.visit(declarator);
	}
	
	/**
	 * In case of return type is empty, it maybe a construct/deconstruct function
	 * @param functionname
	 * @param returnType
	 * @return
	 */
	private String reMapIfConstructDeconstruct(String functionname, String returnType) {
		if (returnType.length()>0) return returnType;
		if (functionname.contains("::")) {
			return functionname.substring(0, functionname.indexOf("::"));
		}else {
			return functionname;
		}
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
			String ns = ((ICPPASTUsingDeclaration)declaration).getName().toString().replace("::", ".");
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
					context.foundNewTypeAlias(declarator.getName().toString(),ASTStringUtilExt.getName(declSpecifier));
				}else if (!(declarator instanceof IASTFunctionDeclarator)) {
					String varType = ASTStringUtilExt.getName(declSpecifier);
					String varName = declarator.getName().toString();
					if (!StringUtils.isEmpty(varType)) {
						context.foundVarDefinition(varName, varType,new ArrayList<>());
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
		context.foundVarDefinition(enumerator.getName().toString(), context.currentType().getRawName(),new ArrayList<>());
		return super.visit(enumerator);
	}
	
	@Override
	public int visit(IASTExpression expression) {
		expressionUsage.foundExpression(expression);
		return super.visit(expression);
	}

	@Override
	public int visit(IASTParameterDeclaration parameterDeclaration) {
		String parameterName = parameterDeclaration.getDeclarator().getName().toString();
		String parameterType = ASTStringUtilExt.getName(parameterDeclaration.getDeclSpecifier());
		if (context.currentFunction()!=null) {
			VarEntity var = new VarEntity(parameterName,parameterType,context.currentFunction(),idGenerator.generateId());
			context.currentFunction().addParameter(var );
		}else {
			//System.out.println("** parameterDeclaration = " + parameter);
		}
		return super.visit(parameterDeclaration);
	}
}