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

import depends.entity.Entity;
import depends.format.path.FilenameWritter;

public class DependencyMatrix {
    private HashMap<String, DependencyPair> dependencyPairs = new HashMap<>();
    private ArrayList<String> nodes = new ArrayList<>();
    private HashMap<Integer,String> originNodeMap = new HashMap<>();
    private ArrayList<String> reMappedNodes;
	private Integer relationCount=0;
    public DependencyMatrix() {
    }
	
	public Collection<DependencyPair> getDependencyPairs() {
        return dependencyPairs.values();
    }

	public void addNode(String name, int id) {
		this.nodes.add(name);
		this.originNodeMap.put(id, name);
	}
	
    public void addDependency(String depType, Integer from, Integer to, Entity fromEntity, Entity toEntity) {
		if(from.equals(to) || from == -1 || to == -1) {
		    return;
		}
		if (dependencyPairs.get(DependencyPair.key(from,to))==null) {
			dependencyPairs.put(DependencyPair.key(from,to),new DependencyPair(from,to));
		}
		DependencyPair dependencyPair = dependencyPairs.get(DependencyPair.key(from,to));
		dependencyPair.addDependency(depType,fromEntity, toEntity);
		relationCount++;
	}

	public ArrayList<String> getNodes() {
		return reMappedNodes;
	}

	public void remapIds() {
		HashMap<String, Integer> nodesMap = new HashMap<>();
		reMappedNodes = new ArrayList<>(nodes);
		reMappedNodes.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});

		for (int i=0;i<reMappedNodes.size();i++) {
			nodesMap.put(reMappedNodes.get(i), i);
		}
		for (DependencyPair dependencyPair:dependencyPairs.values()) {
			Integer from = dependencyPair.getFrom();
			Integer to = dependencyPair.getTo();
			dependencyPair.reMap(translateToNewId( nodesMap, from),translateToNewId( nodesMap, to));
		}
	}

	public Integer relationCount() {
		return relationCount;
	}
	
	public void reWriteFilenamePattern(FilenameWritter filenameRewritter) {
		for (int i=0;i<reMappedNodes.size();i++) {
			reMappedNodes.set(i, filenameRewritter.reWrite(reMappedNodes.get(i)));
		}		
	}
	
	private Integer translateToNewId( HashMap<String, Integer> nodesMap, Integer key) {
		return nodesMap.get(originNodeMap.get(key));
	}
}
