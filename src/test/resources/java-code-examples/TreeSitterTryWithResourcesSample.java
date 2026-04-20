package ts;

class TSResource implements AutoCloseable {
    void ping() {
    }

    @Override
    public void close() {
    }
}

public class TreeSitterTryWithResourcesSample {
    void test() throws Exception {
        try (TSResource r = new TSResource()) {
            r.ping();
        }
    }
}
