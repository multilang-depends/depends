package depends.extractor.ruby;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import depends.entity.repo.EntityRepo;
import depends.extractor.FileParser;
public class RubyFileParser implements FileParser {
	private static final long MAX_PARSE_TIME_PER_FILE = 30000L;
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
        final Future<RuleContext> handler = executor.submit(new Callable<RuleContext>() {
			@Override
			public RuleContext call() throws Exception {
					RubyParser parser = new RubyParser(tokens);
					return parser.prog();
			}				
			
			/* The following function is try to optimize performance with SLL mode, but
			 * the actual result shows that there is no improvement
			 * so we remove it again */
			@SuppressWarnings("unused")
			public RuleContext callWithFallBack() throws Exception {
				RubyParser parser = new RubyParser(tokens);
			    parser.setErrorHandler(new BailErrorStrategy());
				parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
				try {
					return parser.prog();
				} catch (final ParseCancellationException e) {

					// fall-back to LL mode parsing if SLL fails
					tokens.reset();
					parser.reset();
					parser.removeErrorListeners();
				    parser.setErrorHandler(new DefaultErrorStrategy());
					parser.getInterpreter().setPredictionMode(PredictionMode.LL);
					return parser.prog();
				}				
			}
        });
        
        RuleContext rootContext =null;
        try {
        	rootContext = handler.get(MAX_PARSE_TIME_PER_FILE, TimeUnit.MILLISECONDS);
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
        	System.err.println("time out of parse error in " + fileFullPath); 
        	handler.cancel(true);
        	return;
        }
        if (rootContext==null) {
        	System.err.println("parse error in " + fileFullPath); 
        	return;
        }
        RubyListener bridge = new RubyListener(fileFullPath, entityRepo);
	    ParseTreeWalker walker = new ParseTreeWalker();
	    walker.walk(bridge, rootContext);
	}

}
