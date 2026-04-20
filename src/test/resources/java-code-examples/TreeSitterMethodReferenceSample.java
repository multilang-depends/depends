package ts;

interface TSRefRunnable {
    void run();
}

interface TSRefConsumer {
    void accept(TSRefTarget target);
}

class TSRefTarget {
    static void staticPing() {
    }

    void instancePing() {
    }
}

public class TreeSitterMethodReferenceSample {
    void test() {
        TSRefRunnable r = TSRefTarget::staticPing;
        TSRefConsumer c = TSRefTarget::instancePing;
        r.run();
        c.accept(new TSRefTarget());
    }
}
