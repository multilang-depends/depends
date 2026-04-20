package ts;

class TSChain {
    TSChain foo() {
        return this;
    }

    TSChain bar() {
        return this;
    }

    void baz() {
    }
}

public class TreeSitterCallChainSample {
    void test() {
        this.helper().foo().bar().baz();
    }

    TSChain helper() {
        return new TSChain();
    }
}
