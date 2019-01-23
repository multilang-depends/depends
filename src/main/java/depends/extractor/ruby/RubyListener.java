package depends.extractor.ruby;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;

import depends.entity.VarEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.ruby.RubyParser.Alias_statementContext;
import depends.extractor.ruby.RubyParser.Class_definitionContext;
import depends.extractor.ruby.RubyParser.ExprArrayAccessContext;
import depends.extractor.ruby.RubyParser.ExprAssignContext;
import depends.extractor.ruby.RubyParser.ExprBatchAssignContext;
import depends.extractor.ruby.RubyParser.ExprBlockContext;
import depends.extractor.ruby.RubyParser.ExprCalcuationContext;
import depends.extractor.ruby.RubyParser.ExprCompareLogicalContext;
import depends.extractor.ruby.RubyParser.ExprConditionStatementContext;
import depends.extractor.ruby.RubyParser.ExprDefineTestContext;
import depends.extractor.ruby.RubyParser.ExprDotClassContext;
import depends.extractor.ruby.RubyParser.ExprDotRefContext;
import depends.extractor.ruby.RubyParser.ExprEqualTestContext;
import depends.extractor.ruby.RubyParser.ExprFunctionCall1Context;
import depends.extractor.ruby.RubyParser.ExprFunctionCall2Context;
import depends.extractor.ruby.RubyParser.ExprFunctionCall3Context;
import depends.extractor.ruby.RubyParser.ExprListContext;
import depends.extractor.ruby.RubyParser.ExprLogicalJoinContext;
import depends.extractor.ruby.RubyParser.ExprLogicalNotContext;
import depends.extractor.ruby.RubyParser.ExprPatternMatch1Context;
import depends.extractor.ruby.RubyParser.ExprPatternMatch2Context;
import depends.extractor.ruby.RubyParser.ExprPatternMatch3Context;
import depends.extractor.ruby.RubyParser.ExprPrefixCalcContext;
import depends.extractor.ruby.RubyParser.ExprPrimaryContext;
import depends.extractor.ruby.RubyParser.ExprQuestionContext;
import depends.extractor.ruby.RubyParser.ExprRangeContext;
import depends.extractor.ruby.RubyParser.ExprWitStatementSuffixContext;
import depends.extractor.ruby.RubyParser.Function_call_paramContext;
import depends.extractor.ruby.RubyParser.Function_definitionContext;
import depends.extractor.ruby.RubyParser.Function_definition_paramContext;
import depends.extractor.ruby.RubyParser.Module_definitionContext;
import depends.extractor.ruby.RubyParser.PrimaryBeginBlockContext;
import depends.extractor.ruby.RubyParser.PrimaryBlockCase1Context;
import depends.extractor.ruby.RubyParser.PrimaryBlockCase2Context;
import depends.extractor.ruby.RubyParser.PrimaryBlockClassDefContext;
import depends.extractor.ruby.RubyParser.PrimaryBlockContext;
import depends.extractor.ruby.RubyParser.PrimaryBlockForContext;
import depends.extractor.ruby.RubyParser.PrimaryBlockFunctionDefContext;
import depends.extractor.ruby.RubyParser.PrimaryBlockIfContext;
import depends.extractor.ruby.RubyParser.PrimaryBlockModelDefContext;
import depends.extractor.ruby.RubyParser.PrimaryBlockUnlessContext;
import depends.extractor.ruby.RubyParser.PrimaryBlockUntilContext;
import depends.extractor.ruby.RubyParser.PrimaryBlockWhenContext;
import depends.extractor.ruby.RubyParser.PrimaryBlockWhileContext;
import depends.extractor.ruby.RubyParser.PrimaryBracketContext;
import depends.extractor.ruby.RubyParser.PrimaryFunctionCall0Context;
import depends.extractor.ruby.RubyParser.PrimaryListExprContext;
import depends.extractor.ruby.RubyParser.PrimaryListHashContext;
import depends.extractor.ruby.RubyParser.PrimaryRangeContext;
import depends.extractor.ruby.RubyParser.PrimaryRegexContext;
import depends.extractor.ruby.RubyParser.PrimaryStatementBreakContext;
import depends.extractor.ruby.RubyParser.PrimaryStatementRaiseContext;
import depends.extractor.ruby.RubyParser.PrimaryStatementRescueContext;
import depends.extractor.ruby.RubyParser.PrimaryStatementReturnContext;
import depends.extractor.ruby.RubyParser.PrimaryStatementYieldContext;
import depends.extractor.ruby.RubyParser.PrimarySymbolContext;
import depends.extractor.ruby.RubyParser.PrimaryVarPathContext;
import depends.extractor.ruby.RubyParser.Undef_statementContext;
import depends.relations.Inferer;

/*
 * Undef  - 这个支持起来比较复杂
 * 全局变量，类变量和成员变量的支持；查变量不要查错了
 * .New确定类型；
 * 从函数返回值中确定类型；
 * 表达式确定类型；
 */
public class RubyListener extends RubyParserBaseListener {

	private RubyHandlerContext context;
	private EntityRepo entityRepo;
	RubyParserHelper helper = new RubyParserHelper();
	private ExpressionUsage expressionUsage;

	public RubyListener(String fileFullPath, EntityRepo entityRepo, IncludedFileLocator includedFileLocator,
			ExecutorService executorService, Inferer inferer) {
		this.context = new RubyHandlerContext(entityRepo, includedFileLocator, executorService, inferer);
		this.entityRepo = entityRepo;
		expressionUsage = new ExpressionUsage(context, entityRepo, helper,inferer);
		context.startFile(fileFullPath);
	}

	// Alias
	@Override
	public void enterAlias_statement(Alias_statementContext ctx) {
		context.foundNewTypeAlias(helper.getName(ctx.function_name_or_symbol(0)),
				helper.getName(ctx.function_name_or_symbol(1)));
		super.enterAlias_statement(ctx);
	}

	// Undef
	@Override
	public void enterUndef_statement(Undef_statementContext ctx) {
		// TODO Auto-generated method stub
		super.enterUndef_statement(ctx);
	}

	// Module
	@Override
	public void enterModule_definition(Module_definitionContext ctx) {
		context.foundNamespace(helper.getName(ctx));
		super.enterModule_definition(ctx);
	}

	@Override
	public void exitModule_definition(Module_definitionContext ctx) {
		context.exitLastedEntity();
		super.exitModule_definition(ctx);
	}

	// Class
	@Override
	public void enterClass_definition(Class_definitionContext ctx) {
		if (helper.isSingletonClass(ctx)) {
			// TODO: lookup the name of variable, and add method to it
			String varName = helper.getName(ctx);
		} else {
			context.foundNewType(helper.getName(ctx));
			System.out.println(helper.getName(ctx));
			String parentType = helper.getParentType(ctx.class_header());
			if (parentType != null) {
				context.foundExtends(parentType);
			}
		}
		super.enterClass_definition(ctx);
	}

	@Override
	public void exitClass_definition(Class_definitionContext ctx) {
		if (helper.isSingletonClass(ctx)) {
			// TODO: lookup the name of variable, and gracefully exit it
			String varName = helper.getName(ctx);
		} else {
			context.exitLastedEntity();
		}
		super.exitClass_definition(ctx);
	}

	// Function
	@Override
	public void enterFunction_definition(Function_definitionContext ctx) {
		context.foundMethodDeclarator(helper.getName(ctx), null, new ArrayList<>());
		if (ctx.function_definition_header().function_definition_params() != null) {
			for (Function_definition_paramContext param : ctx.function_definition_header().function_definition_params()
					.function_definition_param()) {
				VarEntity var = new VarEntity(helper.getName(param), null, context.currentFunction(),
						entityRepo.generateId());
				context.currentFunction().addParameter(var);
			}
		}
		super.enterFunction_definition(ctx);
	}

	@Override
	public void exitFunction_definition(Function_definitionContext ctx) {
		context.exitLastedEntity();
		super.exitFunction_definition(ctx);
	}

	// Assign
	@Override
	public void enterExprBatchAssign(ExprBatchAssignContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterExprBatchAssign(ctx);
	}

	// Assign
	@Override
	public void enterExprAssign(ExprAssignContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterExprAssign(ctx);
	}

	// Call
	@Override
	public void enterPrimaryFunctionCall0(PrimaryFunctionCall0Context ctx) {
		String fname = this.helper.getName(ctx.function_name());
		List<Function_call_paramContext> parameters = ctx.func_call_parameters().function_call_param();
		Collection<String> params = helper.getAllArgName(ctx.func_call_parameters().function_call_param());
		context.processSpecialFuncCall(fname, params);
		expressionUsage.foundExpression(ctx);
		super.enterPrimaryFunctionCall0(ctx);
	}

	@Override
	public void enterExprFunctionCall1(ExprFunctionCall1Context ctx) {
		String fname = this.helper.getName(ctx.function_name());
		Collection<String> params = helper.getAllArgName(ctx.func_call_parameters_no_bracket().function_call_param());
		context.processSpecialFuncCall(fname, params);
		expressionUsage.foundExpression(ctx);
		super.enterExprFunctionCall1(ctx);
	}

	@Override
	public void enterExprFunctionCall2(ExprFunctionCall2Context ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterExprFunctionCall2(ctx);
	}

	@Override
	public void enterExprFunctionCall3(ExprFunctionCall3Context ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterExprFunctionCall3(ctx);
	}

	@Override
	public void enterExprPrimary(ExprPrimaryContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterExprPrimary(ctx);
	}

	@Override
	public void enterExprList(ExprListContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterExprList(ctx);
	}

	@Override
	public void enterExprDotRef(ExprDotRefContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterExprDotRef(ctx);
	}

	@Override
	public void enterExprQuestion(ExprQuestionContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterExprQuestion(ctx);
	}

	@Override
	public void enterExprPrefixCalc(ExprPrefixCalcContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterExprPrefixCalc(ctx);
	}

	@Override
	public void enterExprArrayAccess(ExprArrayAccessContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterExprArrayAccess(ctx);
	}

	@Override
	public void enterExprDefineTest(ExprDefineTestContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterExprDefineTest(ctx);
	}

	@Override
	public void enterExprRange(ExprRangeContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterExprRange(ctx);
	}

	@Override
	public void enterExprPatternMatch1(ExprPatternMatch1Context ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterExprPatternMatch1(ctx);
	}

	@Override
	public void enterExprPatternMatch2(ExprPatternMatch2Context ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterExprPatternMatch2(ctx);
	}

	@Override
	public void enterExprPatternMatch3(ExprPatternMatch3Context ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterExprPatternMatch3(ctx);
	}

	@Override
	public void enterExprLogicalNot(ExprLogicalNotContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterExprLogicalNot(ctx);
	}

	@Override
	public void enterExprCompareLogical(ExprCompareLogicalContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterExprCompareLogical(ctx);
	}

	@Override
	public void enterExprLogicalJoin(ExprLogicalJoinContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterExprLogicalJoin(ctx);
	}

	@Override
	public void enterExprEqualTest(ExprEqualTestContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterExprEqualTest(ctx);
	}

	@Override
	public void enterExprCalcuation(ExprCalcuationContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterExprCalcuation(ctx);
	}

	@Override
	public void enterExprConditionStatement(ExprConditionStatementContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterExprConditionStatement(ctx);
	}

	@Override
	public void enterExprBlock(ExprBlockContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterExprBlock(ctx);
	}

	@Override
	public void enterExprWitStatementSuffix(ExprWitStatementSuffixContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterExprWitStatementSuffix(ctx);
	}

	@Override
	public void enterExprDotClass(ExprDotClassContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterExprDotClass(ctx);
	}

	@Override
	public void enterPrimaryVarPath(PrimaryVarPathContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterPrimaryVarPath(ctx);
	}

	@Override
	public void enterPrimaryRegex(PrimaryRegexContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterPrimaryRegex(ctx);
	}

	@Override
	public void enterPrimarySymbol(PrimarySymbolContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterPrimarySymbol(ctx);
	}

	@Override
	public void enterPrimaryBracket(PrimaryBracketContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterPrimaryBracket(ctx);
	}

	@Override
	public void enterPrimaryBlock(PrimaryBlockContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterPrimaryBlock(ctx);
	}

	@Override
	public void enterPrimaryStatementBreak(PrimaryStatementBreakContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterPrimaryStatementBreak(ctx);
	}

	@Override
	public void enterPrimaryStatementReturn(PrimaryStatementReturnContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterPrimaryStatementReturn(ctx);
	}

	@Override
	public void enterPrimaryStatementRaise(PrimaryStatementRaiseContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterPrimaryStatementRaise(ctx);
	}

	@Override
	public void enterPrimaryStatementRescue(PrimaryStatementRescueContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterPrimaryStatementRescue(ctx);
	}

	@Override
	public void enterPrimaryStatementYield(PrimaryStatementYieldContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterPrimaryStatementYield(ctx);
	}

	@Override
	public void enterPrimaryBeginBlock(PrimaryBeginBlockContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterPrimaryBeginBlock(ctx);
	}

	@Override
	public void enterPrimaryBlockIf(PrimaryBlockIfContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterPrimaryBlockIf(ctx);
	}

	@Override
	public void enterPrimaryBlockWhen(PrimaryBlockWhenContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterPrimaryBlockWhen(ctx);
	}

	@Override
	public void enterPrimaryBlockUnless(PrimaryBlockUnlessContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterPrimaryBlockUnless(ctx);
	}

	@Override
	public void enterPrimaryBlockCase1(PrimaryBlockCase1Context ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterPrimaryBlockCase1(ctx);
	}

	@Override
	public void enterPrimaryBlockCase2(PrimaryBlockCase2Context ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterPrimaryBlockCase2(ctx);
	}

	@Override
	public void enterPrimaryBlockWhile(PrimaryBlockWhileContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterPrimaryBlockWhile(ctx);
	}

	@Override
	public void enterPrimaryBlockUntil(PrimaryBlockUntilContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterPrimaryBlockUntil(ctx);
	}

	@Override
	public void enterPrimaryBlockFor(PrimaryBlockForContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterPrimaryBlockFor(ctx);
	}

	@Override
	public void enterPrimaryBlockClassDef(PrimaryBlockClassDefContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterPrimaryBlockClassDef(ctx);
	}

	@Override
	public void enterPrimaryBlockFunctionDef(PrimaryBlockFunctionDefContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterPrimaryBlockFunctionDef(ctx);
	}

	@Override
	public void enterPrimaryBlockModelDef(PrimaryBlockModelDefContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterPrimaryBlockModelDef(ctx);
	}

	@Override
	public void enterPrimaryListHash(PrimaryListHashContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterPrimaryListHash(ctx);
	}

	@Override
	public void enterPrimaryListExpr(PrimaryListExprContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterPrimaryListExpr(ctx);
	}

	@Override
	public void enterPrimaryRange(PrimaryRangeContext ctx) {
		expressionUsage.foundExpression(ctx);
		super.enterPrimaryRange(ctx);
	}

}
