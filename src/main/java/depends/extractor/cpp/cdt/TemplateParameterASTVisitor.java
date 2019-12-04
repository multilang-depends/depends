package depends.extractor.cpp.cdt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTLiteralExpression;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTTypeId;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTTemplateParameter;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTemplateId;

import depends.entity.GenericName;

class TemplateParameterASTVisitor extends ASTVisitor{

	private List<GenericName> parameters;

	public TemplateParameterASTVisitor(List<GenericName> parameters) {
		super(true);
		this.parameters = parameters;
	}
	
	

	@Override
	public int visit(IASTName name) {
		if (name instanceof CPPASTTemplateId) {
			final CPPASTTemplateId templateId = (CPPASTTemplateId) name;
			for (IASTNode argument:templateId.getTemplateArguments()) {
				if (argument instanceof IASTTypeId) {
					IASTDeclSpecifier decl = ((IASTTypeId) argument).getDeclSpecifier();
					String parameterName = ASTStringUtilExt.getName(decl);
					parameterName = parameterName.replace("...", "");
					parameters.add(GenericName.build(parameterName));
				} else if (argument instanceof IASTIdExpression){
					String parameterName = ASTStringUtilExt.getName(((IASTIdExpression)argument).getName());
					parameters.add(GenericName.build(parameterName));
				} else if (argument instanceof IASTLiteralExpression){
					parameters.add(GenericName.build("<Literal>"));
				}else {
					System.err.println ("TODO: unknown template arguments");
				}
			}		}
		return super.visit(name);
	}



	@Override
	public int visit(ICPPASTTemplateParameter templateParameter) {
		return super.visit(templateParameter);
	}

	
}