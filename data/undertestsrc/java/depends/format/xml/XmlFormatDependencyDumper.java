package depends.format.xml;

import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import depends.format.AbstractFormatDependencyDumper;
import depends.format.FileAttributes;
import depends.matrix.DependencyMatrix;

public class XmlFormatDependencyDumper extends AbstractFormatDependencyDumper{
	@Override
	public String getFormatName() {
		return "xml";
	}
    public XmlFormatDependencyDumper(DependencyMatrix dependencyMatrix, String projectName, String outputDir) {
		super(dependencyMatrix,projectName,outputDir);
	}

	private void toXml(XDepObject xDepObject, String xmlFileName)  {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(XDepObject.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(xDepObject, new FileOutputStream(xmlFileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@Override
	public boolean output() {
        XDataBuilder xBuilder = new XDataBuilder();
        XDepObject xDepObject = xBuilder.build(matrix,new FileAttributes(name));
        toXml(xDepObject,composeFilename()+".xml");
 		return true;
	}
}
