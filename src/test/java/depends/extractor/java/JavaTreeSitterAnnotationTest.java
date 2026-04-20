package depends.extractor.java;

import depends.deptypes.DependencyType;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class JavaTreeSitterAnnotationTest extends JavaTreeSitterParserTest {
    @Before
    public void setUp() {
        super.init();
    }

    @Test
    public void test_should_extract_annotation_relations_on_major_sites() throws IOException {
        String src = "./src/test/resources/java-code-examples/AnnotationTest.java";
        createParser().parse(src);
        resolveAllBindings();

        assertNotNull(entityRepo.getEntity("AnnotationTest"));
        assertContainsRelation(entityRepo.getEntity("TheClass"), DependencyType.ANNOTATION, "AnnotationTest");
        assertContainsRelation(entityRepo.getEntity("TheClass.TheClass"), DependencyType.ANNOTATION, "AnnotationTest");
        assertContainsRelation(entityRepo.getEntity("TheClass.theField"), DependencyType.ANNOTATION, "AnnotationTest");
        assertContainsRelation(entityRepo.getEntity("TheFunction.foo"), DependencyType.ANNOTATION, "AnnotationTest");
        assertContainsRelation(entityRepo.getEntity("TheEnum"), DependencyType.ANNOTATION, "AnnotationTest");
        assertContainsRelation(entityRepo.getEntity("TheInterface.foo"), DependencyType.ANNOTATION, "AnnotationTest");
        assertContainsRelation(entityRepo.getEntity("TheInterface.theConst"), DependencyType.ANNOTATION, "AnnotationTest");
    }
}
