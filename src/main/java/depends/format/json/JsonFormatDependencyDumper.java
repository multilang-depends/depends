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

package depends.format.json;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

import depends.format.AbstractFormatDependencyDumper;
import depends.format.FileAttributes;
import depends.matrix.core.DependencyMatrix;

public class JsonFormatDependencyDumper extends AbstractFormatDependencyDumper {
	@Override
	public String getFormatName() {
		return "json";
	}

	public JsonFormatDependencyDumper(DependencyMatrix dependencyMatrix, String projectName, String outputDir) {
		super(dependencyMatrix, projectName,outputDir);
	}

	@Override
	public boolean output() {
		JDataBuilder jBuilder = new JDataBuilder();
		JDepObject jDepObject = jBuilder.build(matrix, new FileAttributes(name));
		toJson(jDepObject, composeFilename()+ ".json");
		return true;
	}

	

	private void toJson(JDepObject depObject, String jsonFileName) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File(jsonFileName), depObject);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
