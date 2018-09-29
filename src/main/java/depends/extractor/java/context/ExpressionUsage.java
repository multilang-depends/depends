package depends.extractor.java.context;

import java.util.HashMap;
import java.util.UUID;

import org.antlr.v4.runtime.RuleContext;

import depends.deptypes.DependencyType;
import depends.entity.ContainerEntity;
import depends.entity.Expression;
import depends.entity.repo.EntityRepo;
import depends.extractor.GenericHandler;
import depends.javaextractor.JavaParser.ExpressionContext;
import depends.javaextractor.JavaParser.PrimaryContext;
import depends.util.Tuple;

public class ExpressionUsage {
	GenericHandler handler;
	public ExpressionUsage(GenericHandler handler) {
		this.handler = handler;
	}

	public void foundExpression(ExpressionContext ctx) {
		RuleContext parent = findParentInStack(ctx);
		Expression d = new Expression(ctx.hashCode(),parent==null?null:parent.hashCode());
		handler.context().lastContainer().addExpression(d);
		d.text = ctx.getText(); //for debug purpose. no actual effect
		Tuple<String, String> nodeInfo = getExpressionType(ctx);
		if (nodeInfo!=null) {
			d.returnType = nodeInfo.x;
			d.identifier = nodeInfo.y;
		}
		if (d.identifier==null && ctx.IDENTIFIER()!=null)
			d.identifier = ctx.IDENTIFIER().getText();
		else if (d.identifier==null && ctx.methodCall()!=null) {
			if (ctx.methodCall().THIS()!=null) {
				d.identifier = "this";
			}else if (ctx.methodCall().SUPER()!=null) {
				d.identifier = "super";
			}else {
				d.identifier = ctx.methodCall().IDENTIFIER().getText();
			}
		}
		else if (d.identifier==null && (ctx.NEW()!=null && ctx.creator()!=null)){
			d.identifier = ctx.creator().createdName().IDENTIFIER(0).getText();
		}
		d.isDot = isDot(ctx);
		d.isSet = isSet(ctx);
		d.isCall = ctx.methodCall()==null?false:true;
		handler.context().lastContainer().updateParentReturnType(d.id,d.returnType,handler.context());
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
			return new Tuple<String,String>(ctx.creator().createdName().IDENTIFIER(0).getText(),"");
		}
		return null;
	}
	
	private boolean isDot(ExpressionContext ctx) {
		if (ctx.bop!=null)
			if (ctx.bop.getText().equals(".")) return true;
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
			type = "Built-in";
			varName = ctx.literal().getText();
		}else if (ctx.IDENTIFIER()!=null) {
		//2. if it is a var name, dertermine the type based on context.
			varName = ctx.IDENTIFIER().getText();
			type = handler.context().inferType(varName);
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
		if (handler.context().lastContainer()==null) {
			return null;
		}
		if (handler.context().lastContainer().expressions().containsKey(ctx.parent.hashCode())) return ctx.parent;
		return findParentInStack(ctx.parent);
	}
}
