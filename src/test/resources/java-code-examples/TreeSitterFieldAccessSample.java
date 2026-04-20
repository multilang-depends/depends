package ts;

class TSAccessHolder {
    int value;
}

public class TreeSitterFieldAccessSample {
    int localField;
    TSAccessHolder holder;

    void test() {
        this.localField = 1;
        holder.value = this.localField;
        int x = holder.value;
    }
}
