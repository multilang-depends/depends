package depends.format.path;

import static org.junit.Assert.*;

import org.junit.Test;

public class WindowsPathFilenameWritterTest {

	@Test
	public void testRewriteFilename() {
		WindowsPathFilenameWritter r = new WindowsPathFilenameWritter();
		assertEquals("\\abc\\123.cpp",r.reWrite("/abc/123.cpp"));
		assertEquals("\\abc\\123.cpp",r.reWrite("\\abc\\123.cpp"));
	}

}
