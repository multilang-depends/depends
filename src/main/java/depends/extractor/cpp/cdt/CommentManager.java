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

package depends.extractor.cpp.cdt;

import org.eclipse.cdt.core.dom.ast.IASTComment;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.internal.core.dom.parser.ASTTranslationUnit;

public class CommentManager {

	private IASTTranslationUnit translationUnit;
	IASTComment[] comments;
	boolean[] joinWithNext;
	public CommentManager(IASTTranslationUnit translationUnit) {
		this.translationUnit = translationUnit;
		comments = ((ASTTranslationUnit) translationUnit).getComments();
		joinWithNext = new boolean[comments.length];
		
		
		for (int i=1;i<comments.length;i++) {
			IASTComment previous = comments[i-1];
			int previousEnd = (previous.getFileLocation().getNodeOffset()+
					previous.getFileLocation().getNodeLength());
			IASTComment current = comments[i];
			if (current.getFileLocation().getNodeOffset()-previousEnd<5)
				joinWithNext[i-1] = true;
			else
				joinWithNext[i-1] = false;
		}
		joinWithNext[comments.length-1] = false;
	}

	public String getLeadingCommentText(int startOffset) {
		int adjacent = findCommentIndex(startOffset);
		if (adjacent==-1) return "";
		String comment = new String( comments[adjacent].getComment());
		int i=adjacent-1;
		while(i>=0) {
			if (joinWithNext[i]) {
				comment = new String(comments[i].getComment())+comment;
				i--;
			}else {
				break;
			}
		}
		return comment;
	}
	private int findCommentIndex(int startOffset) {
		
		IASTComment[] comments = ((ASTTranslationUnit) translationUnit).getComments();
		int i=0;
		for (;i<comments.length;i++) {
			IASTComment c = comments[i];
			int gap = startOffset-(c.getFileLocation().getNodeOffset()+
					c.getFileLocation().getNodeLength());
			if (gap>0 && gap<10) {
				break;
			}
			if (gap<0) return -1;
		}
		return i>=comments.length?-1:i;
	}
}
