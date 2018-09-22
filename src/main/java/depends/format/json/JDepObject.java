package depends.format.json;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "matrix")
public class JDepObject {
    private String schemaVersion;
    private String name;
    private ArrayList<String> variables;
    private ArrayList<JCellObject> cells;

    public String getName() {
        return name;
    }

    @XmlAttribute(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    public String getSchemaVersion() {
        return schemaVersion;
    }

    @XmlAttribute(name = "schema-version")
    public void setSchemaVersion(String schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public ArrayList<String> getVariables() {
        return variables;
    }

    @XmlElement
    public void setVariables(ArrayList<String> variables) {
        this.variables = variables;
    }

    public ArrayList<JCellObject> getCells() {
        return cells;
    }

    @XmlElement
    public void setCells(ArrayList<JCellObject> cells) {
        this.cells = cells;
    }
}
