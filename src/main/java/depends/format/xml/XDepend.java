package depends.format.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "depend")
public class XDepend {

    private String name;

    private float weight;

    public String getName() {
        return name;
    }

    @XmlAttribute(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    public float getWeight() {
        return weight;
    }

    @XmlAttribute(name = "weight")
    public void setWeight(float weight) {
        this.weight = weight;
    }
}
