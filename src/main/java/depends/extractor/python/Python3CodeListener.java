package depends.extractor.python;

import java.util.ArrayList;

import depends.entity.TypeEntity;
import depends.entity.VarEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.python.Python3Parser.ClassdefContext;
import depends.extractor.python.Python3Parser.FuncdefContext;
import depends.extractor.python.Python3Parser.TfpdefContext;
import depends.relations.Inferer;

public class Python3CodeListener extends Python3BaseListener {

	private PythonHandlerContext context;
	private PythonParserHelper helper = PythonParserHelper.getInst();
	private ExpressionUsage expressionUsage;

	public Python3CodeListener(String fileFullPath, EntityRepo entityRepo, Inferer inferer) {
		this.context = new PythonHandlerContext(entityRepo,inferer);
		this.expressionUsage = new ExpressionUsage();
		context.startFile(fileFullPath);
	}

	@Override
	public void enterFuncdef(FuncdefContext ctx) {
        String functionName = ctx.NAME().getText();
        context.foundMethodDeclarator(functionName, null, new ArrayList<>());
        super.enterFuncdef(ctx);
	}
	
	

	@Override
	public void enterTfpdef(TfpdefContext ctx) {
		String paramName = ctx.NAME().getText();
		context.addMethodParameter(paramName);
		super.enterTfpdef(ctx);
	}

	@Override
	public void exitFuncdef(FuncdefContext ctx) {
		context.exitLastedEntity();
		super.exitFuncdef(ctx);
	}

	@Override
	public void enterClassdef(ClassdefContext ctx) {
		String name = ctx.NAME().getText();
		TypeEntity type = context.foundNewType(name);
		ArrayList<String> baseClasses = this.helper.getBaseClassList(ctx.arglist());
		baseClasses.forEach(base->type.addExtends(base));
		
		super.enterClassdef(ctx);
	}
	
	@Override
	public void exitClassdef(ClassdefContext ctx) {
		context.exitLastedEntity();
		super.exitClassdef(ctx);
	}
	
	
	
}
