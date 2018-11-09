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
import depends.javaextractor.CPP14BaseListener;
import depends.javaextractor.CPP14Lexer;
import depends.javaextractor.CPP14Parser;

public class Antlr4CppFileParser extends CppFileParser {

	public Antlr4CppFileParser(String fileFullPath, EntityRepo entityRepo, List<String> includePaths) {
		super(fileFullPath, entityRepo);
	}

	@Override
	public void parse() throws IOException {
		CharStream input = CharStreams.fromFileName(fileFullPath);
		Lexer lexer = new CPP14Lexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CPP14Parser parser = new CPP14Parser(tokens);
		parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
		ParseTree tree = null;
		try {
		    tree = parser.translationunit();  // STAGE 1
		}
		catch (Exception ex) {
		    tokens.reset(); // rewind input stream
		    parser.reset();
		    parser.getInterpreter().setPredictionMode(PredictionMode.LL);
		    tree = parser.translationunit();  // STAGE 2
		}
		CppEntitiesListener bridge = new CppEntitiesListener(tokens,fileFullPath, entityRepo);
		ParseTreeWalker walker = new ParseTreeWalker();
		walker.walk(bridge, tree);
	}

}
