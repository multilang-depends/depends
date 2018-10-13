package depends.extractor.cpp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import depends.entity.IdGenerator;
import depends.entity.repo.EntityRepo;
import depends.entity.types.VarEntity;
import depends.extractor.HandlerContext;
import depends.javaextractor.CPP14BaseListener;
import depends.javaextractor.CPP14Parser.ClassheadContext;
import depends.javaextractor.CPP14Parser.ClassspecifierContext;
import depends.javaextractor.CPP14Parser.DeclaratoridContext;
import depends.javaextractor.CPP14Parser.FunctiondefinitionContext;
import depends.javaextractor.CPP14Parser.IdexpressionContext;
import depends.javaextractor.CPP14Parser.NoptrdeclaratorContext;

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
//		String functionName = "";
//		if (ctx.declarator().ptrdeclarator()!=null) {
//			if (ctx.declarator().ptrdeclarator().noptrdeclarator()!=null) {
//				functionName = getFunctionName(ctx.declarator().ptrdeclarator().noptrdeclarator());
//			}
//		}
//		if (ctx.declarator().noptrdeclarator()!=null) {
//			functionName = getFunctionName(ctx.declarator().ptrdeclarator().noptrdeclarator());
//		}
//		Collection<VarEntity> parameters = new ArrayList<>();
//		String returnType = "void";
//		List<String> throwedType = new ArrayList<>();
//		context.foundMethodDeclarator(functionName,
//				parameters, returnType, throwedType);

		super.enterFunctiondefinition(ctx);
	}
	
	@Override
	public void enterDeclaratorid(DeclaratoridContext ctx) {
		IdexpressionContext id = ctx.idexpression();
		if (id.unqualifiedid()!=null) {
			Collection<VarEntity> parameters = new ArrayList<>();
			String returnType = "void";
			List<String> throwedType = new ArrayList<>();
			context.foundMethodDeclarator(id.unqualifiedid().Identifier().getText(),
					parameters, returnType, throwedType);		
		}		
		super.enterDeclaratorid(ctx);
	}
	private String getFunctionName(NoptrdeclaratorContext ctx) {
		System.out.println(ctx.getText());
		if (ctx.declaratorid()!=null) {
			IdexpressionContext id = ctx.declaratorid().idexpression();
			if (id.unqualifiedid()!=null) {

				return id.unqualifiedid().Identifier().getText();
			}
		}		
		return "";
	}
	@Override
	public void exitFunctiondefinition(FunctiondefinitionContext ctx)  {
		context.exitLastedEntity();
		super.exitFunctiondefinition(ctx);
	}
	
}

