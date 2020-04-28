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

package depends.entity;

import java.util.HashMap;
import java.util.HashSet;

public class PackageEntity extends TypeEntity {
	HashMap<String,Entity> entities	 = new HashMap<>();
	private String filePath;
	
	public PackageEntity() {}

	public PackageEntity(String rawName, Integer id) {
		super(GenericName.build(rawName),  null,id);
		setQualifiedName(rawName); //in Java, package raw name = full name
	}

	public PackageEntity(String rawName, Entity currentFile, Integer id) {
		super(GenericName.build(rawName),  currentFile,id);
	}

	public Entity getChildOfName(String name) {
		for (Entity child:this.getChildren()) {
			if (child.getRawName().equals(name)) 
				return child;
		}
		if (entities.get(name)!=null)
			return entities.get(name);
		return null;
	}

	public void addChild(String moduleName, Entity entity) {
		super.addChild(entity);
		entities.put(moduleName, entity);
		visibleNames.put(moduleName, entity);
	}
	
	@Override
	public Entity getByName(String name, HashSet<Entity> searched) {
		Entity entity = super.getByName(name, searched);
		if (entity!=null)
			return entity;
		for (Entity child:getChildren()) {
			if (child instanceof FileEntity) {
				if (searched.contains(child)) continue;
				entity = child.getByName(name,searched);
				if (entity!=null) return entity;
			}
		}
		return null;
	}

	/**
	 * For Python, the package entities defined in a file path
	 * @param filePath
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
}
