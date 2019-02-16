package depends.addons;

import org.junit.Test;

public class DV8MappingFileBuilderTest {

	@Test
	public void test() {
		DV8MappingFileBuilder b = new DV8MappingFileBuilder();
		b.create("/tmp/depends-dv8-mapping.json");
	}

}
