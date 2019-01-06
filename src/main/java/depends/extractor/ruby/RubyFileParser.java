package depends.extractor.ruby;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import depends.entity.repo.EntityRepo;
import depends.extractor.FileParser;
public class RubyFileParser implements FileParser {
	private String fileFullPath;
	private EntityRepo entityRepo;
	private ExecutorService executor;

	public RubyFileParser(String fileFullPath, EntityRepo entityRepo, ExecutorService executorService) {
        this.fileFullPath = fileFullPath;
        this.entityRepo = entityRepo;
        this.executor = executorService;
    }

	@Override
	public void parse() throws IOException {
        CharStream input = CharStreams.fromFileName(fileFullPath);
        Lexer lexer = new RubyLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        final Future<RubyParser> handler = executor.submit(new Callable<RubyParser>() {
			@Override
			public RubyParser call() throws Exception {
				return new RubyParser(tokens);
			}
        });
        
        RubyParser parser =null;
        try {
        	parser = handler.get(15000, TimeUnit.MILLISECONDS);
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
        	System.err.println("time out of parse error in " + fileFullPath); 
        	handler.cancel(true);
        }
        if (parser==null) {
        	System.err.println("parse error in " + fileFullPath); 
        	return;
        }
        RubyListener bridge = new RubyListener(fileFullPath, entityRepo);
	    ParseTreeWalker walker = new ParseTreeWalker();
	    walker.walk(bridge, parser.prog());
	}

}
