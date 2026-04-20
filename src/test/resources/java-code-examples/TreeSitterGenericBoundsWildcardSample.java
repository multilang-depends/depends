package ts;

class TSGBase {
}

interface TSGMarker {
}

class TSGChild extends TSGBase implements TSGMarker {
}

public class TreeSitterGenericBoundsWildcardSample<T extends TSGBase & TSGMarker> {
    java.util.List<? extends TSGBase> up;
    java.util.List<? super TSGChild> down;
}
