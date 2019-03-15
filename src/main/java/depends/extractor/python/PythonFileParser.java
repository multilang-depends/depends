package depends.extractor.python;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import depends.entity.repo.EntityRepo;
import depends.extractor.FileParser;
import depends.extractor.ruby.IncludedFileLocator;
import depends.relations.Inferer;

public class PythonFileParser implements FileParser {

	private String fileFullPath;
	private EntityRepo entityRepo;
	private Inferer inferer;

	public PythonFileParser(String fileFullPath, EntityRepo entityRepo, IncludedFileLocator includedFileLocator,
			Inferer inferer, PythonProcessor pythonProcessor) {
		this.fileFullPath = fileFullPath;
		this.entityRepo = entityRepo;
		this.inferer = inferer;
	}

	@Override
	public void parse() throws IOException {
        CharStream input = CharStreams.fromFileName(fileFullPath);
        Lexer lexer = new Python3Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Python3Parser parser = new Python3Parser(tokens);
        Python3CodeListener bridge = new Python3CodeListener(fileFullPath, entityRepo,inferer);
	    ParseTreeWalker walker = new ParseTreeWalker();
	    walker.walk(bridge, parser.file_input());
	}

}
