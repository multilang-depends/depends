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

package depends.matrix.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class DependencyPair {
	private Integer from;
	private Integer to;
	HashMap<String, DependencyValue> dependencies;
	public DependencyPair(Integer from, Integer to) {
		this.from = from;
		this.to= to;
		dependencies = new HashMap<>();
	}
	public static String key(Integer from, Integer to) {
		return ""+from+"-->"+to;
	}
	
	public void addDependency(String depType, int weight, DependencyDetail detail) {
		if (dependencies.get(depType)==null)
			dependencies.put(depType, new DependencyValue(depType));
		DependencyValue value = dependencies.get(depType);
		value.addDependency(weight,detail);
	}
	
	public void addDependency(String depType, int weight, List<DependencyDetail> details) {
		if (dependencies.get(depType)==null)
			dependencies.put(depType, new DependencyValue(depType));
		DependencyValue value = dependencies.get(depType);		
		value.addDependency(weight,details);
	}
	
	public Integer getFrom() {
		return from;
	}
	public Integer getTo() {
		return to;
	}
	public Collection<DependencyValue> getDependencies() {
		return dependencies.values();
	}
	public void reMap(Integer from, Integer to) {
		this.from = from;
		this.to = to;
	}


}
