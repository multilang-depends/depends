package depends.extractor.cpp.g4;

import org.antlr.v4.runtime.CommonTokenStream;

import depends.entity.IdGenerator;
import depends.entity.repo.EntityRepo;
import depends.extractor.HandlerContext;
import depends.extractor.cpp.g4elsa.helper.FunctionDefinitionContextHelper;
import depends.javaextractor.CElsaBaseListener;
import depends.javaextractor.CElsaParser.FunctionDefinitionContext;

public class CppEntitiesListener extends CElsaBaseListener {
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
	public void enterFunctionDefinition(FunctionDefinitionContext ctx) {
		FunctionDefinitionContextHelper helper = new FunctionDefinitionContextHelper(ctx);
		System.out.println(helper.getFunctionName());
		System.out.println(helper.getFunctionDocComments());
		super.enterFunctionDefinition(ctx);
	}
	
	
	
	
}

