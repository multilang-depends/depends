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

package depends.extractor.java.context;

import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import depends.extractor.java.JavaParser.CreatorContext;

public class CreatorContextHelper {

	public static String getCreatorType(CreatorContext creator) {
		if (creator==null) return null;
		if (creator.createdName()==null) return creator.getText();
		if (creator.createdName().IDENTIFIER()==null)
			return creator.createdName().getText();
		if (creator.createdName().IDENTIFIER().size()>0)
			return buildName(creator.createdName().IDENTIFIER());
		return null;
	}

	private static String buildName(List<TerminalNode> identifiers) {
		StringBuffer sb = new StringBuffer();
		for(TerminalNode id:identifiers) {
			if (sb.length()>0)
				sb.append(".");
			sb.append(id);
		}
		return sb.toString();
		
	}
}
