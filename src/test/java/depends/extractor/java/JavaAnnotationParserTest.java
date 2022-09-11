package depends.extractor.java;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;

public class JavaAnnotationParserTest extends JavaParserTest {
	@Before
	public void setUp() {
		super.init();
	}
	@Test
	public void test_could_parse_annotationType() throws IOException {
        String src = "./src/test/resources/java-code-examples/AnnotationTest.java";
        JavaFileParser parser =createParser();
        parser.parse(src);
        resolveAllBindings();
        assertEquals(1,entityRepo.getEntity("AnnotationTest.value").getRelations().size());
	}
	
	@Test
	public void test_could_detect_annotation_in_class_level() throws IOException {
        String src = "./src/test/resources/java-code-examples/AnnotationTest.java";
        JavaFileParser parser =createParser();
        parser.parse(src);
        resolveAllBindings();
        this.assertContainsRelation(entityRepo.getEntity("TheClass"), DependencyType.ANNOTATION, "AnnotationTest");
	}
	
	@Test
	public void test_could_detect_annotation_in_function_level() throws IOException {
        String src = "./src/test/resources/java-code-examples/AnnotationTest.java";
        JavaFileParser parser =createParser();
        parser.parse(src);
        resolveAllBindings();
        this.assertContainsRelation(entityRepo.getEntity("TheFunction.foo"), DependencyType.ANNOTATION, "AnnotationTest");
	}
	
	@Test
	public void test_could_detect_no_annotation_in_function_level() throws IOException {
        String src = "./src/test/resources/java-code-examples/AnnotationTest.java";
        JavaFileParser parser =createParser();
        parser.parse(src);
        resolveAllBindings();
        this.assertNotContainsRelation(entityRepo.getEntity("TheFunction.bar"), DependencyType.ANNOTATION, "AnnotationTest");
	}

	@Test
	public void test_could_detect_annotation_in_miscs() throws IOException {
        String src = "./src/test/resources/java-code-examples/AnnotationTest.java";
        JavaFileParser parser =createParser();
        parser.parse(src);
        resolveAllBindings();
        this.assertContainsRelation(entityRepo.getEntity("TheClass.TheClass"), DependencyType.ANNOTATION, "AnnotationTest");
        this.assertContainsRelation(entityRepo.getEntity("TheEnum"), DependencyType.ANNOTATION, "AnnotationTest");
        this.assertContainsRelation(entityRepo.getEntity("TheInterface.foo"), DependencyType.ANNOTATION, "AnnotationTest");
        this.assertContainsRelation(entityRepo.getEntity("TheInterface.theConst"), DependencyType.ANNOTATION, "AnnotationTest");
        this.assertContainsRelation(entityRepo.getEntity("TheClass.theField"), DependencyType.ANNOTATION, "AnnotationTest");
	}
	

}
