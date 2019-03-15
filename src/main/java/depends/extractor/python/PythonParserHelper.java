package depends.extractor.python;

import java.util.ArrayList;

import depends.extractor.python.Python3Parser.ArglistContext;

public class PythonParserHelper {

	private static PythonParserHelper inst = null;
	public static PythonParserHelper getInst() {
		if (inst==null) inst = new PythonParserHelper();
		return inst;
	}
	public ArrayList<String> getBaseClassList(ArglistContext arglist) {
		ArrayList<String> r = new ArrayList<String>();
		if (arglist==null) return r;
		if (arglist.argument() == null) return r;
		if (arglist.argument().isEmpty()) return r;
		arglist.argument().forEach(arg->r.add(arg.getText()));
		return r;
	}

}
