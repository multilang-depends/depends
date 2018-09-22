package depends.extractor.java;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import depends.entity.repo.EntityRepo;
import depends.javaextractor.Java9Lexer;
import depends.javaextractor.Java9Parser;


public class JavaFileParser implements depends.extractor.FileParser{
	private String fileFullPath;
	private EntityRepo entityRepo;
	public JavaFileParser(String fileFullPath,EntityRepo entityRepo) {
        this.fileFullPath = fileFullPath;
        this.entityRepo = entityRepo;
	}
	
	
	@Override
	public void parse() throws IOException {
        CharStream input = CharStreams.fromFileName(fileFullPath);
        Lexer lexer = new Java9Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Java9Parser parser = new Java9Parser(tokens);
        JavaAdapterListener bridge = new JavaAdapterListener(fileFullPath, entityRepo);
	    ParseTreeWalker walker = new ParseTreeWalker();
	    walker.walk(bridge, parser.compilationUnit());
    }

}
