package depends.format.json;

import depends.format.excel.ExcelXlsxFormatDependencyDumper;
import depends.matrix.core.DependencyMatrix;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JsonFormatDependencyDumperTest {
	DependencyMatrix dependencyMatrix;
	 @Rule
	  public TemporaryFolder folder= new TemporaryFolder();
	 
	 @Before
	 public void init() {
		 int SIZE = 4000;
			dependencyMatrix = new DependencyMatrix(SIZE*100);
			for (int i=0;i<SIZE;i++)
				dependencyMatrix.addNode(""+i,i);
			System.out.print("here1");
			
			for (int i=0;i<SIZE;i++) {
				System.out.println("in "+i);
				for (int j=0;j<1500;j++) {
					dependencyMatrix.addDependency("t", i, j, 0, new ArrayList<>());
				}
			}
			System.out.print("here2"); 
	 }
	 @Ignore
	public void testJson() throws IOException {
		
		String projectName = "test";
		String outputDir =  folder.getRoot().getAbsolutePath();
		JsonFormatDependencyDumper dumper = new JsonFormatDependencyDumper(dependencyMatrix, projectName, outputDir);
		dumper.output();
		System.out.println(outputDir+File.separator+"test.json");
		
	}

	@Ignore
	public void testExcel() throws IOException {
		String projectName = "test";
		String outputDir =  folder.getRoot().getAbsolutePath();
		ExcelXlsxFormatDependencyDumper dumper = new ExcelXlsxFormatDependencyDumper(dependencyMatrix, projectName, outputDir);
		dumper.output();
		System.out.println(outputDir+File.separator+"test.xlsx");
		
	}
	
}
