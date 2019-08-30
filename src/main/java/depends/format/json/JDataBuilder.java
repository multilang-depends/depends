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

package depends.format.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import depends.format.FileAttributes;
import depends.matrix.core.DependencyDetail;
import depends.matrix.core.DependencyMatrix;
import depends.matrix.core.DependencyPair;
import depends.matrix.core.DependencyValue;

public class JDataBuilder {
	public JDepObject build(DependencyMatrix dependencyMatrix, FileAttributes attribute) {
		ArrayList<String> files = dependencyMatrix.getNodes();
		Collection<DependencyPair> dependencyPairs = dependencyMatrix.getDependencyPairs();
		ArrayList<JCellObject> cellObjects = buildCellObjects(dependencyPairs); // transform finalRes into cellObjects

		JDepObject depObject = new JDepObject();
		depObject.setVariables(files);
		depObject.setName(attribute.getAttributeName());
		depObject.setSchemaVersion(attribute.getSchemaVersion());
		depObject.setCells(cellObjects);

		return depObject;
	}

	private ArrayList<JCellObject> buildCellObjects(Collection<DependencyPair> dependencyPairs) {
		ArrayList<JCellObject> cellObjects = new ArrayList<JCellObject>();

		for (DependencyPair dependencyPair : dependencyPairs) {
			Map<String, Float> valueObject = buildValueObject(dependencyPair.getDependencies());
			List<DetailItem> details = buildDetails(dependencyPair.getDependencies());
			JCellObject cellObject = new JCellObject();
			cellObject.setSrc(dependencyPair.getFrom());
			cellObject.setDest(dependencyPair.getTo());
			cellObject.setValues(valueObject);
			cellObject.setDetails(details);
			cellObjects.add(cellObject);
		}
		return cellObjects;
	}

	private List<DetailItem> buildDetails(Collection<DependencyValue> dependencies) {
		List<DetailItem>  r = new ArrayList<>();
		for (DependencyValue dependency : dependencies) {
			for (DependencyDetail detail:dependency.getDetails()) {
				r.add(new DetailItem(detail.getSrc(),detail.getDest(),dependency.getType()));
			}
		}
		if (r.size()==0) return null;
		return r;
	}

	private Map<String, Float> buildValueObject(Collection<DependencyValue> dependencies) {
		Map<String, Float> valueObject = new HashMap<String, Float>();
		for (DependencyValue dependency : dependencies) {
			valueObject.put(dependency.getType(), (float) dependency.getWeight());
		}
		return valueObject;
	}
}
