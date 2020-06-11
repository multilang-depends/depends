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

import depends.entity.GenericName;
import multilang.depends.util.file.FileUtil;

public class PomCoords  {
	public PomCoords() {
	}

	public String groupId = "";
	public String artifactId = "";
	public String version = "";
	public String getPath() {
		return groupId+"."+artifactId+"_" +version+"_" ;
	}
	public void fillFromIfNull(PomParent pomParent) {
		if (groupId=="") groupId = pomParent.groupId;
		if (artifactId=="") artifactId = pomParent.artifactId;
		if (version=="") version = pomParent.version;
	}
	public GenericName getGenericNamePath() {
		return new GenericName(getPath());
	}
	public void sureFillVersion(List<String> includePaths) {
		if (version!="") return;
		StringBuilder sb = new StringBuilder();
		sb.append(this.groupId.replace(".", File.separator));
		sb.append(File.separator);
		sb.append(this.artifactId);
		sb.append(File.separator);
		
		for (String includePath:includePaths) {
			String path = includePath+File.separator+sb.toString();
			if (FileUtil.existFile(path)) {
				File f = new File(path);
				String max = "";
				for (String d:f.list()) {
					if (d.compareTo(max)>0)
						max = d;
				}
				version = max;
			}
		}
	}

}
