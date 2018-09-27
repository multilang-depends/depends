package depends.extractor.java.context;

import java.util.ArrayList;
import java.util.List;

import depends.javaextractor.JavaParser.ConstantDeclaratorContext;
import depends.javaextractor.JavaParser.VariableDeclaratorContext;
import depends.javaextractor.JavaParser.VariableDeclaratorIdContext;
import depends.javaextractor.JavaParser.VariableDeclaratorsContext;

public class VariableDeclaratorsContextHelper {

	public static List<String> getVariables(VariableDeclaratorsContext variableDeclarators) {
		List<String> vars = new ArrayList<>();
		if (variableDeclarators==null) return vars; 
		for (VariableDeclaratorContext vContext:variableDeclarators.variableDeclarator()) {
			vars.add(vContext.variableDeclaratorId().IDENTIFIER().getText());
		}
		return vars;
	}

	public static List<String> getVariables(List<ConstantDeclaratorContext> constantDeclarator) {
		List<String> vars = new ArrayList<>();
		if (constantDeclarator==null) return vars; 
		for (ConstantDeclaratorContext vContext:constantDeclarator) {
			vars.add(vContext.IDENTIFIER().getText());
		}
		return vars;
	}

	public static List<String> getVariable(VariableDeclaratorIdContext variableDeclaratorIdContext) {
		List<String> vars = new ArrayList<>();
		if (variableDeclaratorIdContext==null) return vars; 
		vars.add(variableDeclaratorIdContext.IDENTIFIER().getText());
		return vars;
	}

}
