package depends.format.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "matrix")
public class XDepObject {
    private String schemaVersion;
    private String name;
    private XFiles variables;
    private XCells cells;

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

    @XmlElement(name = "variables")
    public void setVariables(XFiles variables) {
        this.variables = variables;
    }

    public XFiles getVariables() {
        return variables;
    }

    @XmlElement(name = "cells")
    public void setCells(XCells cells) {
        this.cells = cells;
    }

    public XCells getCells() {
        return cells;
    }
}
