package depends.extractor.cpp.cdt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStandardFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.gnu.c.ICASTKnRFunctionDeclarator;
import org.eclipse.cdt.internal.core.model.ASTStringUtil;

import depends.entity.ContainerEntity;
import depends.entity.IdGenerator;
import depends.entity.types.FunctionEntity;
import depends.entity.types.VarEntity;

public class MethodContext {
	public String methodName ;
	public List<VarEntity> parameters = new ArrayList<>();
	public String returnType;
	public List<String> throwedType = new ArrayList<>();
	private IASTFunctionDefinition function;
	private IdGenerator idGenerator;

	public MethodContext(IASTFunctionDefinition function, ContainerEntity containerEntity, IdGenerator idGenerator) {
		this.function = function;
		this.idGenerator = idGenerator;
		final IASTFunctionDeclarator declarator= function.getDeclarator();
		CdtDeclaratorContext declaratorContext = new CdtDeclaratorContext(function.getDeclarator());
		this.methodName = declaratorContext.getName();
        
        final IASTDeclSpecifier declSpecifier= function.getDeclSpecifier();
		final String[] parameterTypes= ASTStringUtil.getParameterSignatureArray(declarator);
		for (String type:parameterTypes){
			this.parameters.add(new VarEntity("", type, containerEntity, idGenerator.generateId()));
		}
		this.returnType= ASTStringUtil.getReturnTypeString(declSpecifier, declarator);

	}

	public void addParameters(FunctionEntity containerEntity) {
		final IASTFunctionDeclarator declarator= function.getDeclarator();
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
			ICASTKnRFunctionDeclarator knRDeclarator = (ICASTKnRFunctionDeclarator)declarator;
			for (i=0;i<knRDeclarator.getParameterNames().length;i++) {
				this.parameters.get(i++).setRawName(knRDeclarator.getParameterNames()[i].getRawSignature());
			}
		}		
	}


}
