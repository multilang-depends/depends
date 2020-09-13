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

import depends.entity.*;
import depends.entity.repo.EntityRepo;
import depends.extractor.ParserCreator;
import depends.extractor.ruby.IncludedFileLocator;
import depends.extractor.ruby.RubyHandlerContext;
import depends.relations.Inferer;
import org.jrubyparser.ast.*;
import org.jrubyparser.util.NoopVisitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;

public class JRubyVisitor extends NoopVisitor {

	private RubyHandlerContext context;
	RubyParserHelper helper = RubyParserHelper.getInst();
	private ExpressionUsage expressionUsage;

	public JRubyVisitor(String fileFullPath, EntityRepo entityRepo, IncludedFileLocator includedFileLocator,
			ExecutorService executorService, Inferer inferer, ParserCreator parserCreator) {
		this.context = new RubyHandlerContext(entityRepo, includedFileLocator, executorService, inferer, parserCreator);
		expressionUsage = new ExpressionUsage(context, entityRepo, helper, inferer);
		context.startFile(fileFullPath);

	}

	@Override
	public Object visitAliasNode(AliasNode node) {
		context.foundNewAlias(node.getNewNameString(), node.getOldNameString());
		return super.visitAliasNode(node);
	}

	@Override
	public Object visitModuleNode(ModuleNode node) {
		String name = helper.getName(node.getCPath());
		context.foundNamespace(name,node.getPosition().getStartLine());
		super.visitModuleNode(node);
		context.exitLastedEntity();
		return null;
	}

	@Override
	public Object visitClassNode(ClassNode node) {
		TypeEntity type = context.foundNewType(helper.getName(node.getCPath()),node.getPosition().getStartLine());
		Node superNode = node.getSuper();
		
		if (superNode instanceof ConstNode ||
				superNode instanceof SymbolNode ||
				superNode instanceof Colon2ConstNode ||
				superNode instanceof Colon3Node) {
			String superName = helper.getName(superNode);
			context.foundExtends(superName);
		}else{
			if (superNode != null) {
				System.err.println("cannot support the super node style" + superNode.toString());
			}
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
		String fname = helper.getName(node);
		Collection<String> params = getParams(node);
		context.processSpecialFuncCall(fname, params, node.getPosition().getStartLine());
		return super.visitFCallNode(node);
	}

	private Collection<String> getParams(IArgumentNode node) {
		Node args = node.getArgs();
		Collection<String> params = new ArrayList<>();
		if (args instanceof ArrayNode) {
			ArrayNode argArray = (ArrayNode) args;
			for (Node arg : argArray.childNodes()) {
				if (arg instanceof StrNode) {
					params.add(((StrNode) arg).getValue());
				} else if (arg instanceof ConstNode) {
					params.add(((ConstNode) arg).getName());
				}
			}
		}
		return params;
	}

	@Override
	public Object visitCallNode(CallNode node) {
		String fname = helper.getName(node);
		Collection<String> params = getParams(node);
		addCallToReceiverVar(node, fname);
		context.processSpecialFuncCall(fname, params, node.getPosition().getStartLine());
		return super.visitCallNode(node);
	}

	private void addCallToReceiverVar(CallNode node, String fname) {
		if (helper.isCommonOperator(fname))return;
		Node varNode = node.getReceiver();
		
		GenericName varName = GenericName.build(helper.getName(varNode));
		if (varName==null) return;
		Entity var = context.foundEntityWithName(varName);
		if (var != null && var instanceof VarEntity) {
			VarEntity varEntity = (VarEntity) var;
			varEntity.addFunctionCall(GenericName.build(fname));
		}
	}

	@Override
	public Object visitUnaryCallNode(UnaryCallNode node) {
		String fname = helper.getName(node);
		Collection<String> params = new ArrayList<>();
		context.processSpecialFuncCall(fname, params, node.getPosition().getStartLine());
		return super.visitUnaryCallNode(node);
	}

	/**
	 * VCallNode is just a function call without parameter
	 */
	@Override
	public Object visitVCallNode(VCallNode node) {
		return super.visitVCallNode(node);
	}

	@Override
	public Object visitDefnNode(DefnNode node) {
		FunctionEntity method = context.foundMethodDeclarator(node.getName(),node.getPosition().getStartLine());
		method.setLine(node.getPosition().getStartLine());

		super.visitDefnNode(node);
		context.exitLastedEntity();
		return null;
	}

	@Override
	public Object visitDefsNode(DefsNode node) {
		boolean handled = false;
		Node varNode = node.getReceiver();
		if (varNode instanceof SelfNode) {
			//will be handled by context.foundMethodDeclarator(node.getName(), null, new ArrayList<>());
		} else if (varNode instanceof ConstNode) {
			String className = ((INameNode) varNode).getName();
			Entity entity = context.foundEntityWithName(GenericName.build(className));
			if (entity != null && entity instanceof ContainerEntity) {
				FunctionEntity method = context.foundMethodDeclarator(((ContainerEntity) entity), node.getName(),node.getPosition().getStartLine());
				method.setLine(node.getPosition().getStartLine());
				handled = true;
			}

		} else if (varNode instanceof INameNode) {
			String varName = ((INameNode) varNode).getName();
			Entity var = context.foundEntityWithName(GenericName.build(varName));
			if (var != null && var instanceof ContainerEntity) {
				FunctionEntity method = context.foundMethodDeclarator(((ContainerEntity) var), node.getName(),node.getPosition().getStartLine());
				method.setLine(node.getPosition().getStartLine());
				handled = true;
			}
		} 

		if (!handled) {
			// fallback to add it to last container
			FunctionEntity method = context.foundMethodDeclarator(node.getName(),node.getPosition().getStartLine());
			method.setLine(node.getPosition().getStartLine());
		}
		super.visitDefsNode(node);
		context.exitLastedEntity();
		return null;
	}

	@Override
	public Object visitGlobalVarNode(GlobalVarNode node) {
		context.foundVarDefinition(context.globalScope(), node.getName(),node.getPosition().getStartLine());
		return super.visitGlobalVarNode(node);
	}

	@Override
	public Object visitInstVarNode(InstVarNode node) {
		context.foundVarDefinition(context.currentType(), node.getName(),node.getPosition().getStartLine());
		return super.visitInstVarNode(node);
	}

	@Override
	public Object visitClassVarAsgnNode(ClassVarAsgnNode node) {
		context.foundVarDefinition(helper.getScopeOfVar(node,context), node.getName(),node.getPosition().getStartLine());
		return super.visitClassVarAsgnNode(node);
	}

	@Override
	public Object visitClassVarDeclNode(ClassVarDeclNode node) {
		context.foundVarDefinition(context.currentType(), node.getName(),node.getPosition().getStartLine());
		return super.visitClassVarDeclNode(node);
	}

	@Override
	public Object visitClassVarNode(ClassVarNode node) {
		context.foundVarDefinition(context.currentType(), node.getName(),node.getPosition().getStartLine());
		return super.visitClassVarNode(node);
	}

	@Override
	public Object visitLocalVarNode(LocalVarNode node) {
		return super.visitLocalVarNode(node);
	}

	@Override
	public Object visitDVarNode(DVarNode node) {
		context.foundVarDefinition(context.lastContainer(), node.getName(),node.getPosition().getStartLine());
		return super.visitDVarNode(node);
	}

	@Override
	public Object visitDAsgnNode(DAsgnNode node) {
		context.foundVarDefinition(helper.getScopeOfVar(node,context), node.getName(),node.getPosition().getStartLine());
		return super.visitDAsgnNode(node);
	}

	@Override
	public Object visitGlobalAsgnNode(GlobalAsgnNode node) {
		context.foundVarDefinition(helper.getScopeOfVar(node,context), node.getName(),node.getPosition().getStartLine());
		return super.visitGlobalAsgnNode(node);
	}

	@Override
	public Object visitInstAsgnNode(InstAsgnNode node) {
		context.foundVarDefinition(helper.getScopeOfVar(node,context), node.getName(),node.getPosition().getStartLine());
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
		context.foundVarDefinition(helper.getScopeOfVar(node,context), node.getName(),node.getPosition().getStartLine());
		return super.visitLocalAsgnNode(node);
	}

	@Override
	protected Object visit(Node node) {
		expressionUsage.foundExpression(node);
		return super.visit(node);
	}

	public void done() {
		context.done();
	}
}
