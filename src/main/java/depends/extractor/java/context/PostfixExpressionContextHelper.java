package depends.extractor.java.context;

import depends.javaextractor.Java9Parser.PostfixExpressionContext;

public class PostfixExpressionContextHelper {

	/**
	 * postfixExpression
	 * 	:	(	primary
	 * 		|	expressionName
	 * 		)
	 * 		(	postIncrementExpression_lf_postfixExpression
	 * 		|	postDecrementExpression_lf_postfixExpression
	 * 		)*
	 *   	;
	 */
	public String getVariable(PostfixExpressionContext ctx) {
		if (ctx.expressionName()!=null)
			return ctx.expressionName().getText();
		if (ctx.primary()!=null) {
		}
		return null;
	}

}
