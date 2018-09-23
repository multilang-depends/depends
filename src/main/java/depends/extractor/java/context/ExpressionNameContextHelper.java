package depends.extractor.java.context;

import java.util.List;

import depends.javaextractor.Java9Parser.ExpressionNameContext;
import edu.emory.mathcs.backport.java.util.Arrays;

public class ExpressionNameContextHelper {

	@SuppressWarnings("unchecked")
	public List<String> getVarName(ExpressionNameContext expressionName) {
		String name = expressionName.getText();
		String[] path = name.split("\\.");
		return Arrays.asList(path);
	}

}
