package depends.extractor.java;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import depends.entity.repo.EntityRepo;
import depends.javaextractor.JavaLexer;
import depends.javaextractor.JavaParser;


public class JavaFileParser extends depends.extractor.FileParser{
	private String fileFullPath;
	private EntityRepo entityRepo;
	public JavaFileParser(String fileFullPath,EntityRepo entityRepo) {
        this.fileFullPath = fileFullPath;
        this.entityRepo = entityRepo;
        entityRepo.addBuiltIn(this);
        super.createBuiltInTypes();
	}


	@Override
	public void parse() throws IOException {
        CharStream input = CharStreams.fromFileName(fileFullPath);
        Lexer lexer = new JavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        JavaEntitiesListener bridge = new JavaEntitiesListener(fileFullPath, entityRepo);
	    ParseTreeWalker walker = new ParseTreeWalker();
	    walker.walk(bridge, parser.compilationUnit());
    }

	@Override
	public String[] getBuiltInTypeStr() {
		return new String[]{
				"void","int","double","char","byte","boolean","long","short","float",
				"BigDecimal","Integer","Double","Char","Byte","Boolean","Long","Short","Float",
				"String","Object","Class","Exception"
		};
	}
	@Override
	public String[] getBuiltInPrefixStr() {
		return new String[]{
				"java.","javax."
		};
	}

	
}
