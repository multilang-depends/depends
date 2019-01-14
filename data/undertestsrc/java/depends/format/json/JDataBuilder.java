package depends.format.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import depends.format.FileAttributes;
import depends.matrix.DependencyMatrix;
import depends.matrix.DependencyPair;
import depends.matrix.DependencyValue;

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
			JCellObject cellObject = new JCellObject();
			cellObject.setSrc(dependencyPair.getFrom());
			cellObject.setDest(dependencyPair.getTo());
			cellObject.setValues(valueObject);
			cellObjects.add(cellObject);
		}
		return cellObjects;
	}

	private Map<String, Float> buildValueObject(Collection<DependencyValue> dependencies) {
		Map<String, Float> valueObject = new HashMap<String, Float>();
		for (DependencyValue dependency : dependencies) {
			valueObject.put(dependency.getType(), (float) dependency.getWeight());
		}
		return valueObject;
	}
}
