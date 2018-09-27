package depends.extractor.java.context;

import depends.javaextractor.JavaParser.QualifiedNameContext;

public class QualitiedNameContextHelper {
	public static String getName(QualifiedNameContext ctx) {
		String r = "";
		for (int i=0;i<ctx.IDENTIFIER().size();i++) {
			String dot = r.isEmpty()?"":".";
			r = r + dot + ctx.IDENTIFIER(i).getText();
		}
		return r;
	}
}
