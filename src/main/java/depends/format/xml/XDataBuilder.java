package depends.format.xml;

import java.util.ArrayList;
import java.util.Map;

import depends.format.matrix.DependencyMatrix;
import depends.util.Configure;

public class XDataBuilder {

    public XDepObject build(DependencyMatrix matrix) {
        Configure configure = Configure.getConfigureInstance();
        ArrayList<String> files = matrix.getNodes();
        Map<Integer, Map<Integer, Map<String, Integer>>> finalRes = matrix.getRelations();

        XFiles xFiles = new XFiles();
        xFiles.setFiles(files);

        ArrayList<XCell> xCellList = buildCellList(finalRes);

        XCells xCells = new XCells();
        xCells.setCells(xCellList);

        XDepObject xDepObject = new XDepObject();
        xDepObject.setName(configure.getAttributeName());
        xDepObject.setSchemaVersion(configure.getSchemaVersion());
        xDepObject.setVariables(xFiles);
        xDepObject.setCells(xCells);

        return xDepObject;
    }



    private ArrayList<XCell> buildCellList(Map<Integer, Map<Integer, Map<String, Integer>>> finalRes) {
        ArrayList<XCell> cellList = new ArrayList<XCell>();
        for (Map.Entry<Integer, Map<Integer, Map<String, Integer>>> entry1 : finalRes.entrySet()) {
            int src = entry1.getKey();

            Map<Integer, Map<String, Integer>> values1 = entry1.getValue();
            for (Map.Entry<Integer, Map<String, Integer>> entry2 : values1.entrySet()) {
                int dst = entry2.getKey();

                Map<String, Integer> values2 = entry2.getValue();
                ArrayList<XDepend> xDepends = buildDependList(values2);
                XCell xCell = new XCell();
                xCell.setSrc(src);
                xCell.setDest(dst);
                xCell.setDepends(xDepends);
                cellList.add(xCell);
            }
        } //end for
        return cellList;
    }



    private ArrayList<XDepend> buildDependList(Map<String, Integer> values2) {
        ArrayList<XDepend> dependList = new ArrayList<XDepend>();

        for (Map.Entry<String, Integer> entry3 : values2.entrySet()) {
            String depType = entry3.getKey();
            float weight = (float) entry3.getValue();
            XDepend xDepend = new XDepend();
            xDepend.setWeight(weight);
            xDepend.setName(depType);
            dependList.add(xDepend);
        } //end for
        return dependList;
    }

}
