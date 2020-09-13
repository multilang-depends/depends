package depends.extractor.golang;

import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class FieldDeclHelper {
    private String string;
    private String typeName = null;
    List<String> identifiers = null;
    private String anonymousField = null;

    public FieldDeclHelper(GoParser.FieldDeclContext ctx) {
        if (ctx.identifierList()!=null){
            identifiers = new ArrayList<>();
            for (TerminalNode identifier:ctx.identifierList().IDENTIFIER()){
                this.identifiers.add(identifier.getText());
            }

        }
        if (ctx.type_()!=null){
            TypeDefHelper typeDefHelper = new TypeDefHelper(ctx.type_());
            if (typeDefHelper.isTypeRef()){
                this.typeName = typeDefHelper.getTypeRefName();
            }
            else /*define new one*/{
                //TOOD:
            }
        }
        if (ctx.string_()!=null){
            string = ctx.string_().getText();
        }
        if (ctx.anonymousField()!=null) {
            anonymousField = ctx.anonymousField().getText();
        }
    }

    public List<String> getIdentifiers() {
        return this.identifiers;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getAnonymousField() {
        return anonymousField;
    }

    public String getString() {
        return this.string;
    }
}
