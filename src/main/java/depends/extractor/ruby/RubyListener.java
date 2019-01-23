package depends.extractor.ruby;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;

import depends.entity.VarEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.ruby.RubyParser.Alias_statementContext;
import depends.extractor.ruby.RubyParser.Class_definitionContext;
import depends.extractor.ruby.RubyParser.ExprAssignContext;
import depends.extractor.ruby.RubyParser.ExprBatchAssignContext;
import depends.extractor.ruby.RubyParser.ExprFunctionCall1Context;
import depends.extractor.ruby.RubyParser.ExprFunctionCall2Context;
import depends.extractor.ruby.RubyParser.ExprFunctionCall3Context;
import depends.extractor.ruby.RubyParser.Function_call_paramContext;
import depends.extractor.ruby.RubyParser.Function_definitionContext;
import depends.extractor.ruby.RubyParser.Function_definition_paramContext;
import depends.extractor.ruby.RubyParser.Module_definitionContext;
import depends.extractor.ruby.RubyParser.PrimaryFunctionCall0Context;
import depends.extractor.ruby.RubyParser.PrimaryStatementRaiseContext;
import depends.extractor.ruby.RubyParser.PrimaryStatementReturnContext;
import depends.extractor.ruby.RubyParser.PrimaryStatementYieldContext;
import depends.extractor.ruby.RubyParser.Undef_statementContext;
import depends.relations.Inferer;

public class RubyListener extends RubyParserBaseListener{

	private RubyHandlerContext context;
	private EntityRepo entityRepo;
	RubyParserHelper helper = new RubyParserHelper();
	
	public RubyListener(String fileFullPath, EntityRepo entityRepo,IncludedFileLocator includedFileLocator,ExecutorService executorService, Inferer inferer) {
		this.context = new RubyHandlerContext(entityRepo,includedFileLocator,executorService,inferer);
		this.entityRepo = entityRepo;
		context.startFile(fileFullPath);		
	}

	
	//Alias
	@Override
	public void enterAlias_statement(Alias_statementContext ctx) {
		context.foundNewTypeAlias(helper.getName(ctx.function_name_or_symbol(0)), helper.getName(ctx.function_name_or_symbol(1)));
		super.enterAlias_statement(ctx);
	}

	//Undef
	@Override
	public void enterUndef_statement(Undef_statementContext ctx) {
		// TODO Auto-generated method stub
		super.enterUndef_statement(ctx);
	}

	//Module
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


	//Class
	@Override
	public void enterClass_definition(Class_definitionContext ctx) {
		if (helper.isSingletonClass(ctx)) {
			//TODO: lookup the name of variable, and add method to it
			String varName = helper.getName(ctx);
		}else {
			context.foundNewType(helper.getName(ctx));
			System.out.println(helper.getName(ctx));
			String parentType = helper.getParentType(ctx.class_header());
			if (parentType!=null) {
				context.foundExtends(parentType);
			}
		}
		super.enterClass_definition(ctx);
	}
	@Override
	public void exitClass_definition(Class_definitionContext ctx) {
		if (helper.isSingletonClass(ctx)) {
			//TODO: lookup the name of variable, and gracefully exit it
			String varName = helper.getName(ctx);
		}else {
			context.exitLastedEntity();
		}
		super.exitClass_definition(ctx);
	}

	//Function
	@Override
	public void enterFunction_definition(Function_definitionContext ctx) {
		context.foundMethodDeclarator(helper.getName(ctx), null, new ArrayList<>());
		if (ctx.function_definition_header().function_definition_params()!=null) {
			for ( Function_definition_paramContext param:ctx.function_definition_header().function_definition_params().function_definition_param()) {
				VarEntity var = new VarEntity(helper.getName(param), null, context.currentFunction(), entityRepo.generateId());
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

	//Assign
	@Override
	public void enterExprBatchAssign(ExprBatchAssignContext ctx) {
		// TODO Auto-generated method stub
		super.enterExprBatchAssign(ctx);
	}
	@Override
	public void enterExprAssign(ExprAssignContext ctx) {
		// TODO Auto-generated method stub
		super.enterExprAssign(ctx);
	}

	//Call
	@Override
	public void enterPrimaryFunctionCall0(PrimaryFunctionCall0Context ctx) {
		String fname = this.helper.getName(ctx.function_name());
		List<Function_call_paramContext> parameters = ctx.func_call_parameters().function_call_param();
		Collection<String> params = helper.getAllArgName(ctx.func_call_parameters().function_call_param());
		context.processSpecialFuncCall(fname,params);
		super.enterPrimaryFunctionCall0(ctx);
	}

	@Override
	public void enterExprFunctionCall1(ExprFunctionCall1Context ctx) {
		String fname = this.helper.getName(ctx.function_name());
		
		Collection<String> params = helper.getAllArgName(ctx.func_call_parameters_no_bracket().function_call_param());
		context.processSpecialFuncCall(fname,params);
		super.enterExprFunctionCall1(ctx);
	}
	@Override
	public void enterExprFunctionCall2(ExprFunctionCall2Context ctx) {
		// TODO Auto-generated method stub
		super.enterExprFunctionCall2(ctx);
	}
	@Override
	public void enterExprFunctionCall3(ExprFunctionCall3Context ctx) {
		// TODO Auto-generated method stub
		super.enterExprFunctionCall3(ctx);
	}
	//Yield(Call)
	@Override
	public void enterPrimaryStatementYield(PrimaryStatementYieldContext ctx) {
		// TODO Auto-generated method stub
		super.enterPrimaryStatementYield(ctx);
	}

	//Return
	@Override
	public void enterPrimaryStatementReturn(PrimaryStatementReturnContext ctx) {
		// TODO Auto-generated method stub
		super.enterPrimaryStatementReturn(ctx);
	}

	//Throw
	@Override
	public void enterPrimaryStatementRaise(PrimaryStatementRaiseContext ctx) {
		// TODO Auto-generated method stub
		super.enterPrimaryStatementRaise(ctx);
	}
}
