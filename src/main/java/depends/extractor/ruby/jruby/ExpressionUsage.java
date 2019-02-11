package depends.extractor.ruby.jruby;

import java.util.List;

import org.jrubyparser.ast.AssignableNode;
import org.jrubyparser.ast.ClassVarNode;
import org.jrubyparser.ast.ConstNode;
import org.jrubyparser.ast.FCallNode;
import org.jrubyparser.ast.GlobalVarNode;
import org.jrubyparser.ast.ILiteralNode;
import org.jrubyparser.ast.InstVarNode;
import org.jrubyparser.ast.LocalVarNode;
import org.jrubyparser.ast.Node;
import org.jrubyparser.ast.VCallNode;

import depends.entity.ContainerEntity;
import depends.entity.Expression;
import depends.entity.VarEntity;
import depends.entity.repo.IdGenerator;
import depends.extractor.ruby.RubyHandlerContext;
import depends.relations.Inferer;

public class ExpressionUsage {
	RubyHandlerContext context;
	IdGenerator idGenerator;
	Inferer inferer;
	private RubyParserHelper helper;
	public ExpressionUsage(RubyHandlerContext context,IdGenerator idGenerator, RubyParserHelper helper, Inferer inferer) {
		this.context = context;
		this.idGenerator = idGenerator;
		this.inferer = inferer;
		this.helper = helper;
	}
	@SuppressWarnings("deprecation")
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
			expression.identifier = "<literal>";
			expression.rawType = Inferer.buildInType.getQualifiedName();
		} else if (ctx instanceof ConstNode)  {
			expression.rawType = helper.getName(ctx);
			expression.identifier = helper.getName(ctx);
		} else if (ctx instanceof LocalVarNode ||
				ctx instanceof GlobalVarNode ||
				ctx instanceof ClassVarNode||
				ctx instanceof InstVarNode)  {
			expression.identifier = helper.getName(ctx);
		} 
		if (ctx instanceof AssignableNode) {
			expression.isSet = true;
		}else if (helper.isFunctionCall(ctx)) {
			String name = helper.getName(ctx);
			expression.isCall = true;
			if (name.equals("new")) {
				expression.isCreate = true;
				List<Node> childNodes = ctx.childNodes();
				if (childNodes.size()>0) {
					expression.identifier = helper.getName(ctx.childNodes().get(0));
				}else {
					expression.identifier = context.currentType().getRawName();
				}
				expression.rawType = expression.identifier ;
				expression.deriveTypeFromChild = false;
				Node parentNode = ctx.getParent();
				if (parentNode instanceof AssignableNode) {
					ContainerEntity scope = helper.getScopeOfVar((AssignableNode)parentNode, this.context);
					VarEntity var = scope.getVarOfName(helper.getName(parentNode));
					if (var!=null) {
						var.setRawType(expression.rawType);
					}
					System.out.println(var);
				}
			}else {
				expression.identifier = name;
				expression.rawType = helper.getReciever(ctx);
				if (expression.rawType!=null) {
					expression.isDot = true;
				}
				
				if (ctx instanceof VCallNode ||
						ctx instanceof FCallNode) {
					expression.deriveTypeFromChild = false;
				}
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