package depends.extractor.cpp.g4;

import org.antlr.v4.runtime.CommonTokenStream;

import depends.entity.IdGenerator;
import depends.entity.repo.EntityRepo;
import depends.extractor.HandlerContext;
import depends.extractor.cpp.cdt.CppHandlerContext;
import depends.extractor.cpp.g4cpp14.helper.CommentsUtil;
import depends.extractor.cpp.g4elsa.helper.FunctionDefinitionContextHelper;
import depends.extractor.cpp.g4elsa.helper.MemberDeclarationGeneralContextHelper;
import depends.extractor.cpp.g4elsa.helper.SimpleDeclarationContextHelper;
import depends.javaextractor.CElsaBaseListener;
import depends.javaextractor.CElsaParser.FunctionDefinitionContext;
import depends.javaextractor.CElsaParser.MemberDeclarationGeneralContext;
import depends.javaextractor.CElsaParser.SimpleDeclarationContext;

public class CppEntitiesListener extends CElsaBaseListener {
	private HandlerContext context;
	private IdGenerator idGenerator;
	private CommonTokenStream tokens;
	
	public CppEntitiesListener(CommonTokenStream tokens, String fileFullPath, EntityRepo entityRepo) {
		this.tokens = tokens;
		this.context = new CppHandlerContext(entityRepo);
		idGenerator = entityRepo;
		context.startFile(fileFullPath);
	}

	@Override
	public void enterFunctionDefinition(FunctionDefinitionContext ctx) {
		FunctionDefinitionContextHelper helper = new FunctionDefinitionContextHelper(ctx);
		System.out.println(helper.getFunctionName());
		System.out.println(helper.getReturnType());
		System.out.println(CommentsUtil.getLeadingComments(tokens,ctx));
		System.out.println(helper.getParameters());
		super.enterFunctionDefinition(ctx);
	}

	@Override
	public void enterSimpleDeclaration(SimpleDeclarationContext ctx) {
		SimpleDeclarationContextHelper helper = new SimpleDeclarationContextHelper(ctx);
		System.out.println(helper.getFunctionName());
		System.out.println(helper.getReturnType());
		System.out.println(CommentsUtil.getLeadingComments(tokens,ctx));
		System.out.println(helper.getParameters());
		super.enterSimpleDeclaration(ctx);
	}

	@Override
	public void enterMemberDeclarationGeneral(MemberDeclarationGeneralContext ctx) {
		MemberDeclarationGeneralContextHelper helper = new MemberDeclarationGeneralContextHelper(ctx);
		System.out.println(helper.getFunctionName());
		System.out.println(helper.getReturnType());
		System.out.println(helper.getParameters());
		System.out.println(CommentsUtil.getLeadingComments(tokens,ctx));
		super.enterMemberDeclarationGeneral(ctx);
	}
}

