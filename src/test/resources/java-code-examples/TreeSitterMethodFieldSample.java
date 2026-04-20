package ts;

public class TreeSitterMethodFieldSample {
    Integer x;
    public String y, z;

    public TreeSitterMethodFieldSample(Integer init) {
        this.x = init;
    }

    public Integer sum(Integer p1, Integer p2) throws Exception {
        return p1 + p2;
    }
}
