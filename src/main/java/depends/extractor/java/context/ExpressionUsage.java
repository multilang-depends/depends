package depends.extractor.java.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.antlr.v4.runtime.RuleContext;

import depends.deptypes.DependencyType;
import depends.extractor.GenericHandler;
import depends.javaextractor.JavaParser.ExpressionContext;
import depends.javaextractor.JavaParser.PrimaryContext;
import depends.util.Tuple;

public class ExpressionUsage {
	class Expression {
		String text; //for debug purpose
		String returnType; //the type we care
		String identifier;        //the varName, or method name, etc.
		boolean isSet = false; //is a set relation from right to leftHand
		boolean isDot = false; //is a dot expression, will decuce variable tfype left to right
		boolean isCall = false;
		
		List<Tuple<String, String>> relations = new ArrayList<>();

		@Override
		public String toString() {
			StringBuilder s = new StringBuilder();
			s.append("[").append(text).append("]").append("\n")
					.append("set=").append(isSet).append("\n")
					.append("dot=").append(isDot).append("\n")
					.append("isCall=").append(isCall).append("\n")
					.append("type=").append(returnType).append("\n")
					.append("var=").append(identifier).append("\n");
			
			for (Tuple<String, String> item : relations) {
				s.append(item.y).append("->").append(item.x).append(",");
			}
			return s.toString();
		}
	}

	GenericHandler handler;
	HashMap<RuleContext, Expression> data;

	public ExpressionUsage(GenericHandler handler) {
		this.handler = handler;
		data = new HashMap<>();
	}

	public void foundExpression(ExpressionContext ctx) {
		System.out.println("enter "+ctx.getText());
		if (ctx.creator()!=null)
			System.out.println("creator:"+ctx.creator().getText());
		Expression d = new Expression();
		d.text = ctx.getText();
		Tuple<String, String> nodeInfo = getExpressionType(ctx);
		if (nodeInfo!=null) {
			d.returnType = nodeInfo.x;
			d.identifier = nodeInfo.y;
		}
		if (d.identifier==null && ctx.IDENTIFIER()!=null)
			d.identifier = ctx.IDENTIFIER().getText();
		else if (d.identifier==null && ctx.methodCall()!=null)
			d.identifier = ctx.methodCall().IDENTIFIER().getText();
		else if (d.identifier==null && (ctx.NEW()!=null && ctx.creator()!=null)){
			d.identifier = ctx.creator().createdName().IDENTIFIER(0).getText();
		}
		d.isDot = isDot(ctx);
		d.isSet = isSet(ctx);
		d.isCall = ctx.methodCall()==null?false:true;
		data.put(ctx, d);
		updateParentType(ctx,d.returnType);
	}

	public void commitAllExpressionUsage() {
		for (RuleContext item : data.keySet()) {
			Expression value = data.get(item);
			if (value.isSet) {
				handler.addRelation(value.returnType, DependencyType.RELATION_SET);
			}
			System.out.println(value);
		}
		data.clear();
	}

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
		if (ctx.expression()!=null) return null;
		if (ctx.literal()!=null) {
			type = "Built-in";
			varName = ctx.literal().getText();
		}else if (ctx.IDENTIFIER()!=null) {
			varName = ctx.IDENTIFIER().getText();
			type = handler.context().inferType(varName);
		}else if (ctx.typeTypeOrVoid()!=null) {
			type = ClassTypeContextHelper.getClassName(ctx.typeTypeOrVoid());
		}
		return new Tuple<String, String> (type,varName);
	}

	private RuleContext findParentInStack(RuleContext ctx) {
		if (ctx==null) return null;
		if (data.containsKey(ctx.parent)) return ctx.parent;
		return findParentInStack(ctx.parent);
	}
	private void updateParentType(RuleContext ctx, String type) {
		if (type==null ) return;
		RuleContext parent = findParentInStack(ctx);
		if (parent==null) return;
		System.out.println(parent.getText());
		Expression d = data.get(parent);
		if (d==null) return;
		if (d.returnType!=null) return;
		if (d.isDot && (!d.isCall))
			d.returnType = handler.context().inferVarType(handler.context().resolveTypeNameRef(type), d.identifier);
		else if (d.isDot && d.isCall)
			d.returnType = handler.context().inferFunctionType(handler.context().resolveTypeNameRef(type),d.identifier);
		else
			d.returnType = type;
		updateParentType(parent,d.returnType);
	}
}
