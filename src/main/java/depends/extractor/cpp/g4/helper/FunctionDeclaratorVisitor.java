package depends.extractor.cpp.g4.helper;

import java.util.List;

import depends.javaextractor.CPP14BaseVisitor;
import depends.javaextractor.CPP14Parser.NoptrdeclaratorContext;
import depends.javaextractor.CPP14Parser.ParameterdeclarationclauseContext;

public class FunctionDeclaratorVisitor extends CPP14BaseVisitor<Void> {

	private String functionName;
	private List<String> parameterList;

	

	public String getFunctionName() {
		return functionName;
	}
	public List<String> getParameters() {
		return parameterList;
	}

}
