package depends.extractor.java.context;

import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

public class IdentifierContextHelper {
	public static String getName(List<TerminalNode> identifiers) {
		String r = "";
		for (TerminalNode id:identifiers) {
			String dot = r.isEmpty()?"":".";
			r = r + dot + id.getText();
		}
		return r;
	}
}
