package depends.extractor.cpp.g4elsa.helper;

import java.util.List;

import depends.javaextractor.CElsaParser.DeclaratorContext;
import depends.javaextractor.CElsaParser.DeclaratorIdContext;
import depends.javaextractor.CElsaParser.FunctionDefinitionContext;
import depends.util.Tuple;

public class FunctionDefinitionContextHelper  extends  FunctionContextHelper{

	private FunctionDefinitionContext ctx;
	
	public FunctionDefinitionContextHelper(FunctionDefinitionContext ctx) {
		this.ctx = ctx;
		List<DeclaratorIdContext> declaratorIds = foundDeclaratorIds(ctx.declarator());
		if (declaratorIds.size()>0){
			DeclaratorIdContext funcNameDeclarator = declaratorIds.get(declaratorIds.size()-1);
			functionName = getName(funcNameDeclarator);
		}
		this.returnType = getTypeNameOf(ctx.declSpecifier());
		this.parameters = getParameters(ctx.declarator());
	}


}
