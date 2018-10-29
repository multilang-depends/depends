package depends.extractor.cpp.cdt;

import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.internal.core.model.ASTStringUtil;

public class CdtDeclaratorContext {

	private IASTDeclarator declarator;

	public CdtDeclaratorContext(IASTDeclarator declarator) {
		this.declarator = declarator;
	}

	public String getName(){
		IASTDeclarator nestedDeclarator = foundDeepestDeclarator();
		final IASTName name= nestedDeclarator.getName();
        if (name == null) {
            return "?";
        }else {
        	return ASTStringUtil.getSimpleName(name);
        }
	}

	private IASTDeclarator foundDeepestDeclarator() {
		IASTDeclarator nestedDeclarator= declarator;
		while (nestedDeclarator.getNestedDeclarator() != null) {
			nestedDeclarator= nestedDeclarator.getNestedDeclarator();
		}
		return nestedDeclarator;
	}


}
