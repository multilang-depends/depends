package depends.extractor.java.context;

import java.util.ArrayList;
import java.util.List;

import depends.javaextractor.JavaParser.QualifiedNameContext;
import depends.javaextractor.JavaParser.QualifiedNameListContext;

public class QualitiedNameContextHelper {
	public static String getName(QualifiedNameContext ctx) {
		String r = "";
		for (int i=0;i<ctx.IDENTIFIER().size();i++) {
			String dot = r.isEmpty()?"":".";
			r = r + dot + ctx.IDENTIFIER(i).getText();
		}
		return r;
	}
	
	public static List<String> getNames(QualifiedNameListContext qualifiedNameList) {
		List<String>  names = new ArrayList<>();
		if (qualifiedNameList == null)
			return names;
		for (QualifiedNameContext item : qualifiedNameList.qualifiedName()) {
			names.add(getName(item));
		}
		return names;
	}
}
