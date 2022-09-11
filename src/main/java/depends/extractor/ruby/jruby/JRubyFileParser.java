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

package depends.extractor.ruby.jruby;

import depends.entity.repo.EntityRepo;
import depends.extractor.FileParser;
import depends.extractor.IncludedFileLocator;
import depends.extractor.ParserCreator;
import depends.relations.IBindingResolver;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.jrubyparser.CompatVersion;
import org.jrubyparser.Parser;
import org.jrubyparser.ast.Node;
import org.jrubyparser.lexer.SyntaxException;
import org.jrubyparser.parser.ParserConfiguration;

import java.io.IOException;
import java.io.StringReader;
public class JRubyFileParser extends FileParser {
	private IncludedFileLocator includesFileLocator;
	private IBindingResolver bindingResolver;
	private ParserCreator parserCreator;

	public JRubyFileParser( EntityRepo entityRepo,
                           IncludedFileLocator includesFileLocator,
                           IBindingResolver bindingResolver, ParserCreator parserCreator) {
        this.entityRepo = entityRepo;
        this.includesFileLocator = includesFileLocator;
        this.bindingResolver = bindingResolver;
        this.parserCreator = parserCreator;
    }

	@SuppressWarnings("unchecked")
	@Override
	protected void parseFile(String fileFullPath) throws IOException {
        CharStream input = CharStreams.fromFileName(fileFullPath);
		Parser rubyParser = new Parser();
		StringReader in = new StringReader(input.toString());
		CompatVersion version = CompatVersion.RUBY2_3;
		ParserConfiguration config = new ParserConfiguration(0, version);
		try {
			Node node = rubyParser.parse(fileFullPath, in, config);
			JRubyVisitor parser = new JRubyVisitor(fileFullPath, entityRepo, includesFileLocator, bindingResolver,parserCreator);
			node.accept(parser);
		}catch(SyntaxException e) {
			System.out.println("parsing error in "+e.getPosition()  + "(" + e.getMessage() + ")");
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
