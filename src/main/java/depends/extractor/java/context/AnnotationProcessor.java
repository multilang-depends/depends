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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.RuleContext;
import org.codehaus.plexus.util.StringUtils;

import depends.entity.ContainerEntity;
import depends.entity.GenericName;
import depends.extractor.java.JavaParser.AnnotationContext;

public class AnnotationProcessor {

	public AnnotationProcessor() {
	}

	public void processAnnotationModifier(RuleContext ctx, @SuppressWarnings("rawtypes") Class rootClass,
		String toAnnotationPath,ContainerEntity container) {
		List<ContainerEntity> list  = new ArrayList<>() ;
		list.add(container);
		processAnnotationModifier(ctx, rootClass,
				toAnnotationPath, list);
	}
	
	public void processAnnotationModifier(RuleContext ctx, @SuppressWarnings("rawtypes") Class rootClass,
			String toAnnotationPath, List<?> containers) {

		while (true) {
			if (ctx == null)
				break;
			if (ctx.getClass().equals(rootClass))
				break;
			ctx = ctx.parent;
		}
		if (ctx == null)
			return;


		try {
			Object r = ctx;
			String[] paths = toAnnotationPath.split("\\.");
			for (String path : paths) {
				r = invokeMethod(r, path);
				if (r == null)
					return;
			}
			Collection<AnnotationContext> contexts = new HashSet<>();
			mergeElements(contexts, r);
			for (Object item : contexts) {
				AnnotationContext annotation = (AnnotationContext) item;
				String name = QualitiedNameContextHelper.getName(annotation.qualifiedName());
				containers.stream().forEach(container->((ContainerEntity)container).addAnnotation(GenericName.build(name)));
			}
		} catch (Exception e) {
			return;
		}
	}
	

	private void mergeElements(Collection<AnnotationContext> collection, Object r) {
		if (r instanceof Collection) {
			for (Object item : (Collection<?>) r) {
				mergeElements(collection, item);
			}
		} else {
			if (r instanceof AnnotationContext)
				collection.add((AnnotationContext) r);
		}
	}

	private Object invokeMethod(Object r, String path) {
		if (StringUtils.isEmpty(path))
			return null;
		if (r instanceof Collection) {
			Collection<?> list = (Collection<?>) r;
			return list.stream().map(item -> invokeMethod(item, path)).filter(item -> item != null)
					.collect(Collectors.toSet());
		}
		try {
			Method m = r.getClass().getMethod(path);
			return m.invoke(r);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			return null;
		}
	}




}
