package depends.util;

import java.io.File;
import java.io.IOException;

public class FileUtil {
	public static String uniqFilePath(String filePath) {
		try {
			File f = new File(filePath);
			filePath = f.getCanonicalPath();
		} catch (IOException e) {
		}
		return filePath;
	}

	public static boolean existFile(String path) {
		return new File(path).exists();
	}

	public static boolean isDirectory(String path) {
		return new File(path).isDirectory();
	}
}
