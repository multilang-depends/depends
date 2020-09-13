package depends.extractor.golang;

public class TypeDefHelper {
    private GoParser.Type_Context type;
    public TypeDefHelper(GoParser.Type_Context type_context) {
        this.type = type_context;
        //go to deepest type definition
        while(type.type_()!=null){
            this.type = this.type.type_();
        }
    }

    public boolean isTypeRef(){
        return type.typeName()!=null;
    }

    public String getTypeRefName(){
        if (!isTypeRef()) return null;
        return type.typeName().getText();
    }

    public boolean isStruct(){
        if (type.typeLit()==null) return false;
        if (type.typeLit().structType()!=null) return true;
        return false;
    }
}

