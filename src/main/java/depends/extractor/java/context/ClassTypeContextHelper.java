package depends.extractor.java.context;

import depends.javaextractor.JavaParser.ClassOrInterfaceTypeContext;
import depends.javaextractor.JavaParser.PrimaryContext;
import depends.javaextractor.JavaParser.TypeTypeContext;
import depends.javaextractor.JavaParser.TypeTypeOrVoidContext;

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
