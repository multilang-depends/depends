package depends.extractor.ruby;

import java.io.IOException;

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

	public RubyFileParser(String fileFullPath, EntityRepo entityRepo) {
        this.fileFullPath = fileFullPath;
        this.entityRepo = entityRepo;
    }

	@Override
	public void parse() throws IOException {
        CharStream input = CharStreams.fromFileName(fileFullPath);
        Lexer lexer = new RubyLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RubyParser parser = new RubyParser(tokens);
        RubyListener bridge = new RubyListener(fileFullPath, entityRepo);
	    ParseTreeWalker walker = new ParseTreeWalker();
	    walker.walk(bridge, parser.prog());
	}

}
