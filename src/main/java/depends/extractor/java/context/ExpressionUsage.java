/*
MIT License

Copyright (c) 2018-2019 Gang ZHANG

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package depends.extractor.java.context;

import org.antlr.v4.runtime.RuleContext;

import depends.entity.Expression;
import depends.entity.repo.IdGenerator;
import depends.extractor.HandlerContext;
import depends.extractor.java.JavaParser.ExpressionContext;
import depends.extractor.java.JavaParser.MethodCallContext;
import depends.extractor.java.JavaParser.PrimaryContext;

public class ExpressionUsage {
	HandlerContext context;
	IdGenerator idGenerator;
	public ExpressionUsage(HandlerContext context,IdGenerator idGenerator) {
		this.context = context;
		this.idGenerator = idGenerator;
	}

	public void foundExpression(ExpressionContext ctx) {
		Expression parent = findParentInStack(ctx);
		/* create expression and link it with parent*/
		Expression expression = new Expression(idGenerator.generateId());
		context.lastContainer().addExpression(ctx,expression);

		expression.text = ctx.getText(); //for debug purpose. no actual effect
		expression.parent = parent;
		if (expression.parent!=null) {
			if (expression.parent.deduceTypeBasedId==null) 
				expression.parent.deduceTypeBasedId = expression.id;
		}
		
		if (ctx.primary()!=null) {
			tryFillExpressionTypeAndIdentifier(ctx.primary(),expression);
			return;
		}
		
		expression.isSet = isSet(ctx);
		expression.isCall = ctx.methodCall()==null?false:true;
		expression.isLogic = isLogic(ctx);
		expression.isDot = isDot(ctx);
		if (ctx.creator()!=null ||ctx.innerCreator()!=null){
			expression.isCreate = true;
		}		
/**
 *    | expression bop='.'
      ( IDENTIFIER
      | methodCall
      | THIS
      | NEW nonWildcardTypeArguments? innerCreator
      | SUPER superSuffix
      | explicitGenericInvocation
      )
 */
		//method call
		if (ctx.methodCall()!=null) {
			expression.identifier = getMethodCallIdentifier(ctx.methodCall());
			expression.isCall = true;
		}
		//new 
		if (ctx.NEW()!=null && ctx.creator()!=null) {
			expression.rawType = CreatorContextHelper.getCreatorType(ctx.creator());
			expression.isCall = true;
			expression.deriveTypeFromChild = false;
		}
		
		if (ctx.typeCast()!=null) {
			expression.isCast=true;
			expression.rawType = ctx.typeCast().typeType().getText();
			expression.deriveTypeFromChild = false;
		}
		
		if (ctx.bop!=null && ctx.bop.getText().equals("instanceof")) {
			expression.isCast=true;
			expression.rawType = ctx.typeType().getText();
			expression.deriveTypeFromChild = false;
		}
		
		if (ctx.creator()!=null) {
			expression.deriveTypeFromChild = false;
		}
		
		if (expression.isDot) {
			if (ctx.IDENTIFIER()!=null)
				expression.identifier = ctx.IDENTIFIER().getText();
			else if (ctx.methodCall()!=null)
				expression.identifier = getMethodCallIdentifier(ctx.methodCall());
			else if (ctx.THIS()!=null)
				expression.identifier = "this";
			else if (ctx.innerCreator()!=null) //innner creator like new List(){}
				expression.identifier =  ctx.innerCreator().IDENTIFIER().getText();
			else if (ctx.SUPER()!=null)
				expression.identifier = "super";
			return;
		}
	}

	private String getMethodCallIdentifier(MethodCallContext methodCall) {
		if (methodCall.THIS()!=null) {
			return "this";
		}else if (methodCall.SUPER()!=null) {
			return "super";
		}else {
			return methodCall.IDENTIFIER().getText();
		}
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
	private void tryFillExpressionTypeAndIdentifier(PrimaryContext ctx, Expression expression) {
		if (ctx.expression()!=null) return; 
		//1. we only handle leaf node. if there is still expression,
		//   the type will be determined by child node in the expression
		if (ctx.literal()!=null) {
		//2. if it is a build-in type like "hello"(string), 10(integer), etc.
			expression.rawType = "<Built-in>";
			expression.identifier = "<Literal>";
		}else if (ctx.IDENTIFIER()!=null) {
		//2. if it is a var name, dertermine the type based on context.
			expression.identifier = ctx.IDENTIFIER().getText();
		}else if (ctx.typeTypeOrVoid()!=null) {
		//3. if given type directly
			expression.rawType = ClassTypeContextHelper.getClassName(ctx.typeTypeOrVoid());
		}else if (ctx.THIS()!=null){
			expression.identifier = "this";
		}else if (ctx.SUPER()!=null){
			expression.identifier = "super";
		}
	}

	private Expression findParentInStack(RuleContext ctx) {
		if (ctx==null) return null;
		if (ctx.parent==null) return null;
		if (context.lastContainer()==null) {
			return null;
		}
		if (context.lastContainer().expressions().containsKey(ctx.parent)) return context.lastContainer().expressions().get(ctx.parent);
		return findParentInStack(ctx.parent);
	}
}
