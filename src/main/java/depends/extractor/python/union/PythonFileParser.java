package depends.extractor.python.union;

import depends.entity.repo.EntityRepo;
import depends.extractor.FileParser;
import depends.extractor.IncludedFileLocator;
import depends.extractor.python.PythonLexer;
import depends.extractor.python.PythonParser;
import depends.relations.IBindingResolver;
import multilang.depends.util.file.FileUtil;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;

public class PythonFileParser extends FileParser {
	private IBindingResolver bindingResolver;
	private IncludedFileLocator includeFileLocator;
	private PythonProcessor processor;

	public PythonFileParser(EntityRepo entityRepo, IncludedFileLocator includeFileLocator,
							IBindingResolver bindingResolver, PythonProcessor pythonProcessor) {
		this.entityRepo = entityRepo;
		this.bindingResolver = bindingResolver;
		this.includeFileLocator = includeFileLocator;
		this.processor = pythonProcessor;
	}

	@Override
	protected void parseFile(String fileFullPath) throws IOException {
		fileFullPath = FileUtil.uniqFilePath(fileFullPath);
        CharStream input = CharStreams.fromFileName(fileFullPath);
        Lexer lexer = new PythonLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        
        
        PythonParser parser = new PythonParser(tokens);
        PythonCodeListener bridge = new PythonCodeListener(fileFullPath, entityRepo, bindingResolver, includeFileLocator, processor);
	    ParseTreeWalker walker = new ParseTreeWalker();
	    walker.walk(bridge, parser.file_input());
	}

}
