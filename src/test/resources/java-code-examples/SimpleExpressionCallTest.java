public class SimpleExpressionCallTest {
    public SimpleExpressionCallTest foo(){
        return this;
    }
    
    public SimpleExpressionCallTest bar(){
        _this = this;
        _this[0].foo().bar().foo();
        return this;
    }
}
