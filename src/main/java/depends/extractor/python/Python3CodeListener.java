package depends.extractor.python;

import java.util.ArrayList;

import depends.entity.repo.EntityRepo;
import depends.extractor.python.Python3Parser.FuncdefContext;
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
	public void exitFuncdef(FuncdefContext ctx) {
		context.exitLastedEntity();
		super.exitFuncdef(ctx);
	}
	
}
