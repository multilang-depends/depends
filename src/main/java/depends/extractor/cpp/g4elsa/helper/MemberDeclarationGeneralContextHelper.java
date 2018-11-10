package depends.extractor.cpp.g4elsa.helper;

import depends.javaextractor.CElsaParser.MemberDeclarationGeneralContext;
import depends.javaextractor.CElsaParser.MemberDeclaratorContext;
import depends.javaextractor.CElsaParser.MemberFunctionContext;

public class MemberDeclarationGeneralContextHelper extends  FunctionContextHelper{
	public MemberDeclarationGeneralContextHelper(MemberDeclarationGeneralContext ctx) {
		if(ctx.memberDeclaratorList()==null) return;
		for (MemberDeclaratorContext m:ctx.memberDeclaratorList().memberDeclarator()) {
			if ((m instanceof MemberFunctionContext))  {
				this.functionName = getFunctionName((MemberFunctionContext)m);
				break;
			}
		}
	}

	private String getFunctionName(MemberFunctionContext m) {
		return getName(m.declarator());
	}

}
