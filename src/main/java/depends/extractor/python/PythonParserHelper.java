package depends.extractor.python;

import java.util.ArrayList;

import depends.extractor.python.Python3Parser.ArglistContext;
import depends.extractor.python.Python3Parser.DecoratedContext;

public class PythonParserHelper {

	private static PythonParserHelper inst = null;
	public static PythonParserHelper getInst() {
		if (inst==null) inst = new PythonParserHelper();
		return inst;
	}
	public ArrayList<String> getArgList(ArglistContext arglist) {
		ArrayList<String> r = new ArrayList<String>();
		if (arglist==null) return r;
		if (arglist.argument() == null) return r;
		if (arglist.argument().isEmpty()) return r;
		arglist.argument().forEach(arg->r.add(arg.getText()));
		return r;
	}
	public String getDecoratedElementName(DecoratedContext ctx) {
		String name = null;
    	if (ctx.classdef()!=null) {
    		name = ctx.classdef().NAME().getText();
    	}else if (ctx.funcdef()!=null) {
    		name = ctx.funcdef().NAME().getText();
    	}else if (ctx.async_funcdef()!=null) {
    		name = ctx.async_funcdef().funcdef().NAME().getText();
    	}
		return name;
	}


}
