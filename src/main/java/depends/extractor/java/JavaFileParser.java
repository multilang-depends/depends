package depends.extractor.java;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import depends.entity.repo.EntityRepo;
import depends.extractor.BuiltInTypeIdenfier;
import depends.javaextractor.JavaLexer;
import depends.javaextractor.JavaParser;


public class JavaFileParser implements depends.extractor.FileParser{
	private String fileFullPath;
	private EntityRepo entityRepo;
	
	public class BuiltInType extends BuiltInTypeIdenfier{
		
		public BuiltInType() {
	        super.createBuiltInTypes();
		}
		
		@Override
		public String[] getBuiltInTypeStr() {
			return new String[]{
					"void","int","double","char","byte","boolean","long","short","float",
					"BigDecimal","Integer","Double","Char","Byte","Boolean","Long","Short","Float",
					"String","Object","Class","Exception","StringBuilder",
					"<Built-in>"
			};
		}
		@Override
		public String[] getBuiltInPrefixStr() {
			return new String[]{
					"java.","javax.","com.sun."
			};
		}
		
	}
	public JavaFileParser(String fileFullPath,EntityRepo entityRepo) {
        this.fileFullPath = fileFullPath;
        this.entityRepo = entityRepo;
        entityRepo.setBuiltInTypeIdentifier(new BuiltInType());
	}


	public void parse() throws IOException {
        CharStream input = CharStreams.fromFileName(fileFullPath);
        Lexer lexer = new JavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        JavaEntitiesListener bridge = new JavaEntitiesListener(fileFullPath, entityRepo);
	    ParseTreeWalker walker = new ParseTreeWalker();
	    walker.walk(bridge, parser.compilationUnit());
    }





	
}
