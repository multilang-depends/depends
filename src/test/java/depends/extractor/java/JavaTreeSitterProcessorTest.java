package depends.extractor.java;

import depends.LangRegister;
import depends.extractor.FileParser;
import depends.extractor.LangProcessorRegistration;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class JavaTreeSitterProcessorTest {
    @Test
    public void should_register_java_ts_language() {
        new LangRegister();
        assertNotNull(LangProcessorRegistration.getRegistry().getProcessorOf("java-ts"));
    }

    @Test
    public void should_report_java_ts_as_supported_language() {
        JavaTreeSitterProcessor processor = new JavaTreeSitterProcessor();
        assertEquals("java-ts", processor.supportedLanguage());
    }

    @Test
    public void should_create_java_ts_file_parser() {
        JavaTreeSitterProcessor processor = new JavaTreeSitterProcessor();
        FileParser parser = processor.createFileParser();
        assertTrue(parser instanceof JavaTreeSitterFileParser);
    }
}
