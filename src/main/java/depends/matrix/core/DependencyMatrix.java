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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import multilang.depends.util.file.path.FilenameWritter;

public class DependencyMatrix {
    private HashMap<String, DependencyPair> dependencyPairs = new HashMap<>();
    private ArrayList<String> nodes = new ArrayList<>();
    private HashMap<Integer,String> nodeIdToName = new HashMap<>();
	private Integer relationCount=0;
	private List<String> typeFilter;
    public DependencyMatrix() {
    }
    public DependencyMatrix(int size) {
    	dependencyPairs = new HashMap<>(size);
    }	
	public DependencyMatrix(List<String> typeFilter) {
		this.typeFilter = typeFilter;
	}
	public Collection<DependencyPair> getDependencyPairs() {
        return dependencyPairs.values();
    }

	public void addNode(String name, int id) {
		this.nodes.add(name);
		this.nodeIdToName.put(id, name);
	}
	
	public void addDependency(String depType, Integer from, Integer to,  int weight,List<DependencyDetail> details) {
		if (typeFilter!=null && (!typeFilter.contains(depType)))
			return;
		if(from.equals(to) || from == -1 || to == -1) {
		    return;
		}
		if (dependencyPairs.get(DependencyPair.key(from,to))==null) {
			dependencyPairs.put(DependencyPair.key(from,to),new DependencyPair(from,to));
		}
		DependencyPair dependencyPair = dependencyPairs.get(DependencyPair.key(from,to));
		dependencyPair.addDependency(depType,weight,details);
		relationCount+=weight;		
	}
	
	public void addDependency(String depType, Integer from, Integer to,  int weight,DependencyDetail detail) {
		if (typeFilter!=null && (!typeFilter.contains(depType)))
			return;
		if(from.equals(to) || from == -1 || to == -1) {
		    return;
		}
		if (dependencyPairs.get(DependencyPair.key(from,to))==null) {
			dependencyPairs.put(DependencyPair.key(from,to),new DependencyPair(from,to));
		}
		DependencyPair dependencyPair = dependencyPairs.get(DependencyPair.key(from,to));
		dependencyPair.addDependency(depType,weight,detail);
		relationCount+=weight;		
	}
	
    public ArrayList<String> getNodes() {
		return nodes;
	}

	public Integer relationCount() {
		return relationCount;
	}
	
	public DependencyMatrix reWriteFilenamePattern(FilenameWritter filenameRewritter) {
		this.nodeIdToName = new HashMap<>();
		for (int i=0;i<nodes.size();i++) {
			String name = filenameRewritter.reWrite(nodes.get(i));
			nodes.set(i, name );
			nodeIdToName.put(i, name);
		}		
		return this;
	}

	public String getNodeName(Integer key) {
		return nodeIdToName.get(key);
	}
}
