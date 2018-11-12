package depends.extractor.cpp.g4cpp14.helper;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import depends.javaextractor.CElsaLexer;

public class CommentsUtil {
	public static List<String> getLeadingComments(CommonTokenStream tokens, ParserRuleContext ctx) {
		Token startOfFunc = ctx.getStart();
		List<Token> cmtToken = tokens.getHiddenTokensToLeft(startOfFunc.getTokenIndex(),CElsaLexer.CHANNEL_COMMENT);
		if (cmtToken!=null) {
			ArrayList<String> comments = new ArrayList<>();
			for (Token cmt:cmtToken) {
				comments.add(cmt.getText());
			}
			return comments;
		}
		return new ArrayList<>();
	}
}
