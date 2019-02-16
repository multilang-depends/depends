package depends.extractor.pom;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import depends.entity.repo.EntityRepo;
import depends.extractor.FileParser;
import depends.extractor.xml.XMLLexer;
import depends.extractor.xml.XMLParser;

public class PomFileParser implements FileParser {

	private String fileFullPath;
	private EntityRepo entityRepo;

	public PomFileParser(String fileFullPath, EntityRepo entityRepo) {
        this.fileFullPath = fileFullPath;
        this.entityRepo = entityRepo;
	}

	@Override
	public void parse() throws IOException {
        CharStream input = CharStreams.fromFileName(fileFullPath);
        Lexer lexer = new XMLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        XMLParser parser = new XMLParser(tokens);
        PomListener bridge = new PomListener(fileFullPath, entityRepo);
	    ParseTreeWalker walker = new ParseTreeWalker();
	    walker.walk(bridge, parser.document());
	}

}
