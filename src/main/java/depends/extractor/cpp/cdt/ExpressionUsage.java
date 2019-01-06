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
		Expression expression = new Expression(idGenerator.generateId(),null);
		context.lastContainer().addExpression(declarator,expression);
		expression.isCall = true;
		expression.identifier = functionName;
	}
	
	public void foundExpression(IASTExpression ctx) {
		Expression parent = findParentInStack(ctx);
		/* create expression and link it with parent*/
		Expression expression = new Expression(idGenerator.generateId(),parent==null?null:parent.id);
		expression.text = ctx.getRawSignature(); //for debug purpose. no actual effect
		context.lastContainer().addExpression(ctx,expression);

		if (parent!=null) {
			if (parent.deduceTypeBasedId==null) parent.deduceTypeBasedId = expression.id;
			expression.parent = parent;
		}
		
		
		if (isTerminalExpression(ctx)) {
			tryFillExpressionTypeAndIdentifier(ctx,expression);
			return;
		}
		
		expression.isSet = isSet(ctx);
		expression.isCall = (ctx instanceof IASTFunctionCallExpression)?true:false;
		expression.isLogic = isLogic(ctx);
		if (ctx instanceof ICPPASTNewExpression){
			expression.isCreate = true;
		}		
		expression.isDot = isDot(ctx);

		/**
 *    | expression bop='.'
      ( IDENTIFIER
      | methodCall
      )
 */

		//method call
		if (ctx instanceof IASTFunctionCallExpression) {
			expression.identifier = getMethodCallIdentifier((IASTFunctionCallExpression)ctx);
			expression.isCall = true;
		}
		if (ctx instanceof ICPPASTNewExpression) {
			expression.rawType = ASTStringUtil.getTypeIdString(((ICPPASTNewExpression)ctx).getTypeId());
			expression.isCall = true;
			expression.deriveTypeFromChild = false;
		}

		if (ctx instanceof IASTCastExpression) {
			expression.isCast=true;
			expression.rawType = ASTStringUtil.getTypeIdString(((IASTCastExpression)ctx).getTypeId());
			expression.deriveTypeFromChild = false;

		}
		if (expression.isDot) {
			IASTExpression op2 = ((IASTBinaryExpression)ctx).getOperand2();
			if (op2 instanceof IASTIdExpression)
				expression.identifier = ((IASTIdExpression)op2).getName().toString();
			else if (op2 instanceof IASTLiteralExpression)
				expression.identifier = ((IASTLiteralExpression)op2).getRawSignature();
			else if (op2 instanceof IASTFunctionCallExpression)
				expression.identifier = getMethodCallIdentifier((IASTFunctionCallExpression)op2);
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
			expression.identifier = ((IASTIdExpression) ctx).getName().toString();
		}else if (ctx instanceof IASTLiteralExpression) {
		//2. if it is a var name, dertermine the type based on context.
			expression.identifier = "<Literal>";
			expression.rawType =  "<Built-in>";
		}else if (ctx instanceof IASTTypeIdExpression) {
		//3. if given type directly
			expression.rawType = ASTStringUtil.getTypeIdString(((IASTTypeIdExpression)ctx).getTypeId());
			//TODO: check
		}
	}

	private String getMethodCallIdentifier(IASTFunctionCallExpression methodCall) {
		IASTExpression f = methodCall.getFunctionNameExpression();
		if (f instanceof IASTIdExpression) {
			return ((IASTIdExpression)f).getName().toString();
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