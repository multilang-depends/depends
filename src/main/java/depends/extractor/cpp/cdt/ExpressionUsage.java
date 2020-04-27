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

package depends.extractor.cpp.cdt;
import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTCastExpression;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTLiteralExpression;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTTypeIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTNewExpression;

import depends.entity.Expression;
import depends.entity.GenericName;
import depends.entity.repo.IdGenerator;
import depends.extractor.HandlerContext;

public class ExpressionUsage {
	HandlerContext context;
	IdGenerator idGenerator;
	public ExpressionUsage(HandlerContext context,IdGenerator idGenerator) {
		this.context = context;
		this.idGenerator = idGenerator;
	}

	public void foundCallExpressionOfFunctionStyle(String functionName, IASTDeclarator declarator) {
		/* create expression and link it with parent*/
		Expression expression = new Expression(idGenerator.generateId());
		context.lastContainer().addExpression(declarator,expression);
		expression.setCall(true);
		expression.setIdentifier(functionName);
	}
	
	public void foundExpression(IASTExpression ctx) {
		Expression parent = findParentInStack(ctx);
		//If parent already a call 
		if (parent!=null ) {
			if (ctx instanceof IASTIdExpression && (parent.isCall() || parent.isCast())) {
				return;
			}
		}
		/* create expression and link it with parent*/
		Expression expression = new Expression(idGenerator.generateId());
		expression.setText( ctx.getRawSignature());
		context.lastContainer().addExpression(ctx,expression);
		expression.setParent(parent);
	
		
		if (isTerminalExpression(ctx)) {
			tryFillExpressionTypeAndIdentifier(ctx,expression);
			return;
		}
		
		expression.setSet(isSet(ctx));
		expression.setCall((ctx instanceof IASTFunctionCallExpression)?true:false);
		expression.setLogic(isLogic(ctx));
		if (ctx instanceof ICPPASTNewExpression){
			expression.setCreate(true);;
		}		
		expression.setDot(isDot(ctx));

		/**
 *    | expression bop='.'
      ( IDENTIFIER
      | methodCall
      )
 */

		//method call
		if (ctx instanceof IASTFunctionCallExpression) {
			expression.setIdentifier(getMethodCallIdentifier((IASTFunctionCallExpression)ctx));
			expression.setCall(true);
		}
		if (ctx instanceof ICPPASTNewExpression) {
			expression.setRawType(GenericName.build(ASTStringUtilExt.getTypeIdString(((ICPPASTNewExpression)ctx).getTypeId())));
			expression.setCall(true);
			expression.disableDriveTypeFromChild();
		}

		if (ctx instanceof IASTCastExpression) {
			expression.setCast(true);
			expression.setRawType(GenericName.build(ASTStringUtilExt.getTypeIdString(((IASTCastExpression)ctx).getTypeId())));
			expression.disableDriveTypeFromChild();

		}
		if (expression.isDot()) {
			IASTExpression op2 = ((IASTBinaryExpression)ctx).getOperand2();
			if (op2 instanceof IASTIdExpression)
				expression.setIdentifier(ASTStringUtilExt.getName(((IASTIdExpression)op2).getName()));
			else if (op2 instanceof IASTLiteralExpression)
				expression.setIdentifier(ASTStringUtilExt.getName((IASTLiteralExpression)op2));
			else if (op2 instanceof IASTFunctionCallExpression)
				expression.setIdentifier(getMethodCallIdentifier((IASTFunctionCallExpression)op2));
			return;
		}		
	}

	private boolean isTerminalExpression(IASTExpression ctx) {
		if(ctx instanceof IASTIdExpression) return true;
		if(ctx instanceof IASTLiteralExpression) return true;
		if(ctx instanceof IASTTypeIdExpression) return true;
		//TODO: add others
		return false;
	}
	
	private void tryFillExpressionTypeAndIdentifier(IASTExpression ctx, Expression expression) {
		//1. we only handle leaf node. if there is still expression,
		//   the type will be determined by child node in the expression
		if (ctx instanceof IASTIdExpression){
			expression.setIdentifier(ASTStringUtilExt.getName(((IASTIdExpression)ctx).getName()));
		}else if (ctx instanceof IASTLiteralExpression) {
		//2. if it is a var name, dertermine the type based on context.
			expression.setIdentifier("<Literal>");
			expression.setRawType("<Built-in>");
		}else if (ctx instanceof IASTTypeIdExpression) {
		//3. if given type directly
			expression.setRawType(ASTStringUtilExt.getTypeIdString(((IASTTypeIdExpression)ctx).getTypeId()));
			//TODO: check
		}
	}

	private GenericName getMethodCallIdentifier(IASTFunctionCallExpression methodCall) {
		IASTExpression f = methodCall.getFunctionNameExpression();
		if (f instanceof IASTIdExpression) {
			return GenericName.build(ASTStringUtilExt.getName(((IASTIdExpression)f).getName()));
		}
		return null;
	}

	private boolean isDot(IASTExpression ctx) {
		if (ctx instanceof IASTBinaryExpression) {
			int op = ((IASTBinaryExpression)ctx).getOperator();
			if (op==IASTBinaryExpression.op_pmdot ||
					op==IASTBinaryExpression.op_pmarrow	) return true;
		}
		return false;
	}
	
	private boolean isLogic(IASTExpression ctx) {
		if (ctx instanceof IASTBinaryExpression) {
			 int op = ((IASTBinaryExpression)ctx).getOperator();
			 
			if (op == IASTBinaryExpression.op_equals ||
					op == IASTBinaryExpression.op_notequals ||
					op == IASTBinaryExpression.op_lessThan ||
					op == IASTBinaryExpression.op_lessEqual ||
					op == IASTBinaryExpression.op_greaterThan ||
					op == IASTBinaryExpression.op_greaterEqual ||
					op == IASTBinaryExpression.op_logicalAnd ||
					op == IASTBinaryExpression.op_logicalOr 
					) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isSet(IASTExpression ctx) {
		if (ctx instanceof IASTBinaryExpression) {
			 int op = ((IASTBinaryExpression)ctx).getOperator();
			if (op>=IASTBinaryExpression.op_assign &&
					op<=IASTBinaryExpression.op_binaryOrAssign) {
				return true;
			}
		}
		if (ctx instanceof IASTUnaryExpression) {
			 int op = ((IASTUnaryExpression)ctx).getOperator();
			 if (op == IASTUnaryExpression.op_prefixIncr ||
					 op == IASTUnaryExpression.op_prefixDecr ||
					 op == IASTUnaryExpression.op_postFixIncr ||
					 op == IASTUnaryExpression.op_postFixIncr
					 )
				return true;
			}
		return false;
	}
	

	private Expression findParentInStack(IASTNode ctx) {
		if (ctx==null) return null;
		if (ctx.getParent()==null) return null;
		if (context.lastContainer()==null) {
			return null;
		}
		if (context.lastContainer().expressions().containsKey(ctx.getParent())) return context.lastContainer().expressions().get(ctx.getParent());
		return findParentInStack(ctx.getParent());
	}


}