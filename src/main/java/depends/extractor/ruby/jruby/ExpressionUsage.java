package depends.extractor.ruby.jruby;

import java.util.ArrayList;
import java.util.List;

import org.jrubyparser.ast.AssignableNode;
import org.jrubyparser.ast.CallNode;
import org.jrubyparser.ast.ClassVarAsgnNode;
import org.jrubyparser.ast.ClassVarDeclNode;
import org.jrubyparser.ast.ConstDeclNode;
import org.jrubyparser.ast.DAsgnNode;
import org.jrubyparser.ast.FCallNode;
import org.jrubyparser.ast.GlobalAsgnNode;
import org.jrubyparser.ast.ILiteralNode;
import org.jrubyparser.ast.InstAsgnNode;
import org.jrubyparser.ast.ListNode;
import org.jrubyparser.ast.LocalAsgnNode;
import org.jrubyparser.ast.MultipleAsgnNode;
import org.jrubyparser.ast.Node;
import org.jrubyparser.ast.UnaryCallNode;
import org.jrubyparser.ast.VCallNode;

import depends.entity.Expression;
import depends.entity.repo.IdGenerator;
import depends.extractor.HandlerContext;
import depends.relations.Inferer;

public class ExpressionUsage {
	HandlerContext context;
	IdGenerator idGenerator;
	Inferer inferer;
	private RubyParserHelper helper;
	public ExpressionUsage(HandlerContext context,IdGenerator idGenerator, RubyParserHelper helper, Inferer inferer) {
		this.context = context;
		this.idGenerator = idGenerator;
		this.inferer = inferer;
		this.helper = helper;
	}
	public Expression foundExpression(Node ctx) {
		Expression expression = findExpression(ctx);
		if (expression!=null) return expression;
		Expression parent = findParentInStack(ctx);
		//System.out.println("expr " + ctx.toString());
		/* create expression and link it with parent*/
		expression = new Expression(idGenerator.generateId());
		expression.parent = parent;
		if (expression.parent!=null) {
			if (expression.parent.deduceTypeBasedId==null) 
				expression.parent.deduceTypeBasedId = expression.id;
			/* Set operation always use the 2nd expr's type*/
			if (expression.parent.isSet) {
				expression.parent.deduceTypeBasedId = expression.id;
				parent.addDeduceTypeChild(expression);
			}
		}
		context.lastContainer().addExpression(ctx,expression);
		if (ctx instanceof ILiteralNode) {
			expression.identifier = ((ILiteralNode)ctx).toString();
			expression.rawType = Inferer.buildInType.getQualifiedName();
		}else if (ctx instanceof AssignableNode) {
			expression.isSet = true;
			Node valueNode = ((AssignableNode)ctx).getValue();
			List<String> names = helper.getName((AssignableNode)ctx);
			if (names.size()==1) {
				expression.autoVar = true;
				expression.identifier = names.get(0);
			}else {
			}
		}else if (helper.isFunctionCall(ctx)) {
			String name = helper.getName(ctx);
			if (name.equals("new")) {
				expression.isCreate = true;
				List<Node> childNodes = ctx.childNodes();
				if (childNodes.size()>0) {
					expression.identifier = helper.getName(ctx.childNodes().get(0));
				}else {
					expression.identifier = context.currentType().getRawName();
				}
				expression.rawType = expression.identifier ;
				expression.deriveTypeFromChild  = false;
			}
		}
		return expression;
	}
	
	private Expression findParentInStack(Node ctx) {
		if (ctx==null) return null;
		if (ctx.getParent()==null) return null;
		if (context.lastContainer()==null) {
			return null;
		}
		if (context.lastContainer().expressions().containsKey(ctx.getParent())) return context.lastContainer().expressions().get(ctx.getParent());
		return findParentInStack(ctx.getParent());
	}
	
	private Expression findExpression(Node ctx) {
		if (ctx==null) return null;
		if (ctx.getParent()==null) return null;
		if (context.lastContainer()==null) {
			return null;
		}
		return context.lastContainer().expressions().get(ctx);
	}

}