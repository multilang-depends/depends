package ts;

class TSIncompleteDep {
    void ping() {
    }
}

public class TreeSitterIncompleteSample {
    void broken() {
        TSIncompleteDep dep = new TSIncompleteDep();
        dep.ping();
        if (dep != null) {
            dep.ping()
