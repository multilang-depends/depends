package depends.format.xml;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;

public class XCells {
    private ArrayList<XCell> cells;

    @XmlElement(name = "cell")
    public void setCells(ArrayList<XCell> cells) {
        this.cells = cells;
    }

    public ArrayList<XCell> getCells() {
        return cells;
    }
}
