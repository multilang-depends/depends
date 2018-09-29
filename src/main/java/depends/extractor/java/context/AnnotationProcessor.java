package depends.extractor.java.context;

import java.lang.reflect.Method;
import java.util.List;

import org.antlr.v4.runtime.RuleContext;

import depends.extractor.HandlerContext;
import depends.javaextractor.JavaParser.AnnotationContext;

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
			@SuppressWarnings("unchecked")
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
