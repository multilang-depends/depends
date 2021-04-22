package depends.extractor.python.union;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.FileParser;
import depends.extractor.python.PythonLexer;
import depends.extractor.python.PythonParser;
import depends.extractor.ruby.IncludedFileLocator;
import depends.relations.IBindingResolver;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;

public class PythonFileParser implements FileParser {

	private String fileFullPath;
	private EntityRepo entityRepo;
	private IBindingResolver bindingResolver;
	private IncludedFileLocator includeFileLocator;
	private PythonProcessor processor;

	public PythonFileParser(String fileFullPath, EntityRepo entityRepo, IncludedFileLocator includeFileLocator,
							IBindingResolver bindingResolver, PythonProcessor pythonProcessor) {
		this.fileFullPath = fileFullPath;
		this.entityRepo = entityRepo;
		this.bindingResolver = bindingResolver;
		this.includeFileLocator = includeFileLocator;
		this.processor = pythonProcessor;
	}

	@Override
	public void parse() throws IOException {
		/** If file already exist, skip it */
		Entity fileEntity = entityRepo.getEntity(fileFullPath);
		if (fileEntity!=null && fileEntity instanceof FileEntity) {
			return;
		}
        CharStream input = CharStreams.fromFileName(fileFullPath);
        Lexer lexer = new PythonLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        
        
        PythonParser parser = new PythonParser(tokens);
        PythonCodeListener bridge = new PythonCodeListener(fileFullPath, entityRepo, bindingResolver, includeFileLocator, processor);
	    ParseTreeWalker walker = new ParseTreeWalker();
	    walker.walk(bridge, parser.file_input());
		fileEntity = entityRepo.getEntity(fileFullPath);
		((FileEntity)fileEntity).cacheAllExpressions();
		bridge.done();
	}

}
