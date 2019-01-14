package depends.format.excel;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import depends.format.AbstractFormatDependencyDumper;
import depends.matrix.DependencyMatrix;
import depends.matrix.DependencyPair;
import depends.matrix.DependencyValue;

public class ExcelFormatDependencyDumper extends AbstractFormatDependencyDumper {
	private HSSFWorkbook workbook;
	private HSSFSheet sheet;
	@Override
	public String getFormatName() {
		return "excel";
	}
	public ExcelFormatDependencyDumper(DependencyMatrix dependencyMatrix, String projectName, String outputDir) {
		super(dependencyMatrix, projectName,outputDir);
	}

	@Override
	public boolean output() {
		String filename = composeFilename() + ".xls";
		if (matrix.getNodes().size() > 255) {
			System.out.println("We can only export small matrix(<256 items) to excel" + "due to MS Office limitation");
			return false;
		}
		startFile();
		Collection<DependencyPair> dependencyPairs = matrix.getDependencyPairs();
		HSSFRow[] row = new HSSFRow[matrix.getNodes().size()];

		// create header row
		HSSFRow header = sheet.createRow(0);
		for (int i = 0; i < matrix.getNodes().size(); i++) {
			HSSFCell cell = header.createCell(i + 2);
			cell.setCellValue(i);
		}
		;

		// create header col
		for (int i = 0; i < matrix.getNodes().size(); i++) {
			row[i] = sheet.createRow(i + 1);
			String node = matrix.getNodes().get(i);
			HSSFCell cell = row[i].createCell(0);
			cell.setCellValue(i);
			cell = row[i].createCell(1);
			cell.setCellValue(node);
		}
		;

		// create header col
		for (int i = 0; i < matrix.getNodes().size(); i++) {
			HSSFCell cell = row[i].createCell(i + 2);
			cell.setCellValue("(" + i + ")");
		}
		;

		for (DependencyPair dependencyPair : dependencyPairs) {
			HSSFCell cell = row[dependencyPair.getFrom()].createCell(dependencyPair.getTo() + 2);
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
			workbook.write(new File(filename));
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void startFile() {
		workbook = new HSSFWorkbook();
		sheet = workbook.createSheet("DSM");
	}

}
