package depends.format.excel;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import depends.format.matrix.DependencyMatrix;

public class ExcelDataBuilder {
	
	private DependencyMatrix matrix;
	private HSSFWorkbook workbook ;
	private HSSFSheet sheet ;
	public ExcelDataBuilder(DependencyMatrix matrix) {
		this.matrix = matrix;
	}

	public void output(String filename) {
		startFile();
		Map<Integer, Map<Integer, Map<String, Integer>>> relations = matrix.getRelations();
		HSSFRow[] row = new HSSFRow[matrix.getNodes().size()];
		
		//create header row
		HSSFRow header = sheet.createRow(0);
		for (int i =0 ;i<matrix.getNodes().size();i++) {
			HSSFCell cell = header.createCell(i+2);
			cell.setCellValue(i);
		};
		
		//create header col
		for (int i =0 ;i<matrix.getNodes().size();i++) {
			row[i] = sheet.createRow(i+1);
			String node = matrix.getNodes().get(i);
			HSSFCell cell = row[i].createCell(0);
			cell.setCellValue(i);
			cell = row[i].createCell(1);
			cell.setCellValue(node);
		};

		//create header col
		for (int i =0 ;i<matrix.getNodes().size();i++) {
			HSSFCell cell = row[i].createCell(i+2);
			cell.setCellValue("("+i+")");
		};

		for (Integer from:relations.keySet()) {
			Map<Integer, Map<String, Integer>> relation = relations.get(from);
			for (Integer to:relation.keySet()) {
				HSSFCell cell = row[from].createCell(to+2);
				cell.setCellValue(buildDependencyValues(relation.get(to)));
			}
		}
        closeFile(filename);
	}

	private String buildDependencyValues(Map<String, Integer> dependencies) {
		StringBuilder sb = new StringBuilder();
		for (String dependency:dependencies.keySet()) {
			String comma = sb.length()>0?",":"";
			sb.append(comma).append(dependency).append("(").append(dependencies.get(dependency)).append(")");
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
