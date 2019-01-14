package depends.format.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;

public class XCell {
    private int src;
    private int dest;
    private ArrayList<XDepend> depends;

    @XmlAttribute(name = "src")
    public void setSrc(int src) {
        this.src = src;
    }

    public int getSrc() {
        return src;
    }

    @XmlAttribute(name = "dest")
    public void setDest(int dest) {
        this.dest = dest;
    }

    public int getDest() {
        return dest;
    }

    @XmlElement(name = "depend")
    public void setDepends(ArrayList<XDepend> depends) {
        this.depends = depends;
    }

    public ArrayList<XDepend> getDepends() {
        return depends;
    }
}
