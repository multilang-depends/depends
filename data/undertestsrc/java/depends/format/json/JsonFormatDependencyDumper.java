package depends.format.json;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

import depends.format.AbstractFormatDependencyDumper;
import depends.format.FileAttributes;
import depends.matrix.DependencyMatrix;

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
