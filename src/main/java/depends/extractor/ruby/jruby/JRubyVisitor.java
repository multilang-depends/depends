package depends.extractor.ruby.jruby;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;

import org.jrubyparser.ast.AliasNode;
import org.jrubyparser.ast.ArrayNode;
import org.jrubyparser.ast.CallNode;
import org.jrubyparser.ast.ClassNode;
import org.jrubyparser.ast.Colon2ConstNode;
import org.jrubyparser.ast.ConstNode;
import org.jrubyparser.ast.DAsgnNode;
import org.jrubyparser.ast.DVarNode;
import org.jrubyparser.ast.DefnNode;
import org.jrubyparser.ast.DefsNode;
import org.jrubyparser.ast.FCallNode;
import org.jrubyparser.ast.GlobalAsgnNode;
import org.jrubyparser.ast.GlobalVarNode;
import org.jrubyparser.ast.IArgumentNode;
import org.jrubyparser.ast.INameNode;
import org.jrubyparser.ast.InstAsgnNode;
import org.jrubyparser.ast.InstVarNode;
import org.jrubyparser.ast.LocalAsgnNode;
import org.jrubyparser.ast.LocalVarNode;
import org.jrubyparser.ast.ModuleNode;
import org.jrubyparser.ast.Node;
import org.jrubyparser.ast.RootNode;
import org.jrubyparser.ast.StrNode;
import org.jrubyparser.ast.SymbolNode;
import org.jrubyparser.ast.UnaryCallNode;
import org.jrubyparser.ast.VCallNode;
import org.jrubyparser.util.NoopVisitor;

import depends.entity.repo.EntityRepo;
import depends.extractor.ParserCreator;
import depends.extractor.ruby.IncludedFileLocator;
import depends.extractor.ruby.RubyHandlerContext;
import depends.relations.Inferer;

public class JRubyVisitor extends NoopVisitor {

	private RubyHandlerContext context;
	private EntityRepo entityRepo;
	RubyParserHelper helper = new RubyParserHelper();
	private ExpressionUsage expressionUsage;

	public JRubyVisitor(String fileFullPath, EntityRepo entityRepo, IncludedFileLocator includedFileLocator,
			ExecutorService executorService, Inferer inferer, ParserCreator parserCreator) {
		this.context = new RubyHandlerContext(entityRepo, includedFileLocator, executorService, inferer,parserCreator);
		this.entityRepo = entityRepo;
		expressionUsage = new ExpressionUsage(context, entityRepo, helper,inferer);
		context.startFile(fileFullPath);
	}


	@Override
	public Object visitAliasNode(AliasNode node) {
		context.foundNewTypeAlias(node.getNewNameString(),
				node.getOldNameString());
		return super.visitAliasNode(node);
	}
	
	@Override
	public Object visitModuleNode(ModuleNode node) {
		String name = helper.getName(node.getCPath());
		context.foundNamespace(name);
		super.visitModuleNode(node);
		context.exitLastedEntity();
		return null;
	}

	@Override
	public Object visitClassNode(ClassNode node) {
		context.foundNewType(node.getCPath().getName());
		Node superNode = node.getSuper();
		if (superNode instanceof ConstNode) {
			String superName = ((ConstNode)superNode).getName();
			context.foundExtends(superName);
		}else if (superNode instanceof SymbolNode) {
			String superName = ((SymbolNode)superNode).getName();
			context.foundExtends(superName);
		}else if (superNode instanceof Colon2ConstNode) {
			Colon2ConstNode colon2ConstNode = (Colon2ConstNode)superNode;
			String name1 = helper.getName(colon2ConstNode.getLeftNode());
			String superName = colon2ConstNode.getName();
			context.foundExtends(name1 + "."+superName);
		}
		
		super.visitClassNode(node);
		
		context.exitLastedEntity();
		return null;
	}

	
	@Override
	public Object visitRootNode(RootNode node) {
		System.out.println(node);
		return super.visitRootNode(node);
	}

	@Override
	public Object visitFCallNode(FCallNode node) {
		String fname  = helper.getName(node);
		Collection<String> params = getParams(node);
		context.processSpecialFuncCall(fname, params);
		return super.visitFCallNode(node);
	}


	private Collection<String> getParams(IArgumentNode node) {
		Node args = node.getArgs();
		Collection<String> params = new ArrayList<>();
		if (args instanceof ArrayNode) {
			ArrayNode argArray = (ArrayNode)args;
			for (Node arg:argArray.childNodes()) {
				if (arg instanceof StrNode) {
					params.add(((StrNode)arg).getValue());
				}else if (arg instanceof ConstNode) {
					params.add(((ConstNode)arg).getName());
				}
			}
		}
		return params;
	}
	
	@Override
	public Object visitCallNode(CallNode node) {
		String fname  = helper.getName(node);
		Collection<String> params = getParams(node);
		context.processSpecialFuncCall(fname, params);
		return super.visitCallNode(node);
	}


	@Override
	public Object visitUnaryCallNode(UnaryCallNode node) {
		String fname  = helper.getName(node);
		Collection<String> params = new ArrayList<>();
		context.processSpecialFuncCall(fname, params);
		return super.visitUnaryCallNode(node);
	}


	@Override
	public Object visitVCallNode(VCallNode node) {
		String fname  = helper.getName(node);
		Collection<String> params = new ArrayList<>();
		context.processSpecialFuncCall(fname, params);
		return super.visitVCallNode(node);
	}
	

	@Override
	public Object visitDefnNode(DefnNode node) {
		context.foundMethodDeclarator(node.getName(), null, new ArrayList<>());

		super.visitDefnNode(node);
		context.exitLastedEntity();
		return null;
	}

	@Override
	public Object visitDefsNode(DefsNode node) {
		//TODO: should add the method to the concrete variable
		//
		context.foundMethodDeclarator(node.getName(), null, new ArrayList<>());
		super.visitDefsNode(node);
		context.exitLastedEntity();
		return null;
	}


	@Override
	public Object visitGlobalVarNode(GlobalVarNode node) {
		// TODO Auto-generated method stub
		return super.visitGlobalVarNode(node);
	}


	@Override
	public Object visitInstVarNode(InstVarNode node) {
		// TODO Auto-generated method stub
		return super.visitInstVarNode(node);
	}


	@Override
	public Object visitLocalVarNode(LocalVarNode node) {
		//System.out.println("visitLocalVarNode"+node.getName());
		return super.visitLocalVarNode(node);
	}


	@Override
	public Object visitDVarNode(DVarNode node) {
		// TODO Auto-generated method stub
		return super.visitDVarNode(node);
	}


	@Override
	public Object visitDAsgnNode(DAsgnNode node) {
		// TODO Auto-generated method stub
		return super.visitDAsgnNode(node);
	}


	@Override
	public Object visitGlobalAsgnNode(GlobalAsgnNode node) {
		// TODO Auto-generated method stub
		return super.visitGlobalAsgnNode(node);
	}


	@Override
	public Object visitInstAsgnNode(InstAsgnNode node) {
		// TODO Auto-generated method stub
		return super.visitInstAsgnNode(node);
	}


	@Override
	public Object visitLocalAsgnNode(LocalAsgnNode node) {
		//System.out.println("visitLocalAsgnNode"+node.getName());
		Node right = node.getValue();
		return super.visitLocalAsgnNode(node);
	}

	@Override
	protected Object visit(Node node) {
		expressionUsage.foundExpression(node);
		return super.visit(node);
	}

}
