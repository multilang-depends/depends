package ts;

interface TSAnonAction {
    void run();
}

public class TreeSitterAnonymousInnerClassSample {
    void assist() {
    }

    void test() {
        TSAnonAction action = new TSAnonAction() {
            @Override
            public void run() {
                assist();
            }
        };
        action.run();
    }
}
