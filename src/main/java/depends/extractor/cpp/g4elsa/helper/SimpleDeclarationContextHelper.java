package depends.extractor.cpp.g4elsa.helper;

import depends.javaextractor.CElsaParser.InitDeclaratorContext;
import depends.javaextractor.CElsaParser.SimpleDeclarationContext;
import depends.util.Tuple;

public class SimpleDeclarationContextHelper extends  FunctionContextHelper{

	public SimpleDeclarationContextHelper(SimpleDeclarationContext ctx) {
		if (ctx.initDeclaratorList()==null) return;
		for ( InitDeclaratorContext initDeclarator:ctx.initDeclaratorList().initDeclarator()) {
			functionName = getName(initDeclarator.declarator());
		}
		this.returnType = getTypeNameOf(ctx.declSpecifier());
		String paramType = getTypeNameOf(ctx.declSpecifier());
		Tuple<String, String> r = new Tuple<String, String>("", "");

		if (ctx.initDeclaratorList()!=null) {
			
			for ( InitDeclaratorContext initDeclarator:ctx.initDeclaratorList().initDeclarator()) {
				Tuple<String, String> p = new Tuple<String, String>("", "");
				p.x = paramType;
				p.y = getName(initDeclarator.declarator());
				parameters.add(p);
			}
		}
	}

}
