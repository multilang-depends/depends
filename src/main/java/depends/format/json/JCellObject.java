package depends.format.json;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@XmlRootElement(name = "cell")
public class JCellObject {
    private int src;
    private int dest;
    private Map<String, Float> values;

    public int getSrc() {
        return src;
    }

    @XmlAttribute(name = "src")
    public void setSrc(int src) {
        this.src = src;
    }

    public int getDest() {
        return dest;
    }

    @XmlAttribute(name = "dest")
    public void setDest(int dest) {
        this.dest = dest;
    }

    public void setValues(Map<String, Float> values) {
        this.values = values;
    }

    @XmlElement
    public Map<String, Float> getValues() {
        return values;
    }
}
