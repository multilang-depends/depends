package depends.extractor.ruby;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import depends.extractor.ruby.RubyParser.Class_definitionContext;
import depends.extractor.ruby.RubyParser.Class_headerContext;
import depends.extractor.ruby.RubyParser.CpathContext;
import depends.extractor.ruby.RubyParser.ExprContext;
import depends.extractor.ruby.RubyParser.ExprPrimaryContext;
import depends.extractor.ruby.RubyParser.Function_call_paramContext;
import depends.extractor.ruby.RubyParser.Function_definitionContext;
import depends.extractor.ruby.RubyParser.Function_definition_paramContext;
import depends.extractor.ruby.RubyParser.Function_nameContext;
import depends.extractor.ruby.RubyParser.Function_name_or_symbolContext;
import depends.extractor.ruby.RubyParser.Id_symbolContext;
import depends.extractor.ruby.RubyParser.IdentifierContext;
import depends.extractor.ruby.RubyParser.Module_definitionContext;
import depends.extractor.ruby.RubyParser.PrimaryContext;
import depends.extractor.ruby.RubyParser.PrimarySymbolContext;
import depends.extractor.ruby.RubyParser.PrimaryVarPathContext;
import depends.extractor.ruby.RubyParser.SuperclassContext;
import depends.extractor.ruby.RubyParser.SymbolContext;
import depends.extractor.ruby.RubyParser.Variable_pathContext;

public class RubyParserHelper {

	public String getName(Function_name_or_symbolContext context) {
		if (context.function_name()!=null)
			return getName(context.function_name());
		if (context.symbol()!=null)
			return getName(context.symbol());
		return null;
	}

	public String getName(SymbolContext symbol) {
		return getName(symbol.identifier());
	}

	public String getName(Function_nameContext function_name) {
		if (function_name.cpath()!=null) return getName(function_name.cpath());
		return unifyingCPath(function_name.getText()); //+=.. operators
	}

	public String getName(CpathContext cpath) {
		StringBuilder sb = new StringBuilder();
		for (IdentifierContext id:cpath.identifier()) {
			if (sb.length()>0) sb.append(".");
			sb.append(getName(id));
		}
		return sb.toString();
	}

	/**
	 * Return the actual name. It could include the prefix of scope including 
	 * $(global) @@(class var) @(instance var) , $number/special, and also {} []
	 * @param id
	 * @return
	 */
	public String getName(IdentifierContext id) {
		return unifyingCPath(id.getText());
	}

	public String getName(Module_definitionContext ctx) {
		return getName(ctx.cpath());
	}

	public String getName(Class_definitionContext ctx) {
		if (ctx.class_header().cpath()!=null) return getName(ctx.class_header().cpath());
		return getName(ctx.class_header().identifier());
	}

	public boolean isSingletonClass(Class_definitionContext ctx) {
		if (ctx.class_header().BIT_SHL()!=null) return true;
		return false;
	}

	public String getName(Function_definitionContext ctx) {
		return getName(ctx.function_definition_header().function_name());
	}

	public String getName(Function_definition_paramContext param) {
		if (param.identifier()!=null) return getName(param.identifier());
		return getName(param.hash_asso().expr().get(0));
	}

	private String getName(ExprContext exprContext) {
		if (exprContext instanceof ExprPrimaryContext) {
			ExprPrimaryContext ctx = (ExprPrimaryContext)exprContext;
			return getName(ctx.primary());
		}
		return "";
	}

	public String getName(PrimaryContext primary) {
		if (primary instanceof PrimaryVarPathContext) {
			return getName (((PrimaryVarPathContext)primary).variable_path());
		}
		if (primary instanceof PrimarySymbolContext) {
			return getName (((PrimarySymbolContext)primary).symbol());
		}
		return "";
	}

	public String getName(Variable_pathContext variable_path) {
		return unifyingCPath(variable_path.getText());
	}


	public Collection<String> getAllArgName(List<Function_call_paramContext> func_call_parameters) {
		Collection<String> r = new HashSet<>();
		for (Function_call_paramContext param:func_call_parameters) {
			extractNameFromExpr(r,param.expr());
		}
		return r;
	}

	private void extractNameFromExpr(Collection<String> result, ExprContext exprContext) {
		if (exprContext!=null) {
			if (exprContext instanceof ExprPrimaryContext) {
				ExprPrimaryContext c = (ExprPrimaryContext)(exprContext);
				result.add(extractNameOfPrimary(c.primary()));
			}
			else {
				System.err.println(exprContext.getClass().getSimpleName());
			}
		}
	}

	private String extractNameOfPrimary(PrimaryContext primary) {
		if (primary instanceof PrimaryVarPathContext) {
			PrimaryVarPathContext varPath = (PrimaryVarPathContext)primary;
			return unifyingCPath(varPath.getText().replace("'", "").replace("\"", ""));
		}
		if (primary instanceof PrimarySymbolContext) {
			PrimarySymbolContext symbol = (PrimarySymbolContext)primary;
			return unifyingCPath(symbol.getText().replace(":","").replace("'", "").replace("\"", ""));
		}
		System.err.println(primary.getClass().getSimpleName());
		return null;
	}

	public String getParentType(Class_headerContext class_header) {
		if (class_header==null) return null;
		if (class_header.superclass()==null) return null;
		SuperclassContext superClass = class_header.superclass();
		return unifyingCPath(this.getName(superClass.id_symbol()));
	}

	private String getName(Id_symbolContext id_symbol) {
		return unifyingCPath(id_symbol.getText());
	}

	private String unifyingCPath(String path) {
		return path.replace("::", ".").replace(":",".");
	}
}
