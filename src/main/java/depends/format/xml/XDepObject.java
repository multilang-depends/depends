/*
MIT License

Copyright (c) 2018-2019 Gang ZHANG

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package depends.format.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name = "matrix")
@XmlType(propOrder = {"variables", "cells"})
public class XDepObject {
    private String schemaVersion;
    private String name;
    private XFiles variables;
    private XCells cells;
    private String xmlns="http://dv8.archdia.com/xml/matrix";

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
    



	public String getXmlns() {
		return xmlns;
	}

    @XmlAttribute(name = "xmlns")
	public void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}
}
