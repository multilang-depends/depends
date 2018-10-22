package depends.extractor.cpp;

import depends.entity.IdGenerator;
import depends.entity.repo.EntityRepo;
import depends.extractor.HandlerContext;
import depends.javaextractor.CPP14BaseListener;
import depends.javaextractor.CPP14Parser.ClassheadContext;
import depends.javaextractor.CPP14Parser.ClassspecifierContext;
import depends.javaextractor.CPP14Parser.FunctiondefinitionContext;

public class CppEntitiesListener extends CPP14BaseListener {
	private HandlerContext context;
	private IdGenerator idGenerator;
	public CppEntitiesListener(String fileFullPath, EntityRepo entityRepo) {
		this.context = new HandlerContext(entityRepo);
		idGenerator = entityRepo;
		context.startFile(fileFullPath);
	}
	@Override
	public void enterClasshead(ClassheadContext ctx) {
		if (ctx.classheadname().classname().Identifier()!=null)
			context.foundNewType(ctx.classheadname().classname().Identifier().getText());
		super.enterClasshead(ctx);

		//TODO: 
		ctx.classheadname().classname().simpletemplateid();
	}
	@Override
	public void exitClassspecifier(ClassspecifierContext ctx) {
		context.exitLastedEntity();
		super.exitClassspecifier(ctx);
	}
	@Override
	public void enterFunctiondefinition(FunctiondefinitionContext ctx) {
		FunctiondefinitionContextHelper helper = new FunctiondefinitionContextHelper(ctx);
		context.foundMethodDeclarator(helper.getFunctionName(),
				helper.getParameters(),helper.getReturnType(), helper.getThrowedType());
		super.enterFunctiondefinition(ctx);
	}
	
	@Override
	public void exitFunctiondefinition(FunctiondefinitionContext ctx)  {
		context.exitLastedEntity();
		super.exitFunctiondefinition(ctx);
	}
	
}

