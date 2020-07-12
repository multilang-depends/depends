package depends.extractor.python.union;

import depends.extractor.python.PythonHandlerContext;
import depends.extractor.python.PythonParser.Assert_stmtContext;
import depends.extractor.python.PythonParser.AtomContext;
import depends.extractor.python.PythonParser.Del_stmtContext;
import depends.extractor.python.PythonParser.ExprContext;
import depends.extractor.python.PythonParser.Expr_stmtContext;
import depends.extractor.python.PythonParser.Raise_stmtContext;
import depends.extractor.python.PythonParser.Return_stmtContext;
import depends.extractor.python.PythonParser.Testlist_star_exprContext;
import depends.extractor.python.PythonParser.TrailerContext;
import depends.extractor.python.PythonParser.Yield_stmtContext;
import depends.extractor.python.PythonParserBaseVisitor;
import depends.relations.Inferer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;

import depends.entity.Entity;
import depends.entity.Expression;
import depends.entity.FunctionEntity;
import depends.entity.GenericName;
import depends.entity.TypeEntity;
import depends.entity.VarEntity;
import depends.entity.repo.IdGenerator;
import depends.extractor.HandlerContext;
public class ExpressionUsage {
	HandlerContext context;
	IdGenerator idGenerator;
	private boolean exprStarted=false;
	private Inferer inferer;
	public ExpressionUsage(PythonHandlerContext context, IdGenerator idGenerator, Inferer inferer) {
		this.context = context;
		this.idGenerator = idGenerator;
		this.inferer = inferer;
	}

	/**
	 * Auto deduce variable type from assignment. for example: c = new C() then c is
	 * type of C
	 * 
	 * @param node
	 */
	private void deduceVarTypeInCaseOfAssignment(Expr_stmtContext expr, Expression expression) {
		List<String> names = getName(expr.testlist_star_expr());
		// TODO: should handle list properly;
		String varName = null;
		if (names.size() == 1)
			varName = names.get(0);
		if (varName == null)
			return;
		VarEntity var = context.lastContainer().lookupVarLocally(varName);
		if (var != null) {
			expression.addDeducedTypeVar(var);
		}
	}

	private List<String> getName(Testlist_star_exprContext testlist_star_expr) {
			List<String> names = new ArrayList<>();
			testlist_star_expr.accept(new NameCollector(names));
			return names;
	}

	public void foundExpression(ParserRuleContext ctx) {
		if (!isStartOfContainerRule(ctx)) {
			return ;
		}
		if (context.lastContainer().containsExpression(ctx)) return;
		if (ctx.getParent() instanceof TrailerContext) return;
		
		Expression parent = findParentInStack(ctx);
		Expression expression = parent;
		
		if (ctx.getParent().getChildCount()==1 && parent!=null) {
			//如果就是自己，则无需创建新的Expression	
		}else {
			/* create expression and link it with parent*/
			expression = new Expression(idGenerator.generateId());
			expression.setText(ctx.getText());
			context.lastContainer().addExpression(ctx,expression);
			expression.setParent(parent);
		}
		
		
		if (ctx instanceof Expr_stmtContext) {
			Expr_stmtContext exprAssign = (Expr_stmtContext)ctx;
			if (exprAssign.assign_part()!=null) {
				expression.setSet(true);
				expression.setIdentifier(exprAssign.testlist_star_expr().getText());
				if (isValidIdentifier(expression.getIdentifier())) {
					makeSureVarExist(expression.getIdentifier());
				}
				deduceVarTypeInCaseOfAssignment((Expr_stmtContext)ctx,expression);
			}
		}
		if (ctx instanceof Raise_stmtContext) {
			expression.setThrow (true);
		}
		if (ctx instanceof Return_stmtContext) {
			deduceReturnTypeInCaseOfReturn((Return_stmtContext)ctx,expression);
		}
		if (ctx instanceof ExprContext) {
			processExprContext((ExprContext)ctx, expression);
		}
		
	}

	private void deduceReturnTypeInCaseOfReturn(Return_stmtContext ctx, Expression expression) {
		FunctionEntity currentFunction = context.currentFunction();
		if (currentFunction == null)
			return;
		expression.addDeducedTypeFunction(currentFunction);
	}


	private void makeSureVarExist(GenericName identifier) {
		if (null==context.foundEntityWithName(identifier)) {
			context.foundVarDefinition(context.lastContainer(), identifier.getName());
		}
	}

	private boolean isValidIdentifier(GenericName identifier) {
		Pattern p = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*");
		Matcher m = p.matcher(identifier.getName());
		return m.matches();
	}



	private void processExprContext(ExprContext exprCtx, Expression expression) {
		//func_call, member_access, subscript member, and atom
		Expression lastExpression = null;
		if (exprCtx.atom()!=null) {
			//atom
			Expression atomExpr = new Expression(idGenerator.generateId());
			atomExpr.setParent(expression);
			atomExpr.setText(exprCtx.atom().getText());
			atomExpr.setIdentifier(exprCtx.atom().getText());
			context.lastContainer().addExpression(exprCtx.atom(),atomExpr);
			processAtom(exprCtx.atom(),atomExpr);
			lastExpression = atomExpr;
			if (exprCtx.trailer()==null || exprCtx.trailer().size()==0) {
				//do nothing; it is just an id;
			}else {
				for (TrailerContext trailer:exprCtx.trailer()) {
					if (trailer.name()!=null) {
						Expression trailerExpr = new Expression(idGenerator.generateId());
						trailerExpr.setText(trailer.getText());
						context.lastContainer().addExpression(trailer,trailerExpr);
						trailerExpr.setParent(expression);

						//doted name = member access or method call
						trailerExpr.setDot(true);;
						trailerExpr.setIdentifier(trailer.name().getText());
						if (trailer.arguments()!=null) {
							if (trailer.arguments().OPEN_PAREN()!=null) {
								foundCallStyleExpressionWithDot(trailerExpr,lastExpression.getIdentifier());
							}else {
								//subscript list, do nothing
							}
						}
						lastExpression.setParent(trailerExpr);
						lastExpression = trailerExpr;
					}else {
						//direct call, or direct data access
						if (trailer.arguments()!=null) {
							if (trailer.arguments().OPEN_PAREN()!=null) {
								foundCallStyleExpressionWithoutDot(lastExpression);
							}else {
								//subscript list, do nothing
							}
						}
					}
				}
			}
		}else {
/**			expr
		    | <assoc=right> expr op=POWER expr
		    | op=(ADD | MINUS | NOT_OP) expr
		    | expr op=(STAR | DIV | MOD | IDIV | AT) expr
		    | expr op=(ADD | MINUS) expr
		    | expr op=(LEFT_SHIFT | RIGHT_SHIFT) expr
		    | expr op=AND_OP expr
		    | expr op=XOR expr
		    | expr op=OR_OP expr
		    ;*/

		}
	}


	private boolean isStartOfContainerRule(ParserRuleContext ctx) {
		if (this.exprStarted) return true;
		return ctx instanceof ExprContext ||
				ctx instanceof Expr_stmtContext ||
				ctx instanceof Del_stmtContext ||
				ctx instanceof Return_stmtContext ||
				ctx instanceof Raise_stmtContext ||
				ctx instanceof Raise_stmtContext ||
				ctx instanceof Yield_stmtContext ||
				ctx instanceof Assert_stmtContext;
		}



	private void foundCallStyleExpressionWithDot(Expression theExpression, GenericName varName) {
		GenericName funcName = theExpression.getIdentifier();
		Entity prefixEntity = context.foundEntityWithName(varName);
		if (prefixEntity instanceof VarEntity) {
			((VarEntity) prefixEntity).addFunctionCall(funcName);
		}
		Entity typeEntity = context.foundEntityWithName(funcName);
		if (typeEntity instanceof TypeEntity && typeEntity.getId() > 0) {
			theExpression.setCreate(true);
			theExpression.setType(typeEntity.getType(), typeEntity, inferer);
			theExpression.setRawType(typeEntity.getRawName());
			return;
		} 
		theExpression.setCall(true);
	}



	private void foundCallStyleExpressionWithoutDot(Expression theExpression) {
		GenericName funcName = theExpression.getIdentifier();
		Entity typeEntity = context.foundEntityWithName(funcName);
		if (typeEntity instanceof TypeEntity && typeEntity.getId() > 0) {
			theExpression.getParent().setCreate(true);
			theExpression.setType(typeEntity.getType(), typeEntity, inferer);
			theExpression.getParent().setRawType(typeEntity.getRawName());
			return;
		} 
		theExpression.setCall(true);
	}


	private void processAtom(AtomContext atom, Expression expression) {
		if (atom.name()!=null) {
			expression.setIdentifier(atom.getText());
			return;
		}
		if (atom.STRING()!=null 
				|| atom.NONE()!=null
				|| atom.number()!=null) {
			expression.setRawType("<Built-in>");
			expression.setIdentifier("<Literal>");
			return;
		}

		if (atom.EXEC()!=null
		|| atom.PRINT()!=null
		|| atom.ELLIPSIS()!=null) {
			return;
		}
//	    : OPEN_PAREN (yield_expr | testlist_comp)? CLOSE_PAREN
//	    | OPEN_BRACKET testlist_comp? CLOSE_BRACKET
//	    | OPEN_BRACE dictorsetmaker? CLOSE_BRACE
//	    | REVERSE_QUOTE testlist COMMA? REVERSE_QUOTE
		return;
	}	





	private Expression findParentInStack(RuleContext ctx) {
		if (ctx==null) return null;
		if (ctx.parent==null) return null;
		if (context.lastContainer()==null) {
			return null;
		}
		if (context.lastContainer().expressions().containsKey(ctx.parent)) 
			return context.lastContainer().expressions().get(ctx.parent);
		return findParentInStack(ctx.parent);
	}



	public void startExpr() {
		this.exprStarted = true;
	}



	public void stopExpr() {
		this.exprStarted = false;
	}
	
	
}

class NameCollector extends PythonParserBaseVisitor<Void>{
	private List<String> names;
	NameCollector(List<String> names){
		this.names = names;
	}
	@Override
	public Void visitAtom(AtomContext ctx) {
		if (ctx.name()!=null)
			names.add(ctx.name().getText());
		return super.visitAtom(ctx);
	}
}