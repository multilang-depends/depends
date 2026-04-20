package ts;

class TSBase {
    void ping() {
    }
}

public class TreeSitterThisSuperSample extends TSBase {
    void local() {
    }

    void test() {
        this.local();
        super.ping();
    }
}
