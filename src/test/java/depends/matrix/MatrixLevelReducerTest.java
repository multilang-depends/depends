package depends.matrix;

import static org.junit.Assert.*;

import org.junit.Test;

import depends.matrix.transform.MatrixLevelReducer;

public class MatrixLevelReducerTest {

	@Test
	public void test_MatrixLevelReducer_1() {
		String node = ".maven.maven-parent.16.maven-parent-16_pom";
		assertEquals(".maven",
				MatrixLevelReducer.calcuateNodeAtLevel(node, 1));
	}
	
	@Test
	public void test_MatrixLevelReducer_2() {
		String node = "maven.maven-parent.16.maven-parent-16_pom";
		assertEquals("maven",
				MatrixLevelReducer.calcuateNodeAtLevel(node, 1));
	}
	
	@Test
	public void test_MatrixLevelReducer_3() {
		String node = ".maven.maven-parent.16.maven-parent-16_pom";
		assertEquals(".maven.maven-parent.16.maven-parent-16_pom",
				MatrixLevelReducer.calcuateNodeAtLevel(node, 100));
	}
	
	@Test
	public void test_MatrixLevelReducer_4() {
		String node = "maven.maven-parent....16.maven-parent-16_pom";
		assertEquals("maven.maven-parent.16",
				MatrixLevelReducer.calcuateNodeAtLevel(node, 3));
	}
	
	@Test
	public void test_MatrixLevelReducer_5() {
		String node = "maven/maven-parent/16/maven-parent-16.pom";
		assertEquals("maven",
				MatrixLevelReducer.calcuateNodeAtLevel(node, 1));
	}
	
	@Test
	public void test_MatrixLevelReducer_6() {
		String node = "/maven/maven-parent/16/maven-parent-16.pom";
		assertEquals("/maven",
				MatrixLevelReducer.calcuateNodeAtLevel(node, 1));
	}
}
