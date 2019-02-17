package depends.extractor.pom;

import java.io.IOException;
import java.util.List;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.FileParser;
import depends.extractor.xml.XMLLexer;
import depends.extractor.xml.XMLParser;
import depends.relations.Inferer;

public class PomFileParser implements FileParser {

	private String fileFullPath;
	private EntityRepo entityRepo;
	private PomProcessor parseCreator;
	private List<String> includePaths;
	private Inferer inferer;

	public PomFileParser(String fileFullPath, EntityRepo entityRepo, List<String> includePaths, PomProcessor pomProcessor,Inferer inferer) {
        this.fileFullPath = fileFullPath;
        this.entityRepo = entityRepo;
        this.parseCreator = pomProcessor;
        this.includePaths = includePaths;
        this.inferer = inferer;
	}

	@Override
	public void parse() throws IOException {
		/* If file already exist, skip it */
		Entity fileEntity = entityRepo.getEntity(fileFullPath);
		if (fileEntity!=null && fileEntity instanceof FileEntity) {
			return;
		}
		/*parse file*/
        CharStream input = CharStreams.fromFileName(fileFullPath);
        Lexer lexer = new XMLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        XMLParser parser = new XMLParser(tokens);
        PomListener bridge = new PomListener(fileFullPath, entityRepo, includePaths,parseCreator,inferer);
	    ParseTreeWalker walker = new ParseTreeWalker();
	    walker.walk(bridge, parser.document());
		fileEntity = entityRepo.getEntity(fileFullPath);
		fileEntity.inferEntities(inferer);
	}

}
