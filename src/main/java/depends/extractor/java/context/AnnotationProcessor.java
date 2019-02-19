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

import java.lang.reflect.Method;
import java.util.List;

import org.antlr.v4.runtime.RuleContext;

import depends.extractor.HandlerContext;
import depends.extractor.java.JavaParser.AnnotationContext;

public class AnnotationProcessor {
	private HandlerContext context;

	public AnnotationProcessor(HandlerContext context) {
		this.context = context;
	}
	/**
	 * for any elements who with modifiers like 'public/static/... @Annotation‘，
	 * process annotations as "USE"
	 * 
	 * @param ctx
	 * @param class1
	 */
	
	private boolean containsMethod(RuleContext ctx,String methodName) {
		try {
			Method m = ctx.getClass().getMethod(methodName);
			if (m!=null) return true;
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	private Method getMethod(RuleContext ctx, String methodName) {
		try {
			Method m = ctx.getClass().getMethod(methodName);
			if (m!=null) return m;
		} catch (Exception e) {
			return null;
		}
		return null;	
	}
	
	public void processAnnotationModifier(RuleContext ctx, String methodName) {
		while (true) {
			if (ctx == null)
				break;
			if (containsMethod(ctx,methodName))
				break;
			ctx = ctx.parent;
		}
		if (ctx==null)return;
			
		Method m = getMethod(ctx,methodName);
		if (m==null) return;
		try {
			List<?> modifiers = (List<?>) m.invoke(ctx);
			for (Object modifier : modifiers) {
				Method annotationMethod = modifier.getClass().getMethod("annotation");
				AnnotationContext annotation = (AnnotationContext) (annotationMethod.invoke(modifier));
				if (annotation == null)
					return;
				String name = QualitiedNameContextHelper.getName(annotation.qualifiedName());
				context.foundAnnotation(name);
			}
		} catch (Exception e) {
			return;
		}
	}
}
