package depends.extractor.kotlin;

import depends.entity.repo.EntityRepo;
import depends.extractor.kotlin.KotlinParser.ImportHeaderContext;
import depends.extractor.kotlin.KotlinParser.PackageHeaderContext;
import depends.extractor.kotlin.context.ExpressionUsage;
import depends.importtypes.ExactMatchImport;
import depends.relations.Inferer;

public class KotlinListener extends KotlinParserBaseListener {

	private KotlinHandlerContext context;
	private EntityRepo entityRepo;
	private ExpressionUsage expressionUsage;

	public KotlinListener(String fileFullPath, EntityRepo entityRepo, Inferer inferer) {
		this.context = new KotlinHandlerContext(entityRepo,inferer);
		this.entityRepo = entityRepo;
		this.expressionUsage = new ExpressionUsage(context,entityRepo);
		context.startFile(fileFullPath);
	}

	@Override
	public void enterPackageHeader(PackageHeaderContext ctx) {
		if (ctx.identifier()!=null) {
			context.foundNewPackage(ContextHelper.getName(ctx.identifier()));
		}
		super.enterPackageHeader(ctx);
	}

	@Override
	public void enterImportHeader(ImportHeaderContext ctx) {
		context.foundNewImport(new ExactMatchImport(ContextHelper.getName(ctx.identifier())));
		//TODO: alias of import
		if (ctx.importAlias()!=null) {
			
		}
		super.enterImportHeader(ctx);
	}
	

}
