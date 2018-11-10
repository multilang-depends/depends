package depends.extractor.cpp.g4elsa.helper;

import java.util.ArrayList;
import java.util.List;

import depends.javaextractor.CElsaParser.DeclaratorContext;
import depends.javaextractor.CElsaParser.DeclaratorIdContext;
import depends.javaextractor.CElsaParser.FunctionDefinitionContext;

public class FunctionDefinitionContextHelper {

	private String docComments = "";
	private String functionName = "<not_defined_funcname>";
	private FunctionDefinitionContext ctx;

	public FunctionDefinitionContextHelper(FunctionDefinitionContext ctx) {
		this.ctx = ctx;
		List<DeclaratorIdContext> declaratorIds = foundDeclaratorIds();
		if (declaratorIds.size()>0){
			DeclaratorIdContext funcNameDeclarator = declaratorIds.get(declaratorIds.size()-1);
			functionName = getName(funcNameDeclarator);
		}
		functionName = ctx.getText(); //TODO
	}

	private String getName(DeclaratorIdContext funcNameDeclarator) {
//		if (funcNameDeclarator.idExpression()!=null) {
//			if (funcNameDeclarator.idExpression().)
//			return funcNameDeclarator.idExpression().getText();
//		}
		return null;
	}

	private List<DeclaratorIdContext> foundDeclaratorIds() {
		DeclaratorContext declarator = ctx.declarator();
		while(true) {
			if (declarator.declaratorId()!=null)
				return declarator.declaratorId();
			declarator = declarator.declarator();
			if (declarator==null) return new ArrayList<>();
		}
	}

	public String getFunctionName() {
		return functionName;
	}

	public String getFunctionDocComments() {
		return docComments;
	}

}
