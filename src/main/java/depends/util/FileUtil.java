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

package depends.util;

import java.io.File;
import java.io.IOException;
import java.util.Stack;

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

	public static String getLocatedDir(String filepath) {
		File file = new File(filepath);
		if (!file.exists()) return null;
		return file.getParent();
	}

	public static String getShortFileName(String path) {
		return new File(path).getName();
	}
	
	public static String uniformPath(String path) {
		String[] paths = path.split("[/|\\\\]");
		StringBuilder sb = new StringBuilder();
		Stack<String> pathStack = new Stack<>();
		for (int i=0;i<paths.length;i++) {
			String s = paths[i];
			if (s.equals(".")) continue;
			if (s.equals("..") && !pathStack.empty()) {
				pathStack.pop();
				continue;
			}
			pathStack.push(s);
		}
		
		for (int i=0;i<pathStack.size();i++) {
			sb.append(pathStack.get(i));
			if (i<pathStack.size()-1)
				sb.append(File.separator);
		}
		return sb.toString();
	}
}
