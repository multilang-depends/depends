package depends.extractor.ruby.jruby;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;

import org.jrubyparser.ast.AliasNode;
import org.jrubyparser.ast.ArgumentNode;
import org.jrubyparser.ast.ArrayNode;
import org.jrubyparser.ast.CallNode;
import org.jrubyparser.ast.ClassNode;
import org.jrubyparser.ast.ClassVarAsgnNode;
import org.jrubyparser.ast.ClassVarDeclNode;
import org.jrubyparser.ast.ClassVarNode;
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

import depends.entity.ContainerEntity;
import depends.entity.Entity;
import depends.entity.repo.EntityRepo;
import depends.extractor.ParserCreator;
import depends.extractor.ruby.IncludedFileLocator;
import depends.extractor.ruby.RubyHandlerContext;
import depends.relations.Inferer;

public class JRubyVisitor extends NoopVisitor {

	private RubyHandlerContext context;
	RubyParserHelper helper = new RubyParserHelper();
	private ExpressionUsage expressionUsage;

	public JRubyVisitor(String fileFullPath, EntityRepo entityRepo, IncludedFileLocator includedFileLocator,
			ExecutorService executorService, Inferer inferer, ParserCreator parserCreator) {
		this.context = new RubyHandlerContext(entityRepo, includedFileLocator, executorService, inferer,parserCreator);
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
		boolean handled=false;
	    	Node varNode = node.getReceiver();
		if (varNode instanceof INameNode) {
		    String varName = ((INameNode)varNode).getName();
		    Entity var = context.foundEntityWithName(varName);
		    if (var!=null && var instanceof ContainerEntity) {
			context.foundMethodDeclarator(((ContainerEntity)var),node.getName());
			handled = true;
		    }
		}
		if (!handled) {
		    	//fallback to add it to last container
			context.foundMethodDeclarator(node.getName(), null, new ArrayList<>());
		}
		super.visitDefsNode(node);
		context.exitLastedEntity();
		return null;
	}


	@Override
	public Object visitGlobalVarNode(GlobalVarNode node) {
		foundVar(context.globalScope(),node.getName());
		return super.visitGlobalVarNode(node);
	}


	@Override
	public Object visitInstVarNode(InstVarNode node) {
		foundVar(context.currentType(),node.getName());
		return super.visitInstVarNode(node);
	}

	@Override
	public Object visitClassVarAsgnNode(ClassVarAsgnNode node) {
		foundVar(context.currentType(),node.getName());
		return super.visitClassVarAsgnNode(node);
	}


	@Override
	public Object visitClassVarDeclNode(ClassVarDeclNode node) {
		foundVar(context.currentType(),node.getName());
		return super.visitClassVarDeclNode(node);
	}


	@Override
	public Object visitClassVarNode(ClassVarNode node) {
		foundVar(context.currentType(),node.getName());
		return super.visitClassVarNode(node);
	}
	
	@Override
	public Object visitLocalVarNode(LocalVarNode node) {
		return super.visitLocalVarNode(node);
	}


	@Override
	public Object visitDVarNode(DVarNode node) {
		foundVar(context.lastContainer(),node.getName());
		return super.visitDVarNode(node);
	}


	@Override
	public Object visitDAsgnNode(DAsgnNode node) {
		foundVar(context.lastContainer(),node.getName());
		return super.visitDAsgnNode(node);
	}


	@Override
	public Object visitGlobalAsgnNode(GlobalAsgnNode node) {
		foundVar(context.globalScope(),node.getName());
		return super.visitGlobalAsgnNode(node);
	}


	@Override
	public Object visitInstAsgnNode(InstAsgnNode node) {
		foundVar(context.currentType(),node.getName());
		return super.visitInstAsgnNode(node);
	}


	@Override
	public Object visitArgumentNode(ArgumentNode node) {
		String paramName = node.getName();
		context.addMethodParameter(paramName);
		return super.visitArgumentNode(node);
	}


	@Override
	public Object visitLocalAsgnNode(LocalAsgnNode node) {
		foundVar(context.lastContainer(),node.getName());
		return super.visitLocalAsgnNode(node);
	}

	@Override
	protected Object visit(Node node) {
		expressionUsage.foundExpression(node);
		return super.visit(node);
	}
	
	private void foundVar(ContainerEntity container,String varName ) {
		if (!context.isNameExist(varName)) {
			context.foundVarDefinition(container,varName);
		}
	}

}
