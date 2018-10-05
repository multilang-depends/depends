package depends.extractor.java.context;

import org.antlr.v4.runtime.RuleContext;

import depends.entity.Expression;
import depends.extractor.HandlerContext;
import depends.javaextractor.JavaParser;
import depends.javaextractor.JavaParser.ExpressionContext;
import depends.javaextractor.JavaParser.PrimaryContext;
import depends.util.Tuple;

public class ExpressionUsage {
	HandlerContext context;
	public ExpressionUsage(HandlerContext context) {
		this.context = context;
	}

	public void foundExpression(ExpressionContext ctx) {
		RuleContext parent = findParentInStack(ctx);

		/* create expression and link it with parent*/
		Expression expression = new Expression(ctx.hashCode(),parent==null?null:parent.hashCode());
		if (parent!=null) {
			Expression parentExpression = context.lastContainer().expressions().get(parent.hashCode());
			if (parentExpression.firstChildId==null) parentExpression.firstChildId = expression.id;
		}
		
		context.lastContainer().addExpression(expression);
		expression.text = ctx.getText(); //for debug purpose. no actual effect
		Tuple<String, String> nodeInfo = getExpressionType(ctx);
		if (nodeInfo!=null) {
			expression.rawType = nodeInfo.x;
			expression.identifier = nodeInfo.y;
		}
		if (expression.identifier==null && ctx.IDENTIFIER()!=null)
			expression.identifier = ctx.IDENTIFIER().getText();
		else if (expression.identifier==null && ctx.methodCall()!=null) {
			if (ctx.methodCall().THIS()!=null) {
				expression.identifier = "this";
			}else if (ctx.methodCall().SUPER()!=null) {
				expression.identifier = "super";
			}else {
				expression.identifier = ctx.methodCall().IDENTIFIER().getText();
			}
		}
		else if (expression.identifier==null && (ctx.NEW()!=null && ctx.creator()!=null)){
			expression.identifier = CreatorContextHelper.getCreatorType(ctx.creator());
		}
		expression.isDot = isDot(ctx);
		expression.isSet = isSet(ctx);
		expression.isCall = ctx.methodCall()==null?false:true;
		expression.isLogic = isLogic(ctx);
		if (ctx.creator()!=null ||
				ctx.methodCall()!=null)
			expression.deriveTypeFromChild = false;
		expression.isCall = true;
		if (ctx.creator()!=null ||
				ctx.innerCreator()!=null)
			expression.isCreate = true;
			
	}



	/**
	 * To determine the return type of the expression, 
	 * @param ctx
	 * @return Tuple.x -> expression type
	 *         Tuple.y -> var name (if has)
	 */
	public Tuple<String, String> getExpressionType(ExpressionContext ctx) {
		Tuple<String, String> primaryInfo = getPrimaryType(ctx.primary());
		if (primaryInfo!=null) return primaryInfo;
		if (ctx.typeType()!=null && ctx.expression()!=null) {
			return new Tuple<String,String>(ctx.typeType().getText(),"");
		}
		if (ctx.NEW()!=null & ctx.creator()!=null) {
			return new Tuple<String,String>(CreatorContextHelper.getCreatorType(ctx.creator()),"");
		}
		return null;
	}
	
	private boolean isDot(ExpressionContext ctx) {
		if (ctx.bop!=null)
			if (ctx.bop.getText().equals(".")) return true;
		return false;
	}
	
	private boolean isLogic(ExpressionContext ctx) {
		if (ctx.bop != null) {
			if (OpHelper.isLogic(ctx.bop.getText())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isSet(ExpressionContext ctx) {
		if (ctx.bop != null) {
			if (OpHelper.isAssigment(ctx.bop.getText())) {
				return true;
			}
		}
		if (ctx.prefix != null) {
			if (OpHelper.isIncrementalDecremental(ctx.prefix.getText())) {
				return true;
			}
		}
		if (ctx.postfix != null) {
			if (OpHelper.isIncrementalDecremental(ctx.postfix.getText())) {
				return true;
			}
		}
		
		return false;
	}
	
//  primary
//    : '(' expression ')'
//    | THIS
//    | SUPER
//    | literal
//    | IDENTIFIER
//    | typeTypeOrVoid '.' CLASS
//    | nonWildcardTypeArguments (explicitGenericInvocationSuffix | THIS arguments) //Just USE relation
//    
	private Tuple<String, String> getPrimaryType(PrimaryContext ctx) {
		String type =null;
		String varName = "";
		if (ctx==null) return null;
		//1. we only handle leaf node. if there is still expression,
		//   the type will be determined by child node in the expression
		if (ctx.expression()!=null) return null; 
		if (ctx.literal()!=null) {
		//2. if it is a build-in type like "hello"(string), 10(integer), etc.
			type = "<Built-in>";
			varName = ctx.literal().getText();
		}else if (ctx.IDENTIFIER()!=null) {
		//2. if it is a var name, dertermine the type based on context.
			varName = ctx.IDENTIFIER().getText();
		}else if (ctx.typeTypeOrVoid()!=null) {
		//3. if given type directly
			type = ClassTypeContextHelper.getClassName(ctx.typeTypeOrVoid());
		}else if (ctx.THIS()!=null){
			varName = "this";
			//TODO: deduce this type
		}else if (ctx.SUPER()!=null){
			varName = "super";
			//TODO: deduce super type
		}else {
			System.out.println("TODO: .g4, line 533: nonWildcardTypeArguments (explicitGenericInvocationSuffix | THIS arguments)");
			System.out.println(ctx.getText());
		}
		return new Tuple<String, String> (type,varName);
	}



	private RuleContext findParentInStack(RuleContext ctx) {
		if (ctx==null) return null;
		if (ctx.parent==null) return null;
		if (context.lastContainer()==null) {
			return null;
		}
		if (context.lastContainer().expressions().containsKey(ctx.parent.hashCode())) return ctx.parent;
		return findParentInStack(ctx.parent);
	}
}
