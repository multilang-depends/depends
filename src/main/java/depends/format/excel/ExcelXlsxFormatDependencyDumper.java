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

package depends.format.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import depends.format.AbstractFormatDependencyDumper;
import depends.matrix.core.DependencyMatrix;
import depends.matrix.core.DependencyPair;
import depends.matrix.core.DependencyValue;

public class ExcelXlsxFormatDependencyDumper extends AbstractFormatDependencyDumper {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	@Override
	public String getFormatName() {
		return "xlsx";
	}
	public ExcelXlsxFormatDependencyDumper(DependencyMatrix dependencyMatrix, String projectName, String outputDir) {
		super(dependencyMatrix, projectName,outputDir);
	}

	@Override
	public boolean output() {
		String filename = composeFilename() + ".xlsx";

		startFile();
		Collection<DependencyPair> dependencyPairs = matrix.getDependencyPairs();
		XSSFRow[] row = new XSSFRow[matrix.getNodes().size()];

		// create header row
		XSSFRow header = sheet.createRow(0);
		for (int i = 0; i < matrix.getNodes().size(); i++) {
			XSSFCell cell = header.createCell(i + 2);
			cell.setCellValue(i);
		}
		;

		// create header col
		for (int i = 0; i < matrix.getNodes().size(); i++) {
			row[i] = sheet.createRow(i + 1);
			String node = matrix.getNodes().get(i);
			XSSFCell cell = row[i].createCell(0);
			cell.setCellValue(i);
			cell = row[i].createCell(1);
			cell.setCellValue(node);
		}
		;

		// create header col
		for (int i = 0; i < matrix.getNodes().size(); i++) {
			XSSFCell cell = row[i].createCell(i + 2);
			cell.setCellValue("(" + i + ")");
		}
		;

		for (DependencyPair dependencyPair : dependencyPairs) {
			XSSFCell cell = row[dependencyPair.getFrom()].createCell(dependencyPair.getTo() + 2);
			cell.setCellValue(buildDependencyValues(dependencyPair.getDependencies()));
		}
		closeFile(filename);
		return true;
	}

	private String buildDependencyValues(Collection<DependencyValue> dependencies) {
		StringBuilder sb = new StringBuilder();
		for (DependencyValue dependency : dependencies) {
			String comma = sb.length() > 0 ? "," : "";
			sb.append(comma).append(dependency.getType()).append("(").append(dependency.getWeight()).append(")");
		}
		return sb.toString();
	}

	private void closeFile(String filename) {
		try {
	        FileOutputStream out = new FileOutputStream(filename);
	        workbook.write(out);
	        workbook.close();
	        out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void startFile() {
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("DSM");
	}

}
