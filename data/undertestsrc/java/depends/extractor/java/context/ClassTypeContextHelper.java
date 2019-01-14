package depends.extractor.java.context;

import depends.extractor.java.JavaParser.ClassOrInterfaceTypeContext;
import depends.extractor.java.JavaParser.TypeTypeContext;
import depends.extractor.java.JavaParser.TypeTypeOrVoidContext;

public class ClassTypeContextHelper {

	/**
		typeType
		    : annotation? (classOrInterfaceType | primitiveType) ('[' ']')*
		    ;
		classOrInterfaceType
		    : IDENTIFIER typeArguments? ('.' IDENTIFIER typeArguments?)*
		    ;
     */
	public static String getClassName(TypeTypeContext ctx) {
		if (ctx.primitiveType()!=null)
			return ctx.primitiveType().getText();
		if (ctx.classOrInterfaceType()!=null)
			return getType(ctx.classOrInterfaceType());
		return null;
	}

	private static String getType(ClassOrInterfaceTypeContext ctx) {
		String r = "";
		for (int i=0;i<ctx.IDENTIFIER().size();i++) {
			String dot = r.isEmpty()?"":".";
			r = r + dot + ctx.IDENTIFIER(i).getText();
		}
		return r;
	}

	public static String getClassName(TypeTypeOrVoidContext typeTypeOrVoid) {
		if (typeTypeOrVoid.typeType()!=null) return getClassName(typeTypeOrVoid.typeType());
		return "void";
	}

}
