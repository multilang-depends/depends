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
