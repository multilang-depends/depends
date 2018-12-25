package depends.format.path;

import static org.junit.Assert.*;

import org.junit.Test;

public class DotPathFilenameWritterTest {

	@Test
	public void testRewriteFilename() {
		DotPathFilenameWritter r = new DotPathFilenameWritter();
		assertEquals(".abc.123_cpp",r.reWrite("/abc/123.cpp"));
		assertEquals("..abc.123_cpp",r.reWrite("./abc/123.cpp"));
		assertEquals("..abc.123.1_cpp",r.reWrite("./abc/123.1.cpp"));
	}

}
