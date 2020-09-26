package depends.extractor.golang;

import depends.entity.FunctionEntity;
import depends.entity.GenericName;
import depends.entity.VarEntity;
import depends.entity.repo.EntityRepo;
import depends.relations.Inferer;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;

public class GoListener extends  GoParserBaseListener {
    private final EntityRepo entityRepo;
    GoHandlerContext context;
    @Override
    public void enterSourceFile(GoParser.SourceFileContext ctx) {
        super.enterSourceFile(ctx);
    }

    public GoListener(String fileFullPath, EntityRepo entityRepo, Inferer inferer) {
        context = new GoHandlerContext(entityRepo,inferer);
        context.startFile(fileFullPath);
        this.entityRepo = entityRepo;
    }

    @Override
    public void enterFunctionDecl(GoParser.FunctionDeclContext ctx) {
        String funcName = ctx.IDENTIFIER().getText();
        context.foundMethodDeclarator(funcName,ctx.getStart().getLine());
        foundFuncSignature(ctx.signature());
        super.enterFunctionDecl(ctx);
    }

    @Override
    public void exitFunctionDecl(GoParser.FunctionDeclContext ctx) {
        context.exitLastedEntity();
        super.exitFunctionDecl(ctx);
    }

    @Override
    public void enterPackageClause(GoParser.PackageClauseContext ctx) {
        context.foundPackageDeclaration(ctx.IDENTIFIER().getText());
        super.enterPackageClause(ctx);
    }

    private void foundFuncSignature(GoParser.SignatureContext signature) {
        FunctionEntity func = (FunctionEntity) context.lastContainer();
        if (signature.parameters()!=null) {
            for (GoParser.ParameterDeclContext param : signature.parameters().parameterDecl()) {
                if (param.identifierList() != null) {
                    TypeDefHelper typeDefHelper = new TypeDefHelper(param.type_());
                    for (TerminalNode id : param.identifierList().IDENTIFIER()) {
                        VarEntity varEntity = new VarEntity(GenericName.build(id.getText()),
                                GenericName.build(typeDefHelper.getTypeRefName()), context.lastContainer(),
                                entityRepo.generateId());
                        func.addParameter(varEntity);
                    }
                } else/* with ... parameters*/ {

                }
            }
        }
        if (signature.result()!=null){
            if(signature.result().parameters()!=null){
                for (GoParser.ParameterDeclContext paramDecl:signature.result().parameters().parameterDecl()){
                    TypeDefHelper typeDefHelper = new TypeDefHelper(paramDecl.type_());
                    if (typeDefHelper.isTypeRef()) {
                        func.addReturnType(GenericName.build(typeDefHelper.getTypeRefName()));
                    }else{
                        System.err.println("TODO: unsupport return type");
                    }
                }
            }
            if (signature.result().type_()!=null){
                TypeDefHelper typeDefHelper = new TypeDefHelper(signature.result().type_());
                if (typeDefHelper.isTypeRef()) {
                    func.addReturnType(GenericName.build(typeDefHelper.getTypeRefName()));
                }else{
                    System.err.println("TODO: unsupport return type");
                }
            }
            System.err.println(signature.result().getText());
        }
    }

    @Override
    public void enterTypeSpec(GoParser.TypeSpecContext ctx) {
        TypeSpecHelper specHelper = new TypeSpecHelper(ctx);
        if (specHelper.getTypeDefHelper().isTypeRef()){
            context.foundNewAlias(specHelper.getIdentifier(),specHelper.getTypeDefHelper().getTypeRefName());
        }else if (specHelper.getTypeDefHelper().isStruct()){
            context.foundNewType(specHelper.getIdentifier(),ctx.getStart().getLine());
        }
        super.enterTypeSpec(ctx);
    }

    @Override
    public void exitTypeSpec(GoParser.TypeSpecContext ctx) {
        TypeSpecHelper specHelper = new TypeSpecHelper(ctx);
        if (specHelper.getTypeDefHelper().isStruct()){
            context.exitLastedEntity();
        }
        super.exitTypeSpec(ctx);
    }

//    fieldDecl
//    : ({noTerminatorBetween(2)}? identifierList type_ | anonymousField) string_?
//    ;

    @Override
    public void enterFieldDecl(GoParser.FieldDeclContext ctx) {
        FieldDeclHelper fieldDeclHelper = new FieldDeclHelper(ctx);
        String typeName = fieldDeclHelper.getTypeName();

        if (fieldDeclHelper.getIdentifiers()!=null){
            for (String id:fieldDeclHelper.getIdentifiers()){
                if (typeName==null){
                    System.err.println("TODO: unsupport fieldDecl without type");
                }
                context.foundVarDefinition(id, GenericName.build(typeName),new ArrayList<>(),ctx.getStart().getLine());
            }
        }

        if (fieldDeclHelper.getAnonymousField()!=null){
            System.err.println("TODO: unsupport anonymousField");
        }
        if (fieldDeclHelper.getString()!=null){
            System.err.println("TODO: unsupport field with string " + fieldDeclHelper.getString());
        }
        super.enterFieldDecl(ctx);
    }

    public void done() {

    }
}
