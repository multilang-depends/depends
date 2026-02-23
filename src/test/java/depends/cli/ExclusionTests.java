package depends.cli;

import depends.DependsCommand;
import depends.extractor.AbstractLangProcessor;
import depends.extractor.LangProcessorRegistration;
import depends.extractor.java.JavaProcessor;
import depends.relations.BindingResolver;
import multilang.depends.util.file.FileTraversal;
import multilang.depends.util.file.FileUtil;
import org.junit.Before;
import org.junit.Test;
import picocli.CommandLine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for the --exclude command line functionality
 */
public class ExclusionTests {

    private static final String TEST_RESOURCES_DIR = "./src/test/resources/exclude-examples";

    @Before
    public void setUp() {
        // Ensure lang processors are registered
        try {
            Class.forName("depends.LangRegister");
        } catch (ClassNotFoundException e) {
            // LangRegister not available in test context, that's ok
        }
    }

    // ==================== Command Line Parsing Tests ====================

    @Test
    public void shouldParseSingleExcludePattern() {
        String[] args = {"java", "/some/path", "output", "--exclude=*.test"};
        DependsCommand command = new DependsCommand();
        CommandLine.populateCommand(command, args);

        String[] excludes = command.getExcludes();
        assertNotNull("Excludes should not be null", excludes);
        assertEquals("Should have 1 exclude pattern", 1, excludes.length);
        assertEquals("Pattern should match", "*.test", excludes[0]);
    }

    @Test
    public void shouldParseMultipleExcludePatterns() {
        String[] args = {"java", "/some/path", "output", "--exclude=*.test,build/**,temp"};
        DependsCommand command = new DependsCommand();
        CommandLine.populateCommand(command, args);

        String[] excludes = command.getExcludes();
        assertNotNull("Excludes should not be null", excludes);
        assertEquals("Should have 3 exclude patterns", 3, excludes.length);
        assertEquals("First pattern should match", "*.test", excludes[0]);
        assertEquals("Second pattern should match", "build/**", excludes[1]);
        assertEquals("Third pattern should match", "temp", excludes[2]);
    }

    @Test
    public void shouldParseMultipleExcludeOptions() {
        String[] args = {"java", "/some/path", "output", "--exclude=*.test", "--exclude=build/**"};
        DependsCommand command = new DependsCommand();
        CommandLine.populateCommand(command, args);

        String[] excludes = command.getExcludes();
        assertNotNull("Excludes should not be null", excludes);
        assertEquals("Should have 2 exclude patterns", 2, excludes.length);
    }

    @Test
    public void shouldHaveEmptyExcludesByDefault() {
        String[] args = {"java", "/some/path", "output"};
        DependsCommand command = new DependsCommand();
        CommandLine.populateCommand(command, args);

        String[] excludes = command.getExcludes();
        assertNotNull("Excludes should not be null", excludes);
        assertEquals("Should have 0 exclude patterns by default", 0, excludes.length);
    }

    // ==================== FileTraversal Unit Tests ====================

    @Test
    public void shouldExcludeFilesByGlobPattern() {
        final List<String> visitedFiles = new ArrayList<>();
        FileTraversal traversal = new FileTraversal(new FileTraversal.IFileVisitor() {
            @Override
            public void visit(File file) {
                visitedFiles.add(file.getName());
            }
        });

        traversal.extensionFilter(".java");
        traversal.excludeFilter(new String[]{"*.test.java"});
        traversal.travers(TEST_RESOURCES_DIR);

        // Should not visit files matching *.test.java pattern
        for (String fileName : visitedFiles) {
            assertFalse("Should not visit .test.java files: " + fileName, 
                       fileName.endsWith(".test.java"));
        }
    }

    @Test
    public void shouldExcludeFilesByExtension() {
        final List<String> visitedFiles = new ArrayList<>();
        FileTraversal traversal = new FileTraversal(new FileTraversal.IFileVisitor() {
            @Override
            public void visit(File file) {
                visitedFiles.add(file.getName());
            }
        });

        traversal.excludeFilter(new String[]{"*.md", "*.txt"});
        traversal.travers(TEST_RESOURCES_DIR);

        assertFalse("Should not visit .md files", visitedFiles.contains("README.md"));
        assertFalse("Should not visit .txt files", visitedFiles.contains("temp.txt"));
    }

    @Test
    public void shouldExcludeDirectories() {
        final List<String> visitedPaths = new ArrayList<>();
        FileTraversal traversal = new FileTraversal(new FileTraversal.IFileVisitor() {
            @Override
            public void visit(File file) {
                visitedPaths.add(file.getAbsolutePath());
            }
        });

        traversal.extensionFilter(".java");
        // Use specific path pattern to avoid matching the src/test/resources path
        traversal.excludeFilter(new String[]{"exclude-examples/src/test/**"});
        traversal.travers(TEST_RESOURCES_DIR);

        // Should not visit any files in the test directory
        boolean foundTestFiles = false;
        for (String path : visitedPaths) {
            if (path.contains("exclude-examples/src/test/")) {
                foundTestFiles = true;
                break;
            }
        }
        assertFalse("Should not visit files in test directory", foundTestFiles);
        
        // Should still visit main files
        boolean foundMainFiles = false;
        for (String path : visitedPaths) {
            if (path.contains("MainClass.java") || path.contains("HelperClass.java")) {
                foundMainFiles = true;
                break;
            }
        }
        assertTrue("Should visit main files", foundMainFiles);
    }

    @Test
    public void shouldExcludeBuildDirectory() {
        final List<String> visitedPaths = new ArrayList<>();
        FileTraversal traversal = new FileTraversal(new FileTraversal.IFileVisitor() {
            @Override
            public void visit(File file) {
                visitedPaths.add(file.getAbsolutePath());
            }
        });

        traversal.extensionFilter(".java");
        traversal.excludeFilter(new String[]{"exclude-examples/build/**"});
        traversal.travers(TEST_RESOURCES_DIR);

        // Should not visit any files in the build directory
        boolean foundBuildFiles = false;
        for (String path : visitedPaths) {
            if (path.contains("exclude-examples/build/")) {
                foundBuildFiles = true;
                break;
            }
        }
        assertFalse("Should not visit files in build directory", foundBuildFiles);

        // GeneratedClass.java should not be visited
        boolean foundGenerated = false;
        for (String path : visitedPaths) {
            if (path.contains("GeneratedClass.java")) {
                foundGenerated = true;
                break;
            }
        }
        assertFalse("Should not visit GeneratedClass.java", foundGenerated);
        
        // Should still visit main files
        boolean foundMainFiles = false;
        for (String path : visitedPaths) {
            if (path.contains("MainClass.java") || path.contains("HelperClass.java")) {
                foundMainFiles = true;
                break;
            }
        }
        assertTrue("Should visit main files", foundMainFiles);
    }

    @Test
    public void shouldIncludeNonExcludedFiles() {
        final List<String> visitedFiles = new ArrayList<>();
        FileTraversal traversal = new FileTraversal(new FileTraversal.IFileVisitor() {
            @Override
            public void visit(File file) {
                visitedFiles.add(file.getName());
            }
        });

        traversal.extensionFilter(".java");
        // Use specific path patterns to avoid matching the src/test/resources path
        traversal.excludeFilter(new String[]{"exclude-examples/src/test/**", "exclude-examples/build/**", "*.md", "*.txt"});
        traversal.travers(TEST_RESOURCES_DIR);

        // Should visit main source files
        assertTrue("Should visit MainClass.java", visitedFiles.contains("MainClass.java"));
        assertTrue("Should visit HelperClass.java", visitedFiles.contains("HelperClass.java"));

        // Should not visit test files
        assertFalse("Should not visit TestClass.java", visitedFiles.contains("TestClass.java"));
        assertFalse("Should not visit AnotherTest.java", visitedFiles.contains("AnotherTest.java"));
        assertFalse("Should not visit GeneratedClass.java", visitedFiles.contains("GeneratedClass.java"));
    }

    @Test
    public void shouldHandleMultipleExcludePatterns() {
        final List<String> visitedFiles = new ArrayList<>();
        FileTraversal traversal = new FileTraversal(new FileTraversal.IFileVisitor() {
            @Override
            public void visit(File file) {
                visitedFiles.add(file.getName());
            }
        });

        traversal.extensionFilter(".java");
        // Use specific path patterns to avoid matching the src/test/resources path
        traversal.excludeFilter(new String[]{"exclude-examples/src/test/**", "exclude-examples/build/**"});
        traversal.travers(TEST_RESOURCES_DIR);

        // Should only visit main java files
        assertEquals("Should only visit 2 files (MainClass and HelperClass)", 2, visitedFiles.size());
        assertTrue("Should visit MainClass.java", visitedFiles.contains("MainClass.java"));
        assertTrue("Should visit HelperClass.java", visitedFiles.contains("HelperClass.java"));
    }

    @Test
    public void shouldVisitAllFilesWhenNoExcludes() {
        final List<String> visitedFiles = new ArrayList<>();
        FileTraversal traversal = new FileTraversal(new FileTraversal.IFileVisitor() {
            @Override
            public void visit(File file) {
                visitedFiles.add(file.getName());
            }
        });

        traversal.extensionFilter(".java");
        // No excludeFilter called
        traversal.travers(TEST_RESOURCES_DIR);

        // Should visit all java files including test and build
        assertTrue("Should visit MainClass.java", visitedFiles.contains("MainClass.java"));
        assertTrue("Should visit HelperClass.java", visitedFiles.contains("HelperClass.java"));
        assertTrue("Should visit TestClass.java", visitedFiles.contains("TestClass.java"));
        assertTrue("Should visit AnotherTest.java", visitedFiles.contains("AnotherTest.java"));
        assertTrue("Should visit GeneratedClass.java", visitedFiles.contains("GeneratedClass.java"));
    }

    // ==================== Integration Tests ====================

    @Test
    public void shouldRespectExcludeOptionDuringDependencyAnalysis() {
        String mainFile = FileUtil.uniqFilePath(TEST_RESOURCES_DIR + "/src/main/java/MainClass.java");
        String helperFile = FileUtil.uniqFilePath(TEST_RESOURCES_DIR + "/src/main/java/HelperClass.java");
        String testFile = FileUtil.uniqFilePath(TEST_RESOURCES_DIR + "/src/test/java/TestClass.java");
        String anotherTestFile = FileUtil.uniqFilePath(TEST_RESOURCES_DIR + "/src/test/java/AnotherTest.java");
        String generatedFile = FileUtil.uniqFilePath(TEST_RESOURCES_DIR + "/build/generated/GeneratedClass.java");
        // Use specific path patterns to avoid matching the src/test/resources path
        String[] excludes = new String[]{"exclude-examples/src/test/**", "exclude-examples/build/**", "*.md", "*.txt"};

        AbstractLangProcessor processor = new JavaProcessor();
        BindingResolver bindingResolver = new BindingResolver(processor, false, true);

        processor.buildDependencies(FileUtil.uniqFilePath(TEST_RESOURCES_DIR), new String[]{}, excludes, bindingResolver);

        // Verify that only main source files are in the entity repo
        assertNotNull("MainClass file should exist in repo", 
                     processor.getEntityRepo().getEntity(mainFile));
        assertNotNull("HelperClass file should exist in repo", 
                     processor.getEntityRepo().getEntity(helperFile));

        // Test files should not exist in repo
        assertNull("TestClass file should NOT exist in repo", 
                  processor.getEntityRepo().getEntity(testFile));
        assertNull("AnotherTest file should NOT exist in repo", 
                  processor.getEntityRepo().getEntity(anotherTestFile));

        // Build files should not exist in repo
        assertNull("GeneratedClass file should NOT exist in repo", 
                  processor.getEntityRepo().getEntity(generatedFile));
    }

    @Test
    public void shouldIncludeAllFilesWhenNoExcludesSpecified() {
        String mainFile = FileUtil.uniqFilePath(TEST_RESOURCES_DIR + "/src/main/java/MainClass.java");
        String helperFile = FileUtil.uniqFilePath(TEST_RESOURCES_DIR + "/src/main/java/HelperClass.java");
        String testFile = FileUtil.uniqFilePath(TEST_RESOURCES_DIR + "/src/test/java/TestClass.java");
        String anotherTestFile = FileUtil.uniqFilePath(TEST_RESOURCES_DIR + "/src/test/java/AnotherTest.java");
        String generatedFile = FileUtil.uniqFilePath(TEST_RESOURCES_DIR + "/build/generated/GeneratedClass.java");

        AbstractLangProcessor processor = new JavaProcessor();
        BindingResolver bindingResolver = new BindingResolver(processor, false, true);

        processor.buildDependencies(FileUtil.uniqFilePath(TEST_RESOURCES_DIR), new String[]{}, new String[]{}, bindingResolver);

        // All files should be in the entity repo
        assertNotNull("MainClass file should exist in repo", 
                     processor.getEntityRepo().getEntity(mainFile));
        assertNotNull("HelperClass file should exist in repo", 
                     processor.getEntityRepo().getEntity(helperFile));
        assertNotNull("TestClass file should exist in repo", 
                     processor.getEntityRepo().getEntity(testFile));
        assertNotNull("AnotherTest file should exist in repo", 
                     processor.getEntityRepo().getEntity(anotherTestFile));
        assertNotNull("GeneratedClass file should exist in repo", 
                     processor.getEntityRepo().getEntity(generatedFile));
    }

    @Test
    public void shouldExcludeOnlyMatchingFiles() {
        String mainFile = FileUtil.uniqFilePath(TEST_RESOURCES_DIR + "/src/main/java/MainClass.java");
        String helperFile = FileUtil.uniqFilePath(TEST_RESOURCES_DIR + "/src/main/java/HelperClass.java");
        String testFile = FileUtil.uniqFilePath(TEST_RESOURCES_DIR + "/src/test/java/TestClass.java");
        String anotherTestFile = FileUtil.uniqFilePath(TEST_RESOURCES_DIR + "/src/test/java/AnotherTest.java");
        // Use specific path pattern to exclude only TestClass.java
        String[] excludes = new String[]{"**/TestClass.java"};

        AbstractLangProcessor processor = new JavaProcessor();
        BindingResolver bindingResolver = new BindingResolver(processor, false, true);

        processor.buildDependencies(FileUtil.uniqFilePath(TEST_RESOURCES_DIR), new String[]{}, excludes, bindingResolver);

        // Non-test files should exist
        assertNotNull("MainClass file should exist in repo", 
                     processor.getEntityRepo().getEntity(mainFile));
        assertNotNull("HelperClass file should exist in repo", 
                     processor.getEntityRepo().getEntity(helperFile));
        assertNotNull("AnotherTest file should exist in repo (not matching pattern)", 
                     processor.getEntityRepo().getEntity(anotherTestFile));

        // Only TestClass.java should be excluded
        assertNull("TestClass file should NOT exist in repo", 
                  processor.getEntityRepo().getEntity(testFile));
    }
}
