package ts;

interface TSLambdaConsumer {
    void apply(TSLambdaTarget target);
}

class TSLambdaTarget {
    void ping() {
    }
}

public class TreeSitterLambdaSample {
    void test() {
        TSLambdaConsumer consumer = (TSLambdaTarget target) -> target.ping();
        consumer.apply(new TSLambdaTarget());
    }
}
