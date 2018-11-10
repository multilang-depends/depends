package depends.extractor.cpp.g4elsa.helper;

import depends.javaextractor.CElsaParser.InitDeclaratorContext;
import depends.javaextractor.CElsaParser.SimpleDeclarationContext;

public class SimpleDeclarationContextHelper extends  FunctionContextHelper{

	private SimpleDeclarationContext ctx;

	public SimpleDeclarationContextHelper(SimpleDeclarationContext ctx) {
		this.ctx = ctx;
		if (ctx.initDeclaratorList()==null) return;
		for ( InitDeclaratorContext initDeclarator:ctx.initDeclaratorList().initDeclarator()) {
			functionName = getName(initDeclarator.declarator());
		}
	}

}
