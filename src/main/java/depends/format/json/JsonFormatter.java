package depends.format.json;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonFormatter {

    public void toJson(JDepObject depObject, String jsonFileName) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(jsonFileName), depObject);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
