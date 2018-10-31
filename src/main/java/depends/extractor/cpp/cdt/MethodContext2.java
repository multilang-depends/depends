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
import depends.entity.types.VarEntity;

public class MethodContext2 {
	public String methodName ;
	public List<VarEntity> parameters = new ArrayList<>();
	public String returnType;
	public List<String> throwedType = new ArrayList<>();

	public MethodContext2(IASTFunctionDeclarator declarator, ContainerEntity containerEntity, IdGenerator idGenerator) {
		CdtDeclaratorContext declaratorContext = new CdtDeclaratorContext(declarator);
		this.methodName = declaratorContext.getName();
        
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
		this.returnType= "not complete";//ASTStringUtil.getReturnTypeString(declSpecifier, declarator);

	}


}
