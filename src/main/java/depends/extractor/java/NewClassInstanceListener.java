package depends.extractor.java;

import depends.javaextractor.Java9BaseListener;
import depends.javaextractor.Java9Parser.ClassInstanceCreationExpression_lfno_primaryContext;

public class NewClassInstanceListener extends Java9BaseListener{

	String typeName = "";
	public String getTypeName() {
		return typeName;
	}
	@Override
	public void enterClassInstanceCreationExpression_lfno_primary(
			ClassInstanceCreationExpression_lfno_primaryContext ctx) {
		if (!typeName.isEmpty()) return; //Only visit once
		for (int i=0;i<ctx.identifier().size();i++) {
			String dot = typeName.isEmpty()?"":".";
			typeName += typeName + dot + ctx.identifier(i).getText();
		}
		super.enterClassInstanceCreationExpression_lfno_primary(ctx);
	}

}
