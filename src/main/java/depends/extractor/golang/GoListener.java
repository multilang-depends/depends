package depends.extractor.golang;

import depends.entity.repo.EntityRepo;
import depends.relations.Inferer;

public class GoListener extends  GoParserBaseListener {
    GoHandlerContext context;
    @Override
    public void enterSourceFile(GoParser.SourceFileContext ctx) {
        super.enterSourceFile(ctx);
    }

    public GoListener(String fileFullPath, EntityRepo entityRepo, Inferer inferer) {
        context = new GoHandlerContext(entityRepo,inferer);
        context.startFile(fileFullPath);
    }


    public void done() {

    }
}
