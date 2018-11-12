package depends.extractor.cpp.g4elsa.helper;

import java.util.List;

import depends.javaextractor.CElsaParser.MemberDeclarationGeneralContext;
import depends.javaextractor.CElsaParser.MemberDeclaratorContext;
import depends.javaextractor.CElsaParser.MemberFunctionContext;
import depends.util.Tuple;

public class MemberDeclarationGeneralContextHelper extends  FunctionContextHelper{
	public MemberDeclarationGeneralContextHelper(MemberDeclarationGeneralContext ctx) {
		if(ctx.memberDeclaratorList()==null) return;
		for (MemberDeclaratorContext m:ctx.memberDeclaratorList().memberDeclarator()) {
			if ((m instanceof MemberFunctionContext))  {
				this.functionName = getFunctionName((MemberFunctionContext)m);
				this.parameters = getParameters((MemberFunctionContext)m);
				break;
			}
		}
		this.returnType = getTypeNameOf(ctx.declSpecifier());
	}

	private List<Tuple<String, String>> getParameters(MemberFunctionContext m) {
		return getParameters(m.declarator());
	}

	private String getFunctionName(MemberFunctionContext m) {
		return getName(m.declarator());
	}

}
