package depends.format.xml;

import java.util.ArrayList;
import java.util.Collection;

import depends.format.FileAttributes;
import depends.format.matrix.DependencyMatrix;
import depends.format.matrix.DependencyPair;
import depends.format.matrix.DependencyValue;

public class XDataBuilder {
    public XDepObject build(DependencyMatrix matrix,FileAttributes configure) {
        ArrayList<String> files = matrix.getNodes();
        Collection<DependencyPair> dependencyPairs = matrix.getDependencyPairs();

        XFiles xFiles = new XFiles();
        xFiles.setFiles(files);

        ArrayList<XCell> xCellList = buildCellList(dependencyPairs);

        XCells xCells = new XCells();
        xCells.setCells(xCellList);

        XDepObject xDepObject = new XDepObject();
        xDepObject.setName(configure.getAttributeName());
        xDepObject.setSchemaVersion(configure.getSchemaVersion());
        xDepObject.setVariables(xFiles);
        xDepObject.setCells(xCells);

        return xDepObject;
    }


    private ArrayList<XCell> buildCellList(Collection<DependencyPair> dependencyPairs) {
    	ArrayList<XCell> cellList = new ArrayList<XCell>();
        for (DependencyPair pair : dependencyPairs) {
                ArrayList<XDepend> xDepends = buildDependList(pair.getDependencies());
                XCell xCell = new XCell();
                xCell.setSrc(pair.getFrom());
                xCell.setDest(pair.getTo());
                xCell.setDepends(xDepends);
                cellList.add(xCell);
        } 
        return cellList;
	}

	private ArrayList<XDepend> buildDependList(Collection<DependencyValue> dependencies) {
		ArrayList<XDepend> dependList = new ArrayList<XDepend>();

        for (DependencyValue dependency : dependencies) {
            XDepend xDepend = new XDepend();
            xDepend.setWeight(dependency.getWeight());
            xDepend.setName(dependency.getType());
            dependList.add(xDepend);
        } 
        return dependList;
	}
}
