package depends.extractor.python;

import java.util.ArrayList;
import java.util.List;

import depends.entity.repo.EntityRepo;
import depends.extractor.FileParser;
import depends.extractor.ParserCreator;
import depends.extractor.ParserTest;
import depends.extractor.python.union.PythonFileParser;
import depends.extractor.python.union.PythonProcessor;
import depends.relations.Inferer;
import multilang.depends.util.file.FileUtil;
import multilang.depends.util.file.TemporaryFile;

public abstract class PythonParserTest extends ParserTest implements ParserCreator {

	protected EntityRepo repo;
	private PythonProcessor p;
	protected Inferer inferer;

	public void init() {
		List<String> includeDir = new ArrayList<>();
		includeDir.add("./src/test/resources/python-code-examples/");
		this.p = new PythonProcessor();
		p.includeDirs = includeDir.toArray(new String[] {});
		
		this.repo = p.getEntityRepo();
		this.inferer = p.inferer;
    	TemporaryFile.reset();
		
    }
	
	public PythonFileParser createParser(String src) {
		return (PythonFileParser)createFileParser(src);
	}
	
	@Override
	public FileParser createFileParser(String src) {
		return  p.createFileParser(FileUtil.uniqFilePath(src));
	}
	
	protected String withPackageName(String theFile,String entityName) {
		return FileUtil.uniqFilePath(FileUtil.getLocatedDir(theFile))+"."+entityName;
	}
}
