/*
MIT License

Copyright (c) 2018-2019 Gang ZHANG

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package depends.extractor.java;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.java.JavaLexer;
import depends.extractor.java.JavaParser;
import depends.relations.Inferer;


public class JavaFileParser implements depends.extractor.FileParser{
	private String fileFullPath;
	private EntityRepo entityRepo;
	private Inferer inferer;
	public JavaFileParser(String fileFullPath,EntityRepo entityRepo, Inferer inferer) {
        this.fileFullPath = fileFullPath;
        this.entityRepo = entityRepo;
        this.inferer = inferer;
	}

	@Override
	public void parse() throws IOException {
        CharStream input = CharStreams.fromFileName(fileFullPath);
        Lexer lexer = new JavaLexer(input);
        lexer.setInterpreter(new LexerATNSimulator(lexer, lexer.getATN(), lexer.getInterpreter().decisionToDFA, new PredictionContextCache()));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        ParserATNSimulator interpreter = new ParserATNSimulator(parser, parser.getATN(), parser.getInterpreter().decisionToDFA, new PredictionContextCache());
        parser.setInterpreter(interpreter);
        JavaListener bridge = new JavaListener(fileFullPath, entityRepo,inferer);
	    ParseTreeWalker walker = new ParseTreeWalker();
	    try {
	    	walker.walk(bridge, parser.compilationUnit());
			Entity fileEntity = entityRepo.getEntity(fileFullPath);
			((FileEntity)fileEntity).cacheAllExpressions();
			interpreter.clearDFA();
			bridge.done();
	    	
	    }catch (Exception e) {
	    	System.err.println("error encountered during parse..." );
	    	e.printStackTrace();
	    }
	    
    }
	
}
