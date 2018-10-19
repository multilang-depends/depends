package depends.extractor.cpp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import depends.entity.types.VarEntity;
import depends.javaextractor.CPP14Parser.FunctiondefinitionContext;

public class FunctiondefinitionContextHelper {
	FunctionDeclaratorVisitor v;
	private String returnType;
	public FunctiondefinitionContextHelper(FunctiondefinitionContext ctx) {
		SimpletypespecifierContextHelper helper = new SimpletypespecifierContextHelper(ctx.declspecifierseq().declspecifier().typespecifier()
				.trailingtypespecifier().simpletypespecifier());
		this.returnType = helper.getTypeName();
		v = new FunctionDeclaratorVisitor();
		v.visit(ctx);
	}

	public String getFunctionName() {
		return v.getFunctionName();
	}

	public String getReturnType() {
		return this.returnType;
	}

	public Collection<VarEntity> getParameters() {
		return new ArrayList<>();
	}

	public List<String> getThrowedType() {
		return new ArrayList<>();
	}

}
