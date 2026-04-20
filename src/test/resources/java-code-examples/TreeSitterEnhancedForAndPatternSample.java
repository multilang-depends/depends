package ts;

import java.util.List;

class TSLoopItem {
    void ping() {
    }

    void pong() {
    }
}

public class TreeSitterEnhancedForAndPatternSample {
    void run(List<TSLoopItem> items, Object value) {
        for (TSLoopItem item : items) {
            item.ping();
        }
        if (value instanceof TSLoopItem matched) {
            matched.pong();
        }
    }
}
