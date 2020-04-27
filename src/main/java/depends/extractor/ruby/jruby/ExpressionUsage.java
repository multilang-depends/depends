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

import java.util.List;

import org.jrubyparser.ast.AndNode;
import org.jrubyparser.ast.AssignableNode;
import org.jrubyparser.ast.BlockNode;
import org.jrubyparser.ast.ClassVarNode;
import org.jrubyparser.ast.Colon3Node;
import org.jrubyparser.ast.ConstNode;
import org.jrubyparser.ast.FCallNode;
import org.jrubyparser.ast.FalseNode;
import org.jrubyparser.ast.GlobalVarNode;
import org.jrubyparser.ast.ILiteralNode;
import org.jrubyparser.ast.InstVarNode;
import org.jrubyparser.ast.ListNode;
import org.jrubyparser.ast.LocalVarNode;
import org.jrubyparser.ast.NewlineNode;
import org.jrubyparser.ast.Node;
import org.jrubyparser.ast.OrNode;
import org.jrubyparser.ast.ReturnNode;
import org.jrubyparser.ast.RootNode;
import org.jrubyparser.ast.TrueNode;
import org.jrubyparser.ast.VCallNode;

import depends.entity.ContainerEntity;
import depends.entity.Expression;
import depends.entity.FunctionEntity;
import depends.entity.GenericName;
import depends.entity.VarEntity;
import depends.entity.repo.IdGenerator;
import depends.extractor.ruby.RubyHandlerContext;
import depends.relations.Inferer;

public class ExpressionUsage {
	RubyHandlerContext context;
	IdGenerator idGenerator;
	Inferer inferer;
	private RubyParserHelper helper;

	public ExpressionUsage(RubyHandlerContext context, IdGenerator idGenerator, RubyParserHelper helper,
			Inferer inferer) {
		this.context = context;
		this.idGenerator = idGenerator;
		this.inferer = inferer;
		this.helper = helper;
	}

	public Expression foundExpression(Node ctx) {
		if (ctx instanceof RootNode)
			return null;
		if (ctx instanceof BlockNode)
			return null;
		Expression expression = findExpression(ctx);
		if (expression != null)
			return expression;
		Expression parent = findParentInStack(ctx);
		/* create expression and link it with parent */
		expression = new Expression(idGenerator.generateId());
		expression.setText(ctx.toString());
		expression.setParent(parent);
		if (ctx instanceof NewlineNode) {
			expression.setStatement(true);
		}

		context.lastContainer().addExpression(ctx, expression);
		if (ctx instanceof ILiteralNode && !(ctx instanceof ListNode)) {
			expression.setIdentifier("<literal>");
			expression.setRawType(Inferer.buildInType.getQualifiedName());
		} else if (ctx instanceof TrueNode || ctx instanceof FalseNode) {
			expression.setIdentifier("<boolean>");
			expression.setRawType(Inferer.buildInType.getQualifiedName());
		} else if (ctx instanceof AndNode || ctx instanceof OrNode) {
			expression.setIdentifier("<logical>");
			expression.setRawType(Inferer.buildInType.getQualifiedName());
		} else if (ctx instanceof ConstNode) {
			expression.setRawType(helper.getName(ctx));
			expression.setIdentifier(helper.getName(ctx));
		} else if (ctx instanceof LocalVarNode || ctx instanceof GlobalVarNode || ctx instanceof ClassVarNode
				|| ctx instanceof InstVarNode || ctx instanceof Colon3Node) {
			expression.setIdentifier(helper.getName(ctx));
		}
		if (ctx instanceof AssignableNode) {
			expression.setSet(true);
		} else if (helper.isFunctionCall(ctx)) {
			String name = helper.getName(ctx);
			expression.setCall(true);
			if (name.equals("new")) {
				expression.setCreate(true);
				List<Node> childNodes = ctx.childNodes();
				if (childNodes.size() > 0) {
					expression.setIdentifier(helper.getName(ctx.childNodes().get(0)));
				} else {
					expression.setIdentifier(context.currentType().getRawName());
				}
				expression.setRawType(expression.getIdentifier());
				expression.disableDriveTypeFromChild();
			} else if (name.equals("raise")) {
				expression.setThrow (true);
			} else if (helper.isArithMeticOperator(name)) {
				expression.setIdentifier("<operator>");
				expression.setRawType(Inferer.buildInType.getQualifiedName());
			} else {
				expression.setIdentifier(name);
				expression.setRawType(helper.getReciever(ctx));
				if (expression.getRawType() != null) {
					expression.setDot(true);
				}

				if (ctx instanceof VCallNode || ctx instanceof FCallNode) {
					expression.disableDriveTypeFromChild();
				}
			}
		}
		deduceVarTypeInCaseOfAssignment(ctx, expression);
		deduceReturnTypeInCaseOfReturn(ctx, expression);
		return expression;
	}

	/**
	 * Auto deduce variable type from assignment. for example: c = C.new then c is
	 * type of C
	 * 
	 * @param node
	 * @param expression
	 */
	private void deduceVarTypeInCaseOfAssignment(Node node, Expression expression) {
		Node parentNode = node.getParent();
		if (parentNode instanceof AssignableNode) {
			ContainerEntity scope = helper.getScopeOfVar((AssignableNode) parentNode, this.context);
			VarEntity var = scope.lookupVarLocally(helper.getName(parentNode));
			if (var != null) {
				expression.addDeducedTypeVar(var);
			}
		}
	}

	private void deduceReturnTypeInCaseOfReturn(Node ctx, Expression expression) {
		FunctionEntity currentFunction = context.currentFunction();
		if (currentFunction == null)
			return;
		if (ctx instanceof ReturnNode) {
			expression.addDeducedTypeFunction(currentFunction);
		}
	}

	private Expression findParentInStack(Node ctx) {
		if (ctx == null)
			return null;
		if (ctx.getParent() == null)
			return null;
		if (context.lastContainer() == null) {
			return null;
		}
		if (context.lastContainer().expressions().containsKey(ctx.getParent()))
			return context.lastContainer().expressions().get(ctx.getParent());
		return findParentInStack(ctx.getParent());
	}

	private Expression findExpression(Node ctx) {
		if (ctx == null)
			return null;
		if (ctx.getParent() == null)
			return null;
		if (context.lastContainer() == null) {
			return null;
		}
		return context.lastContainer().expressions().get(ctx);
	}

}