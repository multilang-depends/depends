/*
MIT License

Copyright (c) 2018-2019 Gang ZHANG

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package depends.extractor.java.context;

import java.util.ArrayList;
import java.util.List;

import depends.entity.GenericName;
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
	
	public static List<GenericName> getTypeArguments(TypeTypeContext ctx){
		if (ctx.classOrInterfaceType()==null) return new ArrayList<>();
		return getTypeArguments(ctx.classOrInterfaceType());
	}

	public static List<GenericName> getTypeArguments(ClassOrInterfaceTypeContext type) {
		List<GenericName> typeArguments = new ArrayList<>();
		for(TypeArgumentsContext args:type.typeArguments()) {
			for (TypeArgumentContext arg:args.typeArgument()) {
				if (arg.typeType()==null) continue;
				String argumentType = getClassName(arg.typeType());
				List<GenericName> subTypes = getTypeArguments(arg.typeType());
				if (argumentType!=null)
					typeArguments.add(GenericName.build(argumentType,subTypes));
			}
		}
		return typeArguments;
	}

}
