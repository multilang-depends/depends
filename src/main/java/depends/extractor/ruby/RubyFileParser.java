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

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.FileParser;
import depends.relations.Inferer;
import depends.util.FileUtil;
public class RubyFileParser implements FileParser {
	private static final long MAX_PARSE_TIME_PER_FILE = 180000L;
	private String fileFullPath;
	private EntityRepo entityRepo;
	private ExecutorService executor;
	private IncludedFileLocator includesFileLocator;
	private Inferer inferer;

	public RubyFileParser(String fileFullPath, EntityRepo entityRepo, 
			ExecutorService executorService, 
			IncludedFileLocator includesFileLocator, 
			Inferer inferer) {
        this.fileFullPath  = FileUtil.uniqFilePath(fileFullPath);
        this.entityRepo = entityRepo;
        this.executor = executorService;
        this.includesFileLocator = includesFileLocator;
        this.inferer = inferer;
    }

	@Override
	public void parse() throws IOException {
		/** If file already exist, skip it */
		Entity fileEntity = entityRepo.getEntity(fileFullPath);
		if (fileEntity!=null && fileEntity instanceof FileEntity) {
			return;
		}
		
        CharStream input = CharStreams.fromFileName(fileFullPath);
        Lexer lexer = new RubyLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        final Future<RuleContext> handler = executor.submit(new Callable<RuleContext>() {
			@Override
			public RuleContext call() throws Exception {
					RubyParser parser = new RubyParser(tokens);
					return parser.compilation_unit();
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
					return parser.compilation_unit();
				} catch (final ParseCancellationException e) {

					// fall-back to LL mode parsing if SLL fails
					tokens.reset();
					parser.reset();
					parser.removeErrorListeners();
				    parser.setErrorHandler(new DefaultErrorStrategy());
					parser.getInterpreter().setPredictionMode(PredictionMode.LL);
					return parser.compilation_unit();
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
        RubyListener bridge = new RubyListener(fileFullPath, entityRepo, includesFileLocator,executor,inferer);
	    ParseTreeWalker walker = new ParseTreeWalker();
	    walker.walk(bridge, rootContext);
		fileEntity = entityRepo.getEntity(fileFullPath);
		if (fileEntity!=null) {
			fileEntity.inferEntities(inferer);
		}
	}

}
