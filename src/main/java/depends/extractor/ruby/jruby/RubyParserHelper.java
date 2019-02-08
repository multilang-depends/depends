package depends.extractor.ruby.jruby;

import java.util.ArrayList;
import java.util.List;

import org.jrubyparser.ast.AssignableNode;
import org.jrubyparser.ast.CallNode;
import org.jrubyparser.ast.ClassVarAsgnNode;
import org.jrubyparser.ast.ClassVarDeclNode;
import org.jrubyparser.ast.ConstDeclNode;
import org.jrubyparser.ast.DAsgnNode;
import org.jrubyparser.ast.FCallNode;
import org.jrubyparser.ast.GlobalAsgnNode;
import org.jrubyparser.ast.INameNode;
import org.jrubyparser.ast.InstAsgnNode;
import org.jrubyparser.ast.ListNode;
import org.jrubyparser.ast.LocalAsgnNode;
import org.jrubyparser.ast.MultipleAsgnNode;
import org.jrubyparser.ast.Node;
import org.jrubyparser.ast.UnaryCallNode;
import org.jrubyparser.ast.VCallNode;

public class RubyParserHelper {
	public String getName(Node node) {
		if (node instanceof INameNode)
			return ((INameNode)node).getName();
		return null;
	}

	public boolean isFunctionCall(Node ctx) {
		return ctx instanceof CallNode ||
				ctx instanceof FCallNode ||
				ctx instanceof UnaryCallNode ||
				ctx instanceof VCallNode;
	}
	
	public List<String> getName(AssignableNode ctx) {
		List<String> names = new ArrayList<>();
		if (ctx instanceof ClassVarAsgnNode) {
			names.add(((ClassVarAsgnNode)ctx).getName());
		}else if (ctx instanceof ClassVarDeclNode) {
			names.add(((ClassVarDeclNode)ctx).getName());
		}else if (ctx instanceof ConstDeclNode) {
			names.add(((ConstDeclNode)ctx).getName());
		}else if (ctx instanceof DAsgnNode) {
			names.add(((DAsgnNode)ctx).getName());
		}else if (ctx instanceof GlobalAsgnNode) {
			names.add(((GlobalAsgnNode)ctx).getName());
		}else if (ctx instanceof InstAsgnNode) {
			names.add(((InstAsgnNode)ctx).getName());
		}else if (ctx instanceof LocalAsgnNode) {
			names.add(((LocalAsgnNode)ctx).getName());
		}else if (ctx instanceof MultipleAsgnNode) {
			ListNode pre = ((MultipleAsgnNode)ctx).getPre();
			Node rest = ((MultipleAsgnNode)ctx).getRest();
			ListNode post = ((MultipleAsgnNode)ctx).getPost();
			if (pre!=null) {
				for ( Node preNode:pre.childNodes()) {
					
				}
			}
		}
		return names;
	}
}
