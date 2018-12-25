package depends.extractor.java.context;

import java.util.ArrayList;
import java.util.List;

import depends.extractor.java.JavaParser.ClassOrInterfaceTypeContext;
import depends.extractor.java.JavaParser.TypeArgumentContext;
import depends.extractor.java.JavaParser.TypeArgumentsContext;
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
	
	public static List<String> getTypeArguments(TypeTypeContext ctx){
		if (ctx.classOrInterfaceType()==null) return new ArrayList<>();
		return getTypeArguments(ctx.classOrInterfaceType());
	}

	public static List<String> getTypeArguments(ClassOrInterfaceTypeContext type) {
		List<String> typeArguments = new ArrayList<>();
		for(TypeArgumentsContext args:type.typeArguments()) {
			for (TypeArgumentContext arg:args.typeArgument()) {
				if (arg.typeType()==null) continue;
				String argumentType = getClassName(arg.typeType());
				if (argumentType!=null)
					typeArguments.add(argumentType);
			}
		}
		return typeArguments;
	}

}
