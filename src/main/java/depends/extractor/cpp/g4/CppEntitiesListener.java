package depends.extractor.cpp.g4;

import java.util.List;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

import depends.entity.IdGenerator;
import depends.entity.repo.EntityRepo;
import depends.entity.types.FunctionEntity;
import depends.extractor.HandlerContext;
import depends.javaextractor.CPP14BaseListener;
import depends.javaextractor.CPP14Lexer;
import depends.javaextractor.CPP14Parser.ClassheadContext;
import depends.javaextractor.CPP14Parser.ClassspecifierContext;
import depends.javaextractor.CPP14Parser.FunctiondefinitionContext;

public class CppEntitiesListener extends CPP14BaseListener {
	private HandlerContext context;
	private IdGenerator idGenerator;
	private CommonTokenStream tokens;
	
	public CppEntitiesListener(CommonTokenStream tokens, String fileFullPath, EntityRepo entityRepo) {
		this.tokens = tokens;
		this.context = new HandlerContext(entityRepo);
		idGenerator = entityRepo;
		context.startFile(fileFullPath);
	}
	@Override
	public void enterClasshead(ClassheadContext ctx) {
		if (ctx.classheadname().classname().Identifier()!=null)
			context.foundNewType(ctx.classheadname().classname().Identifier().getText());
		super.enterClasshead(ctx);
	}
	@Override
	public void exitClassspecifier(ClassspecifierContext ctx) {
//		context.exitLastedEntity();
		Token token = ctx.getStart();
		int i = token.getTokenIndex();
		System.out.println(ctx.getText());
		List<Token> bc = tokens.getHiddenTokensToLeft(i,CPP14Lexer.HIDDEN);
		if (bc!=null) {
			for (Token cmt:bc) {
				System.out.println(cmt.getText());
			}
		}
		super.exitClassspecifier(ctx);
	}
	@Override
	public void enterFunctiondefinition(FunctiondefinitionContext ctx) {
//		FunctiondefinitionContextHelper helper = new FunctiondefinitionContextHelper(ctx);
//		FunctionEntity function = context.foundMethodDeclarator(helper.getFunctionName(),
//				helper.getReturnType(), helper.getThrowedType());
//		helper.addParameters(function);
		super.enterFunctiondefinition(ctx);
	}
	
	@Override
	public void exitFunctiondefinition(FunctiondefinitionContext ctx)  {
//		context.exitLastedEntity();
		super.exitFunctiondefinition(ctx);
	}
	
	
	
}

