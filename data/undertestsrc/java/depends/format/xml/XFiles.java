package depends.format.xml;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;

public class XFiles {

    private ArrayList<String> files;

    public ArrayList<String> getFiles() {
        return files;
    }

    @XmlElement(name = "variable")
    public void setFiles(ArrayList<String> files) {
        this.files = files;
    }
}
