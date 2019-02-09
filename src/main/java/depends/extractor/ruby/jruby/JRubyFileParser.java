package depends.extractor.ruby.jruby;

import java.io.IOException;
import java.io.StringReader;
import java.util.concurrent.ExecutorService;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.jrubyparser.CompatVersion;
import org.jrubyparser.Parser;
import org.jrubyparser.ast.Node;
import org.jrubyparser.parser.ParserConfiguration;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.FileParser;
import depends.extractor.ParserCreator;
import depends.extractor.ruby.IncludedFileLocator;
import depends.relations.Inferer;
import depends.util.FileUtil;
public class JRubyFileParser implements FileParser {
	private static final long MAX_PARSE_TIME_PER_FILE = 180000L;
	private String fileFullPath;
	private EntityRepo entityRepo;
	private ExecutorService executor;
	private IncludedFileLocator includesFileLocator;
	private Inferer inferer;
	private ParserCreator parserCreator;

	public JRubyFileParser(String fileFullPath, EntityRepo entityRepo, 
			ExecutorService executorService, 
			IncludedFileLocator includesFileLocator, 
			Inferer inferer, ParserCreator parserCreator) {
        this.fileFullPath  = FileUtil.uniqFilePath(fileFullPath);
        this.entityRepo = entityRepo;
        this.executor = executorService;
        this.includesFileLocator = includesFileLocator;
        this.inferer = inferer;
        this.parserCreator = parserCreator;
    }

	@Override
	public void parse() throws IOException {
		/** If file already exist, skip it */
		Entity fileEntity = entityRepo.getEntity(fileFullPath);
		if (fileEntity!=null && fileEntity instanceof FileEntity) {
			return;
		}
		
        CharStream input = CharStreams.fromFileName(fileFullPath);
		Parser rubyParser = new Parser();
		StringReader in = new StringReader(input.toString());
		CompatVersion version = CompatVersion.RUBY2_3;
		ParserConfiguration config = new ParserConfiguration(0, version);
		Node node = rubyParser.parse("<code>", in, config);
		node.accept(new JRubyVisitor(fileFullPath, entityRepo, includesFileLocator,executor,inferer,parserCreator));
		fileEntity = entityRepo.getEntity(fileFullPath);
		if (fileEntity!=null) {
			fileEntity.inferEntities(inferer);
		}
	}

}
