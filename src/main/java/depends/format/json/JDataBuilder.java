package depends.format.json;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import depends.format.matrix.DependencyMatrix;
import depends.util.Configure;

public class JDataBuilder {


    public JDepObject build(DependencyMatrix dependencyMatrix) {
        Configure configure = Configure.getConfigureInstance();
        ArrayList<String> files = dependencyMatrix.getNodes();
        Map<Integer, Map<Integer, Map<String, Integer>>> finalRes = dependencyMatrix.getRelations();
        ArrayList<JCellObject> cellObjects = buildCellObjects(finalRes); //transform finalRes into cellObjects

        JDepObject depObject = new JDepObject();
        depObject.setVariables(files);
        depObject.setName(configure.getAttributeName());
        depObject.setSchemaVersion(configure.getSchemaVersion());
        depObject.setCells(cellObjects);

        return depObject;
    }


    /**
     *
     * @return
     */
    private ArrayList<JCellObject> buildCellObjects(Map<Integer, Map<Integer, Map<String, Integer>>> finalRes) {
        ArrayList<JCellObject> cellObjects = new ArrayList<JCellObject>();

        for (Map.Entry<Integer, Map<Integer, Map<String, Integer>>> entry1 : finalRes.entrySet()) {
            int src = entry1.getKey();

            Map<Integer, Map<String, Integer>> values1 = entry1.getValue();
            for (Map.Entry<Integer, Map<String, Integer>> entry2: values1.entrySet()) {
                int dst = entry2.getKey();

                Map<String, Integer> values2 = entry2.getValue();
                Map<String, Float> valueObject = buildValueObject(values2);
                JCellObject cellObject = new JCellObject();
                cellObject.setSrc(src);
                cellObject.setDest(dst);
                cellObject.setValues(valueObject);
                cellObjects.add(cellObject);
            }
        }
        return cellObjects;
    }


    /**
     *
     * @param values2
     * @return
     */
    private Map<String, Float> buildValueObject(Map<String, Integer> values2) {
        Map<String, Float> valueObject = new HashMap<String, Float>();
        for (Map.Entry<String, Integer> entry3 : values2.entrySet()) {
            String depType = entry3.getKey();
            float weight = (float) entry3.getValue();
            valueObject.put(depType, weight);
        } //end for
        return valueObject;
    }






}
