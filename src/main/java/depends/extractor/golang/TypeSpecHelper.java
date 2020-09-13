package depends.extractor.golang;

public class TypeSpecHelper {
    private final GoParser.TypeSpecContext ctx;
    private final TypeDefHelper typeDefHelper;
    public TypeSpecHelper(GoParser.TypeSpecContext ctx) {
        this.ctx = ctx;
        this.typeDefHelper = new TypeDefHelper(ctx.type_());
    }

    public String getIdentifier(){
        return  ctx.IDENTIFIER().getText();
    }

    public TypeDefHelper getTypeDefHelper(){
        return typeDefHelper;
    }
}
