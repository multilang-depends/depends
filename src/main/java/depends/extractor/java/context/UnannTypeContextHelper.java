package depends.extractor.java.context;

import depends.javaextractor.Java9Parser.UnannClassOrInterfaceTypeContext;
import depends.javaextractor.Java9Parser.UnannClassType_lf_unannClassOrInterfaceTypeContext;
import depends.javaextractor.Java9Parser.UnannInterfaceType_lf_unannClassOrInterfaceTypeContext;
import depends.javaextractor.Java9Parser.UnannReferenceTypeContext;
import depends.javaextractor.Java9Parser.UnannTypeContext;

public class UnannTypeContextHelper {
	
	public String calculateType(UnannTypeContext type) {
		if (type == null)
			return null;
		UnannReferenceTypeContext t = type.unannReferenceType();
		if (t != null) {
			if (t.unannArrayType() != null && t.unannArrayType().unannClassOrInterfaceType() != null) {
				return computeType(t.unannArrayType().unannClassOrInterfaceType());
			} else if (t.unannClassOrInterfaceType() != null) {
				return computeType(t.unannClassOrInterfaceType());
			} else if (t.unannTypeVariable() != null) {
				return type.getText(); //TODO: incompleted
			}
		}else if (type.unannPrimitiveType()!=null){
			return type.unannPrimitiveType().getText();
		}
		return null;
	}

	public String computeType(UnannClassOrInterfaceTypeContext c) {
		String name = "";
		if (c.unannClassType_lfno_unannClassOrInterfaceType() != null) {
			name = c.unannClassType_lfno_unannClassOrInterfaceType().identifier().getText();
		}
		if (c.unannInterfaceType_lfno_unannClassOrInterfaceType() != null) {
			name = c.unannInterfaceType_lfno_unannClassOrInterfaceType().unannClassType_lfno_unannClassOrInterfaceType()
					.identifier().getText();
		}
		if (c.unannClassType_lf_unannClassOrInterfaceType() != null) {
			for (UnannClassType_lf_unannClassOrInterfaceTypeContext item : c
					.unannClassType_lf_unannClassOrInterfaceType()) {
				name = name + "." + item.identifier().getText();
			}
		}
		if (c.unannInterfaceType_lf_unannClassOrInterfaceType() != null) {
			for (UnannInterfaceType_lf_unannClassOrInterfaceTypeContext item : c
					.unannInterfaceType_lf_unannClassOrInterfaceType()) {
				name = name + "." + item.unannClassType_lf_unannClassOrInterfaceType().identifier().getText();
			}
		}
		return name;
	}
}
