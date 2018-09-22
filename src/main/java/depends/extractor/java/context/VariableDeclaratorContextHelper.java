package depends.extractor.java.context;

import java.util.ArrayList;
import java.util.List;

import depends.javaextractor.Java9Parser.VariableDeclaratorContext;
import depends.javaextractor.Java9Parser.VariableDeclaratorListContext;

public class VariableDeclaratorContextHelper {

	public List<String> extractVarList(VariableDeclaratorListContext variableDeclaratorList) {
		List<String> vars = new ArrayList<>();
		for (VariableDeclaratorContext vContext:variableDeclaratorList.variableDeclarator()) {
			vars.add(vContext.variableDeclaratorId().identifier().getText());
		}
		return vars;
	}
}
