package depends.extractor.cpp.g4;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import depends.entity.repo.EntityRepo;
import depends.extractor.cpp.CppFileParser;
import depends.javaextractor.CPP14Lexer;
import depends.javaextractor.CPP14Parser;

public class Antlr4CppFileParser extends CppFileParser {

	public Antlr4CppFileParser(String fileFullPath, EntityRepo entityRepo) {
		super(fileFullPath, entityRepo);
	}

	@Override
	public void parse() throws IOException {
		CharStream input = CharStreams.fromFileName(fileFullPath);
		Lexer lexer = new CPP14Lexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CPP14Parser parser = new CPP14Parser(tokens);
		CppEntitiesListener bridge = new CppEntitiesListener(fileFullPath, entityRepo);
		ParseTreeWalker walker = new ParseTreeWalker();
		walker.walk(bridge, parser.translationunit());

	}

}
