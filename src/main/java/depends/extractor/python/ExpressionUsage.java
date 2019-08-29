package depends.extractor.python;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import depends.entity.ContainerEntity;
import depends.entity.Entity;
import depends.entity.Expression;
import depends.entity.FunctionEntity;
import depends.entity.TypeEntity;
import depends.entity.VarEntity;
import depends.entity.repo.IdGenerator;
import depends.extractor.python.Python3Parser.AtomContext;
import depends.extractor.python.Python3Parser.Atom_exprContext;
import depends.extractor.python.Python3Parser.Expr_stmtContext;
import depends.extractor.python.Python3Parser.Raise_stmtContext;
import depends.extractor.python.Python3Parser.Return_stmtContext;
import depends.extractor.python.Python3Parser.SuiteContext;
import depends.extractor.python.Python3Parser.TestContext;
import depends.relations.Inferer;

public class ExpressionUsage {
	IdGenerator idGenerator;
	Inferer inferer;
	private PythonHandlerContext context;
	private PythonParserHelper helper;
	public ExpressionUsage(PythonHandlerContext context,IdGenerator idGenerator, PythonParserHelper helper, Inferer inferer) {
		this.context = context;
		this.idGenerator = idGenerator;
		this.inferer = inferer;
		this.helper = helper;
	}
	
	public Expression foundExpression(ParserRuleContext ctx) {
		if (!context.lastContainer().containsExpression()){
			if (!isStartOfContainerRule(ctx)) {
				return null;
			}
		}
		Expression expression = findExpression(ctx);
		if (expression!=null) return expression;
		Expression parent = findParentInStack(ctx);
		/* create expression and link it with parent*/
		expression = new Expression(idGenerator.generateId());
		expression.text = ctx.getText();
		expression.setParent(parent);

		context.lastContainer().addExpression(ctx,expression);
		if (ctx instanceof AtomContext) {
			AtomContext atom = (AtomContext)ctx;
			if (atom.NUMBER()!=null ||
					atom.STRING()!=null &&
					atom.STRING().size()>0) {
				expression.identifier = "<literal>";
				expression.rawType = Inferer.buildInType.getQualifiedName();
			}else if (atom.getText().equals("True")||
					atom.getText().equals("False")) {
				expression.identifier = "<boolean>";
				expression.rawType = Inferer.buildInType.getQualifiedName();
			}else if (atom.getText().equals("None")) {
				expression.identifier = "<null>";
				expression.rawType = Inferer.buildInType.getQualifiedName();
			}else if (atom.NAME()!=null) {
				expression.identifier = atom.NAME().getText();
			}
		}
		
		
		if (ctx instanceof Expr_stmtContext) {
			Expr_stmtContext expr = ((Expr_stmtContext)ctx);
			if ((expr.expr_stmt_rhs()!=null)||
					(expr.augassign()!=null)||
					(expr.annassign()!=null))
			{
				expression.isSet = true;
			}
		}else if (ctx instanceof Atom_exprContext) {
			Atom_exprContext expr = ((Atom_exprContext)ctx);
			if (expr.func_call()!=null) {
				//TODO: should be refined later. Currently only a.b.c could be solved.
				expression.identifier = expr.atom_expr().getText();
				String callPrefix = expr.atom_expr().getText();
				//call with variables
				if (callPrefix.contains(".")) {
					int pos = callPrefix.lastIndexOf('.');
					String functionName = callPrefix.substring(pos+1);
					String preFix = callPrefix.substring(0,pos);
					Entity prefixEntity = context.foundEntityWithName(preFix);
					if (prefixEntity instanceof VarEntity) {
						((VarEntity)prefixEntity).addFunctionCall(functionName);
						expression.isCall = true;
					}
				}
				if (!expression.isCall) {
					Entity typeEntity = context.foundEntityWithName(expression.identifier);
					if (typeEntity instanceof TypeEntity &&
							typeEntity.getId()>0) {
						expression.isCreate = true;
					}else {
						expression.isCall = true;
					}
				}
			}
			else if (expr.member_access()!=null) {
				expression.isDot = true;
				expression.identifier = expr.member_access().NAME().getText();
			}
			//TODO: member access in python should be handled seperately. they could be different types;
		}else if (ctx instanceof Return_stmtContext) {
			deduceReturnTypeInCaseOfReturn(ctx,expression);
		}else if (ctx instanceof Raise_stmtContext) {
			expression.isThrow = true;
			expression.deriveTypeFromChild = true;
		}
		deduceVarTypeInCaseOfAssignment(ctx, expression);
		return expression;
	}

	/**
	 * To judge whether is an 'real' expression 
	 * @param ctx
	 * @return
	 */
	private boolean isStartOfContainerRule(ParserRuleContext ctx) {
		return ctx instanceof SuiteContext ||
				ctx instanceof TestContext ||
				ctx instanceof Expr_stmtContext;
	}


	/**
	 * Auto deduce variable type from assignment.
	 * for example:
	 *       c = C.new  then c is type of C
	 * @param node
	 * @param expression
	 */
	private void deduceVarTypeInCaseOfAssignment(ParserRuleContext node, Expression expression) {
		ParserRuleContext parentNode = node.getParent();
		if (parentNode instanceof Expr_stmtContext) {
			Expr_stmtContext expr = (Expr_stmtContext)parentNode;
			if (expr.expr_stmt_rhs()!=null) {
				ContainerEntity scope = helper.getScopeOfVar(expr, this.context);
				if (scope==null) return;
				String varName = null;
				//TODO: should handle list properly;
				List<String> names = helper.getName(expr.testlist_star_expr());
				if (names.size()==1)
					varName = names.get(0);
				if (varName==null) return;
				VarEntity var = scope.lookupVarLocally(varName );
				if (var!=null) {
					expression.addDeducedTypeVar(var);
				}
			}
		}
	}
	
	private void deduceReturnTypeInCaseOfReturn(ParserRuleContext ctx, Expression expression) {
		FunctionEntity currentFunction = context.currentFunction();
		if (currentFunction ==null) return;
		if (ctx instanceof Return_stmtContext) {
			expression.addDeducedTypeFunction(currentFunction);
		}
	}
	
	private Expression findParentInStack(ParserRuleContext ctx) {
		if (ctx==null) return null;
		if (ctx.getParent()==null) return null;
		if (context.lastContainer()==null) {
			return null;
		}
		if (context.lastContainer().expressions().containsKey(ctx.getParent())) return context.lastContainer().expressions().get(ctx.getParent());
		return findParentInStack(ctx.getParent());
	}
	
	private Expression findExpression(ParserRuleContext ctx) {
		if (ctx==null) return null;
		if (ctx.getParent()==null) return null;
		if (context.lastContainer()==null) {
			return null;
		}
		return context.lastContainer().expressions().get(ctx);
	}

}
