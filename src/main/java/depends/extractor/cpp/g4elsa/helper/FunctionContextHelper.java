package depends.extractor.cpp.g4elsa.helper;

public class FunctionContextHelper extends ContextHelper{
	public String docComments = "";
	public String functionName = "<not_defined_funcname>";
	public String getFunctionName() {
		return functionName;
	}

	public String getFunctionDocComments() {
		return docComments;
	}
}
