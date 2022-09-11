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

package depends.extractor.pom;

import depends.entity.repo.EntityRepo;
import depends.extractor.FileParser;
import depends.extractor.xml.XMLLexer;
import depends.extractor.xml.XMLParser;
import depends.relations.IBindingResolver;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.util.List;

public class PomFileParser extends FileParser {
	private PomProcessor parseCreator;
	private List<String> includePaths;
	private IBindingResolver bindingResolver;

	public PomFileParser(EntityRepo entityRepo, List<String> includePaths, PomProcessor pomProcessor, IBindingResolver bindingResolver) {
        this.entityRepo = entityRepo;
        this.parseCreator = pomProcessor;
        this.includePaths = includePaths;
        this.bindingResolver = bindingResolver;
	}

	@Override

	protected void parseFile(String fileFullPath) throws IOException {
        CharStream input = CharStreams.fromFileName(fileFullPath);
        Lexer lexer = new XMLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        XMLParser parser = new XMLParser(tokens);
        PomListener bridge = new PomListener(fileFullPath, entityRepo, includePaths,parseCreator, bindingResolver);
	    ParseTreeWalker walker = new ParseTreeWalker();
	    walker.walk(bridge, parser.document());
	}

}
