package depends.extractor.cpp;

import java.util.List;

import depends.javaextractor.CPP14BaseVisitor;
import depends.javaextractor.CPP14Parser.NoptrdeclaratorContext;
import depends.javaextractor.CPP14Parser.ParameterdeclarationclauseContext;

public class FunctionDeclaratorVisitor extends CPP14BaseVisitor<Void> {

	private String functionName;
	private List<String> parameterList;

	@Override
	public Void visitNoptrdeclarator(NoptrdeclaratorContext ctx) {
		if (ctx.parametersandqualifiers()!=null)
		{
			DeclaratorIdVisitor visitor = new DeclaratorIdVisitor();
			visitor.visit(ctx.noptrdeclarator());
			this.functionName = visitor.getId();
		}
		return super.visitNoptrdeclarator(ctx);
	}

	@Override
	public Void visitParameterdeclarationclause(ParameterdeclarationclauseContext ctx) {
		if (ctx.parameterdeclarationlist()!=null) {
		DeclaratorIdVisitor visitor = new DeclaratorIdVisitor();
		visitor.visit(ctx.parameterdeclarationlist());
		this.parameterList =visitor.getIdList();
		}
		return super.visitParameterdeclarationclause(ctx);
	}

	public String getFunctionName() {
		return functionName;
	}
	public List<String> getParameters() {
		return parameterList;
	}

}
