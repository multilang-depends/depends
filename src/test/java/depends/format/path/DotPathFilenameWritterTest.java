package depends.format.path;

import static org.junit.Assert.*;

import org.junit.Test;

public class DotPathFilenameWritterTest {

	@Test
	public void testRewriteFilename() {
		DotPathFilenameWritter r = new DotPathFilenameWritter();
		assertEquals("abc.123_cpp",r.reWrite("/abc/123.cpp"));
		assertEquals(".abc.123_cpp",r.reWrite("./abc/123.cpp"));
		assertEquals(".abc.123_1_cpp",r.reWrite("./abc/123.1.cpp"));
		assertEquals("abc.123_cpp",r.reWrite("\\abc\\123.cpp"));
		assertEquals("a_b_c.123_cpp",r.reWrite("\\a.b.c\\123.cpp"));
	}

}
