package depends.extractor.cpp.cdt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStandardFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.gnu.c.ICASTKnRFunctionDeclarator;
import org.eclipse.cdt.internal.core.model.ASTStringUtil;

import depends.entity.ContainerEntity;
import depends.entity.IdGenerator;
import depends.entity.types.VarEntity;
import depends.javaextractor.CPP14Parser.DeclaratorContext;

public class MethodContext {


	public String methodName ;
	public List<VarEntity> parameters = new ArrayList<>();
	public String returnType;
	public List<String> throwedType = new ArrayList<>();

	public MethodContext(IASTFunctionDefinition function, ContainerEntity containerEntity, IdGenerator idGenerator) {
		final IASTFunctionDeclarator declarator= function.getDeclarator();
		CdtDeclaratorContext declaratorContext = new CdtDeclaratorContext(function.getDeclarator());
		this.methodName = declaratorContext.getName();
        
        final IASTDeclSpecifier declSpecifier= function.getDeclSpecifier();
		final String[] parameterTypes= ASTStringUtil.getParameterSignatureArray(declarator);
		for (String type:parameterTypes){
			this.parameters.add(new VarEntity("", type, containerEntity, idGenerator.generateId()));
		}
		int i=0;
		if (declarator instanceof IASTStandardFunctionDeclarator) {
			final IASTStandardFunctionDeclarator standardFunctionDecl= (IASTStandardFunctionDeclarator) declarator;
			final IASTParameterDeclaration[] parameters= standardFunctionDecl.getParameters();
			for (IASTParameterDeclaration param:parameters) {
				CdtDeclaratorContext paramDeclarator = new CdtDeclaratorContext(param.getDeclarator());
				this.parameters.get(i++).setRawName(paramDeclarator.getName());
			}
		} else if (declarator instanceof ICASTKnRFunctionDeclarator) {
			//TODO:
		}
		this.returnType= ASTStringUtil.getReturnTypeString(declSpecifier, declarator);

	}


}
