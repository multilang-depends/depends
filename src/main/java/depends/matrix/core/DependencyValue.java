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

import java.util.ArrayList;
import java.util.List;

public class DependencyValue{
	private int weight;
	private String type;
	private List<DependencyDetail> dependencyDetail;
	public DependencyValue(String type) {
		this.type = type;
		this.weight=0;
		dependencyDetail = new ArrayList<>();
	}

	public void addDependency(int weight, DependencyDetail detail) {
        this.weight += weight;
        if (detail!=null)
            dependencyDetail.add(detail);
	}
	
	public int getWeight() {
		return weight;
	}

	public String getType() {
		return type;
	}

	public List<DependencyDetail> getDetails() {
		return dependencyDetail;
	}

	public void addDependency(int weight, List<DependencyDetail> details) {
        this.weight += weight;
        if (details!=null)
        	dependencyDetail.addAll(details);
	}

}