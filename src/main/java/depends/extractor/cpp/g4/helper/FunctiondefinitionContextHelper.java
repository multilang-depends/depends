package depends.extractor.cpp.g4.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import depends.entity.types.FunctionEntity;
import depends.entity.types.VarEntity;
import depends.javaextractor.CPP14Parser.FunctiondefinitionContext;

/**
functiondefinition
:
	attributespecifierseq? declspecifierseq? declarator virtspecifierseq?
	functionbody
;
 */
public class FunctiondefinitionContextHelper {
	FunctionDeclaratorVisitor v;
	private String returnType;
	public FunctiondefinitionContextHelper(FunctiondefinitionContext ctx) {
		if (ctx.attributespecifierseq()!=null) {
			//TODO: 
		}
		if (ctx.declspecifierseq()!=null) {
			//TODO: 
			//return value appears here
			DeclSpecifierHelper declSpecifierHelper = new DeclSpecifierHelper(ctx.declspecifierseq());
			
		}
		if (ctx.virtspecifierseq()!=null) {
			//TODO: 
		}
//		
//		SimpletypespecifierContextHelper helper = new SimpletypespecifierContextHelper(ctx.declspecifierseq().declspecifier().typespecifier()
//				.trailingtypespecifier().simpletypespecifier());
//		this.returnType = helper.getTypeName();
//		v = new FunctionDeclaratorVisitor();
//		v.visit(ctx);
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

	public void addParameters(FunctionEntity function) {
		// TODO Auto-generated method stub
	}

}
