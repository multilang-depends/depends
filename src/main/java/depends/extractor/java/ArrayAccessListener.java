package depends.extractor.java;

import java.util.ArrayList;
import java.util.List;

import depends.extractor.java.context.ExpressionNameContextHelper;
import depends.javaextractor.Java9BaseListener;
import depends.javaextractor.Java9Parser.ArrayAccessContext;
import depends.javaextractor.Java9Parser.ArrayAccess_lf_primaryContext;
import depends.javaextractor.Java9Parser.ArrayAccess_lfno_primaryContext;

public class ArrayAccessListener extends Java9BaseListener{

	List<String> varName = new ArrayList<>();
	public List<String> getVarName() {
		return varName;
	}
	@Override
	public void enterArrayAccess(ArrayAccessContext ctx) {
		System.out.println(ctx.expression().size());
		if (varName.size()>0) return; //only visit once
		varName = new ExpressionNameContextHelper().getVarName(ctx.expressionName());
		super.enterArrayAccess(ctx);
	}
	@Override
	public void enterArrayAccess_lf_primary(ArrayAccess_lf_primaryContext ctx) {
		System.out.println("entered ArrayAccess_lf_primaryContext" + ctx.getText());
		super.enterArrayAccess_lf_primary(ctx);
	}
	@Override
	public void enterArrayAccess_lfno_primary(ArrayAccess_lfno_primaryContext ctx) {
		System.out.println(ctx.expression().get(0).getText());
		if (varName.size()>0) return; //only visit once
		varName = new ExpressionNameContextHelper().getVarName(ctx.expressionName());
		super.enterArrayAccess_lfno_primary(ctx);
	}
	
	
	

}
