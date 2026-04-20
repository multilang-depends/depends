package ts;

class TSExprA {
    void foo() {
    }
}

public class TreeSitterExpressionSample {
    void test(Object in) {
        TSExprA a = new TSExprA();
        a.foo();
        TSExprA b = (TSExprA) in;
        b = new TSExprA();
    }
}
