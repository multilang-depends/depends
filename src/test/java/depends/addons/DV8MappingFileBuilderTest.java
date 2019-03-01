package depends.addons;

import java.util.ArrayList;

import org.junit.Test;

public class DV8MappingFileBuilderTest {

	@Test
	public void test() {
		DV8MappingFileBuilder b = new DV8MappingFileBuilder(new ArrayList<>());
		b.create("/tmp/depends-dv8-mapping.json");
	}

}
