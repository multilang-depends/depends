package depends.extractor.python;

import depends.entity.repo.EntityRepo;
import depends.extractor.*;
import depends.extractor.python.union.PythonFileParser;
import depends.extractor.python.union.PythonProcessor;
import depends.relations.Inferer;
import multilang.depends.util.file.FileUtil;
import multilang.depends.util.file.TemporaryFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class PythonParserTest extends ParserTest implements ParserCreator {

	protected EntityRepo repo;
	protected PythonProcessor langProcessor;
	protected Inferer inferer;

	public void init() {
		List<String> includeDir = new ArrayList<>();
		includeDir.add("./src/test/resources/python-code-examples/");
		this.langProcessor = new PythonProcessor();
		langProcessor.includeDirs = includeDir.toArray(new String[] {});
		
		this.repo = langProcessor.getEntityRepo();
		this.inferer = langProcessor.inferer;
    	TemporaryFile.reset();
		
    }
	
	public PythonFileParser createParser(String src) {
		return (PythonFileParser)createFileParser(src);
	}
	
	@Override
	public FileParser createFileParser(String src) {
		return  langProcessor.createFileParser(FileUtil.uniqFilePath(src));
	}
	
	protected String withPackageName(String theFile,String entityName) {
		return FileUtil.uniqFilePath(FileUtil.getLocatedDir(theFile))+"."+entityName;
	}

	public Set<UnsolvedBindings> resolveAllBindings() {
		return inferer.resolveAllBindings(false, langProcessor);
	}
}
