package ts;

class TSArgs {
    TSArgs bar() {
        return this;
    }

    TSArgs baz() {
        return this;
    }
}

public class TreeSitterCallInArgumentsSample {
    void sink(TSArgs value) {
    }

    void test() {
        sink(helper().bar().baz());
    }

    TSArgs helper() {
        return new TSArgs();
    }
}
