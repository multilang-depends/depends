package depends.extractor.cpp.g4;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import depends.javaextractor.CPP14BaseListener;
import depends.javaextractor.CPP14Lexer;
import depends.javaextractor.CPP14Parser;


public class G4GrammarTest {
	@Test
	public void testTypedef() throws IOException {
        String src = "./src/test/resources/cpp-code-examples/g4GrammarTest/testTypeDef.h";
		CharStream input = CharStreams.fromFileName(src);
		Lexer lexer = new CPP14Lexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CPP14Parser parser = new CPP14Parser(tokens);
		parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
		ParseTreeWalker walker = new ParseTreeWalker();
		ParseTreeListener visitor = new CPP14BaseListener() ;
		walker.walk(visitor, parser.translationunit());
		try {
		    parser.translationunit();  // STAGE 1
		}
		catch (Exception ex) {
			System.out.println("enter stage2");
		    tokens.reset(); // rewind input stream
		    parser.reset();
		    parser.getInterpreter().setPredictionMode(PredictionMode.LL);
		     parser.translationunit();  // STAGE 2
		}
	}
	
	@Test
	public void testStringBlock() throws IOException {
        String src = "./src/test/resources/cpp-code-examples/g4GrammarTest/StringBlockTest.h";
		CharStream input = CharStreams.fromFileName(src);
		Lexer lexer = new CPP14Lexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CPP14Parser parser = new CPP14Parser(tokens);
		
		ParseTreeWalker walker = new ParseTreeWalker();
		ParseTreeListener visitor = new CPP14BaseListener() ;
		walker.walk(visitor, parser.translationunit());
	}
}
