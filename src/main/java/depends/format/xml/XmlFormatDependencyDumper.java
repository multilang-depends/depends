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

import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import depends.format.AbstractFormatDependencyDumper;
import depends.format.FileAttributes;
import depends.matrix.core.DependencyMatrix;

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
