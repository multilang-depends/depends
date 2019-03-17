package depends.extractor.python;

import java.util.ArrayList;

import depends.entity.DecoratedEntity;
import depends.entity.Entity;
import depends.entity.TypeEntity;
import depends.entity.VarEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.python.Python3Parser.ClassdefContext;
import depends.extractor.python.Python3Parser.DecoratedContext;
import depends.extractor.python.Python3Parser.DecoratorContext;
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
		ArrayList<String> baseClasses = this.helper.getArgList(ctx.arglist());
		baseClasses.forEach(base->type.addExtends(base));
		
		super.enterClassdef(ctx);
	}
	
	@Override
	public void exitClassdef(ClassdefContext ctx) {
		context.exitLastedEntity();
		super.exitClassdef(ctx);
	}
	
    @Override
    public void exitDecorated(DecoratedContext ctx) {
    	String name = helper.getDecoratedElementName(ctx);
    	if (name==null) {
            super.exitDecorated(ctx);
    		return;
    	}
		Entity entity = context.foundEntityWithName(name);
		if (entity instanceof DecoratedEntity) {
	    	for (DecoratorContext decorator:ctx.decorators().decorator()){
		        ((DecoratedEntity)entity).addAnnotation(decorator.dotted_name().getText());
		    	//TODO: Annotation parameters  helper.getArgList(decorator.arglist()));
	    	}
		}
        super.exitDecorated(ctx);
    }

	
}


//gloal statement
//nonlocal statement
//expr_stmt: testlist_star_expr (annassign | augassign (yield_expr|testlist) |
//('=' (yield_expr|testlist_star_expr))*);
//return_stmt: 'return' (testlist)?;
//raise_stmt: 'raise' (test ('from' test)?)?;

// Question : in wuxia's  impl, why should we add __init__ method? -- is there any special purpose of init?
// Is python package must include a __init__.py?
// How to identify import relation in python?


//expr_stmt: testlist_star_expr 
//( - );
//
//annassign 
//| augassign (yield_expr|testlist) 
//| ('=' (yield_expr|testlist_star_expr))*

