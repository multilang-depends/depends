package depends.format.xml;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class XmlFormatter {

    public void toXml(XDepObject xDepObject, String xmlFileName)  {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(XDepObject.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(xDepObject, new FileOutputStream(xmlFileName));
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
