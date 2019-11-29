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

import java.util.Collection;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.GenericName;
import depends.entity.TypeEntity;
import depends.relations.Inferer;

public class PomArtifactEntity extends TypeEntity {
	HashMap<String,String> properties;
	public PomArtifactEntity(String simpleName, Entity parent, Integer id) {
		super(GenericName.build(simpleName), parent, id);
		properties = new HashMap<>();
	}

	public String getProperty(String key) {
		String v = properties.get(key);
		if (v!=null) return v;
		FileEntity file = (FileEntity)(this.getAncestorOfType(FileEntity.class));
		Collection<Entity> parents = file.getImportedRelationEntities();
		for(Entity parent:parents) {
			if (parent instanceof PomArtifactEntity) {
				return ((PomArtifactEntity)parent).getProperty(key);
			}
		}
		return null;
	}
	
	public void addProperty(String key, String value) {
		value = replaceProperty(value);
		properties.put(key, value);
	}

	public String replaceProperty(String content) {
	    Pattern pattern = Pattern.compile("\\$\\{[_A-Za-z0-9\\-\\.]*\\}");
	    Matcher matcher = pattern.matcher(content);
	    String s = content;
	    while (matcher.find()) {
	    	String keyPattern = matcher.group();
	    	String key = keyPattern.replace("${", "").replace("}","");
	    	String value = getProperty(key);
	    	if (value!=null)
	        s = s.replace(keyPattern, value);
	    }
	    return s;
	}

}
