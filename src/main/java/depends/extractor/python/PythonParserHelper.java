package depends.extractor.python;

public class PythonParserHelper {

	private static PythonParserHelper inst = null;
	public static PythonParserHelper getInst() {
		if (inst==null) inst = new PythonParserHelper();
		return inst;
	}

}
