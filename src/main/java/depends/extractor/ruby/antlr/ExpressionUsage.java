package depends.extractor.ruby.antlr;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;

import depends.entity.Expression;
import depends.entity.repo.IdGenerator;
import depends.extractor.HandlerContext;
import depends.extractor.ruby.RubyParser.ExprAssignContext;
import depends.extractor.ruby.RubyParser.ExprBatchAssignContext;
import depends.extractor.ruby.RubyParser.ExprContext;
import depends.extractor.ruby.RubyParser.ExprListContext;
import depends.extractor.ruby.RubyParser.ExprPrimaryContext;
import depends.extractor.ruby.RubyParser.PrimaryVarPathContext;
import depends.extractor.ruby.RubyParser.VarPathLiteralContext;
import depends.extractor.ruby.RubyParser.Variable_pathContext;
import depends.relations.Inferer;

public class ExpressionUsage {
	HandlerContext context;
	IdGenerator idGenerator;
	private RubyParserHelper helper;
	Inferer inferer;
	public ExpressionUsage(HandlerContext context,IdGenerator idGenerator, RubyParserHelper helper,Inferer inferer) {
		this.context = context;
		this.idGenerator = idGenerator;
		this.helper = helper;
		this.inferer = inferer;
	}
	public Expression foundExpression(ParserRuleContext ctx) {
		Expression expression = findExpression(ctx);
		if (expression!=null) return expression;
		Expression parent = findParentInStack(ctx);
		System.out.println("expr " + ctx.getText());
		/* create expression and link it with parent*/
		expression = new Expression(idGenerator.generateId(),parent==null?null:parent.id);
		expression.parent = parent;
		expression.text = ctx.getText();
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
		if (ctx instanceof PrimaryVarPathContext) {
			Variable_pathContext variable_path = ((PrimaryVarPathContext)ctx).variable_path();
			expression.identifier = helper.getName(variable_path);
			if (variable_path instanceof VarPathLiteralContext) {
				expression.rawType =Inferer.buildInType.getDisplayName();
			}else  {
				//to be deduced
			}
		}
		if (ctx instanceof ExprBatchAssignContext) {
			expression.isSet = true;
  		    ExprContext left = ((ExprBatchAssignContext) ctx).expr(0);
			ExprContext right = ((ExprBatchAssignContext) ctx).expr(1);
			if (left instanceof ExprListContext) {
				
			}else if (left instanceof ExprPrimaryContext) {
				expression.identifier = helper.getName(((ExprPrimaryContext)left).primary());
				expression.rawType = null;//need deduce from right type
				expression.autoVar = true;
			}
		}
		if (ctx instanceof ExprAssignContext) {
			expression.isSet = true;
		}
		if (ctx instanceof ExprPrimaryContext) {
			
		}
		return expression;
	}
	
	private Expression findParentInStack(RuleContext ctx) {
		if (ctx==null) return null;
		if (ctx.getParent()==null) return null;
		if (context.lastContainer()==null) {
			return null;
		}
		if (context.lastContainer().expressions().containsKey(ctx.getParent())) return context.lastContainer().expressions().get(ctx.getParent());
		return findParentInStack(ctx.getParent());
	}
	
	private Expression findExpression(RuleContext ctx) {
		if (ctx==null) return null;
		if (ctx.getParent()==null) return null;
		if (context.lastContainer()==null) {
			return null;
		}
		return context.lastContainer().expressions().get(ctx.getParent());
	}

}