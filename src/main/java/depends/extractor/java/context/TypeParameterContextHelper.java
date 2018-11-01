package depends.extractor.java.context;

import java.util.ArrayList;
import java.util.List;

import depends.javaextractor.JavaParser.TypeParameterContext;
import depends.javaextractor.JavaParser.TypeParametersContext;

public class TypeParameterContextHelper {

	public static List<String> getTypeParameters(TypeParametersContext typeParameters) {
		ArrayList<String> r = new ArrayList<>();
		for(TypeParameterContext param:typeParameters.typeParameter()) {
			r.add(param.IDENTIFIER().getText());
		}
		return r;
	}

}
