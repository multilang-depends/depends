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

import depends.javaextractor.CElsaBaseListener;
import depends.javaextractor.CElsaLexer;
import depends.javaextractor.CElsaParser;
import depends.javaextractor.CPP14Lexer;
import depends.javaextractor.CPP14Parser;


public class G4GrammarTest {
	@Test
	public void testTypedef() throws IOException {
        String src = "./src/test/resources/cpp-code-examples/g4GrammarTest/testTypeDef.h";
		CharStream input = CharStreams.fromFileName(src);
		Lexer lexer = new CElsaLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CElsaParser parser = new CElsaParser(tokens);
		parser.translationUnit();  
	}
	
	@Test
	public void testTemplateMethodCall() throws IOException {
        String src = "./src/test/resources/cpp-code-examples/g4GrammarTest/TempateMethodCall.c";
		CharStream input = CharStreams.fromFileName(src);
		Lexer lexer = new CElsaLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CElsaParser parser = new CElsaParser(tokens);
		parser.translationUnit();  
	}
	
	
	@Test
	public void testOperatorFunc() throws IOException {
        String src = "./src/test/resources/cpp-code-examples/g4GrammarTest/OperatorFunc.c";
		CharStream input = CharStreams.fromFileName(src);
		Lexer lexer = new CElsaLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CElsaParser parser = new CElsaParser(tokens);
		parser.translationUnit();  
	}
	
	
	@Test
	public void testOperatorFunc14() throws IOException {
        String src = "./src/test/resources/cpp-code-examples/g4GrammarTest/OperatorFunc.c";
		CharStream input = CharStreams.fromFileName(src);
		Lexer lexer = new CPP14Lexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CPP14Parser parser = new CPP14Parser(tokens);
		parser.translationunit();  
	}
	
	
	@Test
	public void testForLoop() throws IOException {
        String src = "./src/test/resources/cpp-code-examples/g4GrammarTest/ExpressionListInForLoop.c";
		CharStream input = CharStreams.fromFileName(src);
		Lexer lexer = new CElsaLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CElsaParser parser = new CElsaParser(tokens);
		parser.translationUnit();  
	}
	
	
	@Test
	public void testTypedefWith2Stage_ParseErrorNotSuccess() throws IOException {
        String src = "./src/test/resources/cpp-code-examples/g4GrammarTest/testTypeDef.h";
		CharStream input = CharStreams.fromFileName(src);
		Lexer lexer = new CElsaLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CElsaParser parser = new CElsaParser(tokens);
		//parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
		try {
		    parser.translationUnit();  // STAGE 1
		}
		catch (Exception ex) {
			System.out.println("enter stage2");
		    tokens.reset(); // rewind input stream
		    parser.reset();
		    parser.getInterpreter().setPredictionMode(PredictionMode.LL);
		     parser.translationUnit();  // STAGE 2
		}
	}
	
	@Test
	public void testStringBlock() throws IOException {
        String src = "./src/test/resources/cpp-code-examples/g4GrammarTest/StringBlockTest.h";
		CharStream input = CharStreams.fromFileName(src);
		Lexer lexer = new CElsaLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CElsaParser parser = new CElsaParser(tokens);
		
		ParseTreeWalker walker = new ParseTreeWalker();
		ParseTreeListener visitor = new CElsaBaseListener() ;
		walker.walk(visitor, parser.translationUnit());
	}
}
