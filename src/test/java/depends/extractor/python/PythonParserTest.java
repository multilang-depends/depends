package depends.extractor.python;

import depends.extractor.FileParser;
import depends.extractor.ParserCreator;
import depends.extractor.ParserTest;
import depends.extractor.python.union.PythonFileParser;
import depends.extractor.python.union.PythonProcessor;
import multilang.depends.util.file.FileUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class PythonParserTest extends ParserTest implements ParserCreator {
	public void init() {
		List<String> includeDir = new ArrayList<>();
		includeDir.add("./src/test/resources/python-code-examples/");
		this.langProcessor = new PythonProcessor();
		langProcessor.includeDirs = includeDir.toArray(new String[] {});
		super.init(true);
    }
	
	public PythonFileParser createParser() {
		return (PythonFileParser)createFileParser();
	}
	
	@Override
	public FileParser createFileParser() {
		return  langProcessor.createFileParser();
	}
	
	protected String withPackageName(String theFile,String entityName) {
		return FileUtil.uniqFilePath(FileUtil.getLocatedDir(theFile))+"."+entityName;
	}


}
