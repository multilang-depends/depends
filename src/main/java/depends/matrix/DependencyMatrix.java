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

package depends.matrix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;

import depends.format.path.FilenameWritter;

public class DependencyMatrix {
    private HashMap<String, DependencyPair> dependencyPairs = new HashMap<>();
    private ArrayList<String> nodes = new ArrayList<>();
    private HashMap<Integer,String> nodeIdToName = new HashMap<>();
	private Integer relationCount=0;
    public DependencyMatrix() {
    }
	
	public Collection<DependencyPair> getDependencyPairs() {
        return dependencyPairs.values();
    }

	public void addNode(String name, int id) {
		this.nodes.add(name);
		this.nodeIdToName.put(id, name);
	}
	
	public void addDependency(String depType, Integer from, Integer to,  int weight,String detail) {
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

	public DependencyMatrix orderedMatrix() {
	    ArrayList<String> reMappedNodes= new ArrayList<>(nodes);
	    //sort nodes by name
	    reMappedNodes.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
	    DependencyMatrix ordered = new DependencyMatrix();
	    ordered.nodes  = reMappedNodes; 
	    
		HashMap<String, Integer> nodesMap = new HashMap<>();
		for (int i=0;i<reMappedNodes.size();i++) {
			nodesMap.put(reMappedNodes.get(i), i);
		}
		//add dependencies
		for (DependencyPair dependencyPair:dependencyPairs.values()) {
			Integer from = dependencyPair.getFrom();
			Integer to = dependencyPair.getTo();
			for (DependencyValue dep:dependencyPair.getDependencies()) {
				ordered.addDependency(dep.getType(), translateToNewId( nodesMap, from), translateToNewId( nodesMap, to), dep.getWeight(),dep.getDetails());
			}
		}
		return ordered;
	}



	public Integer relationCount() {
		return relationCount;
	}
	
	public DependencyMatrix reWriteFilenamePattern(FilenameWritter filenameRewritter) {
		for (int i=0;i<nodes.size();i++) {
			nodes.set(i, filenameRewritter.reWrite(nodes.get(i)));
		}		
		return this;
	}
	
	private Integer translateToNewId( HashMap<String, Integer> nodesMap, Integer key) {
		return nodesMap.get(nodeIdToName.get(key));
	}

	public DependencyMatrix shrinkToLevel(String levelString) {
		int level = stringToPositiveInt(levelString);
		if (level<0) return this;
		
		return this;
	}
	
	private int stringToPositiveInt(String level) {
		int result = -1;
		try {
			result  = Integer.parseInt(level);}
		catch(Exception e) {
			result = -1;
		}
		if (result<=0) {
			result = -1; 
		}
		return result;
	}

}
