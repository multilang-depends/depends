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

package depends.format.xml;

import java.util.ArrayList;
import java.util.Collection;

import depends.format.FileAttributes;
import depends.matrix.core.DependencyMatrix;
import depends.matrix.core.DependencyPair;
import depends.matrix.core.DependencyValue;

public class XDataBuilder {
    public XDepObject build(DependencyMatrix matrix,FileAttributes attribute) {
        ArrayList<String> files = matrix.getNodes();
        Collection<DependencyPair> dependencyPairs = matrix.getDependencyPairs();

        XFiles xFiles = new XFiles();
        xFiles.setFiles(files);

        ArrayList<XCell> xCellList = buildCellList(dependencyPairs);

        XCells xCells = new XCells();
        xCells.setCells(xCellList);

        XDepObject xDepObject = new XDepObject();
        xDepObject.setName(attribute.getAttributeName());
        xDepObject.setSchemaVersion(attribute.getSchemaVersion());
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
