package depends.extractor.cpp.cdt;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import depends.entity.IdGenerator;

public class KnRFunctionTest {
	IdGenerator idGen ;
	class Visitor extends ASTVisitor {
		Visitor(){super(true);}
		@Override
		public int visit(IASTDeclaration declaration) {
			if (declaration instanceof IASTFunctionDefinition) {
				MethodContext method = null;
				method = new MethodContext((IASTFunctionDefinition) declaration, null, idGen);
				assertEquals("knrFunc", method.methodName);
				assertEquals(3, method.parameters.size());
			}
			return super.visit(declaration);
		}

		@Override
		public int visit(IASTDeclarator declaration) {
			if (declaration instanceof IASTFunctionDeclarator) {
				MethodContext2 method = null;
				method = new MethodContext2((IASTFunctionDeclarator) declaration, null, idGen);
				System.out.println("ee");
				assertEquals("knrFunc", method.methodName);
				assertEquals(3, method.parameters.size());
			}
			return super.visit(declaration);
		}

	}

	@Test
	public void test() {
        String src = "./src/test/resources/cpp-code-examples/FunctionTypeKnR.cpp";
        CDTParser cdtParser = new CDTParser();
        IASTTranslationUnit tu = cdtParser.parse(src);
        idGen = mock(IdGenerator.class);
        tu.accept(new Visitor());
    }

}
