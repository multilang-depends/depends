package ts;

class TSSEA {
    static TSSEA make() {
        return new TSSEA();
    }

    TSSEA ping() {
        return this;
    }

    TSSEA pong() {
        return this;
    }
}

public class TreeSitterSwitchExpressionSample {
    TSSEA select(int kind) {
        return switch (kind) {
            case 1 -> TSSEA.make().ping();
            default -> {
                yield new TSSEA().pong();
            }
        };
    }
}
