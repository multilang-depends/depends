package depends.extractor.cpp.g4;

import java.io.IOException;
import java.util.List;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import depends.entity.repo.EntityRepo;
import depends.extractor.cpp.CppFileParser;
import depends.javaextractor.CElsaBaseListener;
import depends.javaextractor.CElsaLexer;
import depends.javaextractor.CElsaParser;

public class Antlr4CppFileParser extends CppFileParser {

	public Antlr4CppFileParser(String fileFullPath, EntityRepo entityRepo, List<String> includePaths) {
		super(fileFullPath, entityRepo);
	}

	@Override
	public void parse() throws IOException {
		CharStream input = CharStreams.fromFileName(fileFullPath);
		Lexer lexer = new CElsaLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CElsaParser parser = new CElsaParser(tokens);
		//parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
		ParseTree tree = null;
		try {
		    tree = parser.translationUnit();  // STAGE 1
		}
		catch (Exception ex) {
			System.out.print("reset");
		    tokens.reset(); // rewind input stream
		    parser.reset();
		    parser.getInterpreter().setPredictionMode(PredictionMode.LL);
		    tree = parser.translationUnit();  // STAGE 2
		}
		CppEntitiesListener bridge = new CppEntitiesListener(tokens,fileFullPath, entityRepo);
		ParseTreeWalker walker = new ParseTreeWalker();
		walker.walk(bridge, tree);
	}

}
