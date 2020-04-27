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

import java.io.IOException;
import java.io.StringReader;
import java.util.concurrent.ExecutorService;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.jrubyparser.CompatVersion;
import org.jrubyparser.NodeVisitor;
import org.jrubyparser.Parser;
import org.jrubyparser.ast.Node;
import org.jrubyparser.parser.ParserConfiguration;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.FileParser;
import depends.extractor.ParserCreator;
import depends.extractor.ruby.IncludedFileLocator;
import depends.relations.Inferer;
import depends.util.FileUtil;
public class JRubyFileParser implements FileParser {
	private String fileFullPath;
	private EntityRepo entityRepo;
	private ExecutorService executor;
	private IncludedFileLocator includesFileLocator;
	private Inferer inferer;
	private ParserCreator parserCreator;

	public JRubyFileParser(String fileFullPath, EntityRepo entityRepo, 
			ExecutorService executorService, 
			IncludedFileLocator includesFileLocator, 
			Inferer inferer, ParserCreator parserCreator) {
        this.fileFullPath  = FileUtil.uniqFilePath(fileFullPath);
        this.entityRepo = entityRepo;
        this.executor = executorService;
        this.includesFileLocator = includesFileLocator;
        this.inferer = inferer;
        this.parserCreator = parserCreator;
    }

	@SuppressWarnings("unchecked")
	@Override
	public void parse() throws IOException {
		/** If file already exist, skip it */
		Entity fileEntity = entityRepo.getEntity(fileFullPath);
		if (fileEntity!=null && fileEntity instanceof FileEntity) {
			return;
		}
		
        CharStream input = CharStreams.fromFileName(fileFullPath);
		Parser rubyParser = new Parser();
		StringReader in = new StringReader(input.toString());
		CompatVersion version = CompatVersion.RUBY2_3;
		ParserConfiguration config = new ParserConfiguration(0, version);
		try {
			Node node = rubyParser.parse("<code>", in, config);
			JRubyVisitor parser = new JRubyVisitor(fileFullPath, entityRepo, includesFileLocator,executor,inferer,parserCreator);
			node.accept(parser);
			fileEntity = entityRepo.getEntity(fileFullPath);
			((FileEntity)fileEntity).cacheAllExpressions();
			parser.done();
		}catch(Exception e) {
			System.err.println("parsing error in "+fileFullPath);
		}
	}

}
