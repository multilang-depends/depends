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

package depends.matrix.transform;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import depends.matrix.core.DependencyMatrix;
import depends.matrix.core.DependencyPair;
import depends.matrix.core.DependencyValue;

public class MatrixLevelReducer {

	private DependencyMatrix origin;
	private int level;
	HashMap<String, Integer> nodesMap = new HashMap<>();

	public MatrixLevelReducer(DependencyMatrix matrix, String levelString) {
		this.origin = matrix;
		this.level = stringToPositiveInt(levelString);
	}

	public DependencyMatrix shrinkToLevel() {
		if (level < 0)
			return origin;

		ArrayList<String> reMappedNodes = new ArrayList<>();
		for (String node : origin.getNodes()) {
			String newNode = calcuateNodeAtLevel(node, level);
			if (!reMappedNodes.contains(newNode)) {
				reMappedNodes.add(newNode);
			}
		}
		// sort nodes by name
		reMappedNodes.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
	    DependencyMatrix ordered = new DependencyMatrix();
		for (int id=0;id<reMappedNodes.size();id++) {
			nodesMap.put(reMappedNodes.get(id), id);
			ordered.addNode(reMappedNodes.get(id), id);
		}

		// add dependencies
		for (DependencyPair dependencyPair : origin.getDependencyPairs()) {
			for (DependencyValue dep : dependencyPair.getDependencies()) {
				ordered.addDependency(dep.getType(), translateToNewId(dependencyPair.getFrom()),
						translateToNewId(dependencyPair.getTo()), dep.getWeight(), dep.getDetails());
			}
		}
		return ordered;
	}

	public static String calcuateNodeAtLevel(String node, int level) {
		String splitterRegex = "\\.";
		String splitter = ".";
		String windowsSplitter = "\\";
		String unixSplitter = "/";
		
		if (node.contains(windowsSplitter)) {
			splitter = windowsSplitter;
			splitterRegex = windowsSplitter+windowsSplitter;
		}else if (node.contains(unixSplitter)) {
			splitter = unixSplitter;
			splitterRegex = unixSplitter;		
		}
		String prefix = "";
		if (node.startsWith(splitter)) {
			prefix = splitter;
		}
		String[] segments = node.split(splitterRegex);
		StringBuffer sb = new StringBuffer();
		int count = 0;
		for (int i = 0; i < segments.length; i++) {
			if (count == level)
				break;
			if (segments[i].length() > 0) {
				if (sb.length()>0)
					sb.append(splitter);
				sb.append(segments[i]);
				count++;
			}
		}
		return prefix + sb.toString();
	}

	private Integer translateToNewId(Integer id) {
		String newNode = calcuateNodeAtLevel(origin.getNodeName(id), level);
		return nodesMap.get(newNode);
	}

	private int stringToPositiveInt(String level) {
		int result = -1;
		try {
			result = Integer.parseInt(level);
		} catch (Exception e) {
			result = -1;
		}
		if (result <= 0) {
			result = -1;
		}
		return result;
	}

}
