package depends.extractor.cpp.g4;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.Test;

import depends.entity.repo.EntityRepo;
import depends.javaextractor.CPP14BaseListener;
import depends.javaextractor.CPP14Lexer;
import depends.javaextractor.CPP14Parser;
import depends.javaextractor.CPreprocessorBaseListener;
import depends.javaextractor.CPreprocessorLexer;
import depends.javaextractor.CPreprocessorParser;
import depends.javaextractor.CPreprocessorParser.Pp_defineContext;
import depends.javaextractor.CPreprocessorParser.Pp_includeContext;


public class PreprocessorHandlerTest {
	class CPreprocessorListener extends CPreprocessorBaseListener{

		@Override
		public void enterPp_define(Pp_defineContext ctx) {
			for (TerminalNode id:ctx.ID()) {
				System.out.println("define " + id.getText());
			}
			if (ctx.token_sequence()!=null)
			System.out.println(" as " + ctx.token_sequence().getText());
			super.enterPp_define(ctx);
		}

		@Override
		public void enterPp_include(Pp_includeContext ctx) {
			if (ctx.token_sequence()!=null)
				System.out.println(" as " + ctx.token_sequence().getText());
			super.enterPp_include(ctx);
		}

		
		
		
	}
	@Test
	public void testPreprocessor() throws IOException {
        String src = "./src/test/resources/cpp-code-examples/includesTest/PreprocessorTest.cpp";
        
		CharStream input = CharStreams.fromFileName(src);
		Lexer lexer = new CPreprocessorLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CPreprocessorParser parser = new CPreprocessorParser(tokens);
		
		ParseTreeWalker walker = new ParseTreeWalker();
		ParseTreeListener visitor = new CPreprocessorListener() ;
		walker.walk(visitor, parser.translation_unit());
		
		
        
	}

	@Test
	public void testG4ContainsPreprocessor() throws IOException {
        String src = "./src/test/resources/cpp-code-examples/includesTest/PreprocessorTest.cpp";
        
		CharStream input = CharStreams.fromFileName(src);
		Lexer lexer = new CPP14Lexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CPP14Parser parser = new CPP14Parser(tokens);
		
		ParseTreeWalker walker = new ParseTreeWalker();
		ParseTreeListener visitor = new CPP14BaseListener() ;
		walker.walk(visitor, parser.translationunit());
	}
}
