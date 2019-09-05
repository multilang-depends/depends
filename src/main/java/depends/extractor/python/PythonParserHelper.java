package depends.extractor.python;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import depends.entity.ContainerEntity;
import depends.extractor.python.Python3Parser.ArglistContext;
import depends.extractor.python.Python3Parser.AtomContext;
import depends.extractor.python.Python3Parser.Atom_exprContext;
import depends.extractor.python.Python3Parser.DecoratedContext;
import depends.extractor.python.Python3Parser.Expr_stmtContext;
import depends.extractor.python.Python3Parser.Global_stmtContext;
import depends.extractor.python.Python3Parser.ParametersContext;
import depends.extractor.python.Python3Parser.Testlist_star_exprContext;
import depends.extractor.python.Python3Parser.TfpdefContext;

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
	
	public String getName(Global_stmtContext ctx) {
		return getName(ctx.NAME());
	}
	private String getName(List<TerminalNode> nodes) {
		StringBuilder sb = new StringBuilder();
		for (TerminalNode node:nodes) {
			if (sb.length()>0)
				sb.append(".");
			sb.append(node.getText());
		}
		return sb.toString();
	}
	class NameCollector extends Python3BaseVisitor<Void>{
		private List<String> names;
		NameCollector(List<String> names){
			this.names = names;
		}
		@Override
		public Void visitAtom(AtomContext ctx) {
			if (ctx.NAME()!=null)
				names.add(ctx.NAME().getText());
			return super.visitAtom(ctx);
		}
		
	}
	public List<String> getName(Testlist_star_exprContext testlist_star_expr) {
		List<String> names = new ArrayList<>();
		testlist_star_expr.accept(new NameCollector(names));
		return names;
	}
	public ContainerEntity getScopeOfVar(Expr_stmtContext expr, PythonHandlerContext context) {
		return context.lastContainer();
	}
	
	public String getFirstName(Atom_exprContext atom_expr) {
		List<String> names = new ArrayList<>();
		atom_expr.accept(new NameCollector(names));
		if (names.size()>0) return names.get(0);
		return null;
	}
	
	public String getFullName(Atom_exprContext atom_expr) {
		List<String> names = new ArrayList<>();
		atom_expr.accept(new NameCollector(names));
		if (names.size()>0) {
			StringBuilder sb = new StringBuilder();
			names.forEach(item->{
				if (sb.length()>0)
					sb.append(".");
				sb.append(item);
				});
			return sb.toString();
		}
		return null;
	}
	public List<String> getParameterList(ParametersContext parameters) {
		List<String> r = new ArrayList<String>();
		if (parameters==null) return r;
		if (parameters.typedargslist()==null) return r;
		for (TfpdefContext item:parameters.typedargslist().tfpdef()) {
			r.add(item.NAME().getText());
		}
		return r;
	}
}
