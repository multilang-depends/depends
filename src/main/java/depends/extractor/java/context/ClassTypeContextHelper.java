package depends.extractor.java.context;

import depends.javaextractor.Java9Parser.ClassTypeContext;

public class ClassTypeContextHelper {

	public static String getClassName(ClassTypeContext classType) {
		if (classType.classOrInterfaceType() != null)
			return classType.getText();
		return classType.identifier().getText();
	}
}
