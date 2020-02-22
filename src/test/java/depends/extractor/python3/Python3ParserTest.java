package depends.extractor.python3;

import java.util.ArrayList;
import java.util.List;

import depends.entity.repo.EntityRepo;
import depends.extractor.FileParser;
import depends.extractor.ParserCreator;
import depends.extractor.ParserTest;
import depends.extractor.python.py3.Python3FileParser;
import depends.extractor.python.py3.Python3Processor;
import depends.relations.Inferer;
import depends.util.FileUtil;
import depends.util.TemporaryFile;

public abstract class Python3ParserTest extends ParserTest implements ParserCreator {

	protected EntityRepo repo;
	private Python3Processor p;
	protected Inferer inferer;

	public void init() {
		List<String> includeDir = new ArrayList<>();
		includeDir.add("./src/test/resources/python-code-examples/");
		this.p = new Python3Processor();
		p.includeDirs = includeDir.toArray(new String[] {});
		
		this.repo = p.getEntityRepo();
		this.inferer = p.inferer;
    	TemporaryFile.reset();
		
    }
	
	public Python3FileParser createParser(String src) {
		return (Python3FileParser)createFileParser(src);
	}
	
	@Override
	public FileParser createFileParser(String src) {
		return  p.createFileParser(FileUtil.uniqFilePath(src));
	}
	
	protected String withPackageName(String theFile,String entityName) {
		return FileUtil.uniqFilePath(FileUtil.getLocatedDir(theFile))+"."+entityName;
	}
}
