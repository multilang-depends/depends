package depends.extractor.cpp.g4cpp14.helper;

import java.util.ArrayList;
import java.util.List;

import depends.javaextractor.CPP14BaseVisitor;
import depends.javaextractor.CPP14Parser.UnqualifiedidContext;

public class DeclaratorIdVisitor extends CPP14BaseVisitor<Void> {
	private String id;
	
	private List<String> ids = new ArrayList<>();
	
	public String getId() {
		return this.id;
	}
	

	@Override
	public Void visitUnqualifiedid(UnqualifiedidContext ctx) {
		if (ctx.Identifier()!=null) {
			this.id = ctx.Identifier().getText();
			this.ids.add(this.id);
		}
		return super.visitUnqualifiedid(ctx);
	}


	public List<String> getIdList() {
		return ids;
	}
	
}
