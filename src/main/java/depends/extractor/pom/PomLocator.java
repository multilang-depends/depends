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

package depends.extractor.pom;

import java.io.File;
import java.util.List;

import multilang.depends.util.file.FileUtil;

public class PomLocator {

	private List<String> includePaths;
	private PomParent pomParent;

	public PomLocator(List<String> includePaths, PomParent pomParent) {
		this.includePaths = includePaths;
		this.pomParent = pomParent;
	}

	public String getLocation() {
		StringBuilder sb = new StringBuilder();
		sb.append(pomParent.groupId.replace(".", File.separator));
		sb.append(File.separator);
		sb.append(pomParent.artifactId);
		sb.append(File.separator);
		sb.append(pomParent.version);
		sb.append(File.separator);
		sb.append(pomParent.artifactId);
		sb.append("-");
		sb.append(pomParent.version);
		sb.append(".pom");
		for (String includePath:includePaths) {
			String path = includePath+File.separator+sb.toString();
			if (FileUtil.existFile(path)) {
				return FileUtil.uniqFilePath(path);
			}
		}
		return null;
	}

}
