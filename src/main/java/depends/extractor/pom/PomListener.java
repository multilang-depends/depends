package depends.extractor.pom;

import org.antlr.v4.runtime.ParserRuleContext;

import depends.entity.Entity;
import depends.entity.Expression;
import depends.entity.VarEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.xml.XMLParser.ElementContext;
import depends.extractor.xml.XMLParserBaseListener;

public class PomListener extends XMLParserBaseListener{
	private PomHandlerContext context;
	private EntityRepo entityRepo;
	PomArtifactEntity currentEntity;
	private VarEntity currentVar;
	Expression currentExpression;
	private PomParent pomParent;
	private static String elementNamePattern = "{$$GROUP_ID}.{$$ARTIFACT_ID}($$VERSION)";
	public PomListener(String fileFullPath, EntityRepo entityRepo) {
		this.context = new PomHandlerContext(entityRepo);
		this.entityRepo = entityRepo;
		context.startFile(fileFullPath);		
	}

	@Override
	public void enterElement(ElementContext ctx) {
		String name = ctx.Name(0).getText();
		if (name.equals("project")) {
			currentEntity = new PomArtifactEntity(elementNamePattern,context.currentFile(),entityRepo.generateId());
		}else if (name.equals("plugin")) {
			currentExpression = new Expression(entityRepo.generateId());
			currentExpression.rawType  = elementNamePattern;
		}else if (name.equals("dependency")) {
			currentVar = new VarEntity(elementNamePattern,elementNamePattern,currentEntity,entityRepo.generateId());
		}else if (name.equals("parent")) {
			pomParent = new PomParent(elementNamePattern);
		}
		
		//Add attribute
		else if (name.equals("groupId")) {
			appendGroupId(ctx.getParent(),ctx.content().getText());
		}else if (name.equals("artifactId")) {
			appendArtifactId(ctx.getParent(),ctx.content().getText());
		}else if (name.equals("version")) {
			appendVersion(ctx.getParent(),ctx.content().getText());
		} 
		super.enterElement(ctx);
	}
	
	private void appendVersion(ParserRuleContext parent, String version) {
		Object currentElement = getElement(parent);
		if (currentElement==null) return;
		if (currentElement instanceof Entity) {
			Entity e = ((Entity)currentElement);
			e.setRawName(e.getRawName().replace("$$VERSION", version));
		}else if (currentElement instanceof Expression) {
			Expression e = (Expression)currentElement;
			e.rawType = (e.rawType.replace("$$VERSION", version));
		}else if (currentElement instanceof PomParent) {
			PomParent p = (PomParent)currentElement;
			p.setContent(p.getContent().replace("$$VERSION", version));
		}
	}

	private Object getElement(ParserRuleContext parent) {
		if (!(parent instanceof ElementContext)) return null;
		ElementContext p = (ElementContext)parent;
		String name = p.Name(0).getText();
		if (name.equals("project")) {
			return currentEntity;
		}else if (name.equals("plugin")) {
			return currentExpression;
		}else if (name.equals("dependency")) {
			return currentVar;
		}else if (name.equals("parent")) {
			return pomParent;
		}
		return null;
	}

	private void appendArtifactId(ParserRuleContext parent, String artifactId) {
		Object currentElement = getElement(parent);
		if (currentElement==null) return;
		if (currentElement instanceof Entity) {
			Entity e = ((Entity)currentElement);
			e.setRawName(e.getRawName().replace("$$ARTIFACT_ID", artifactId));
		}else if (currentElement instanceof Expression) {
			Expression e = (Expression)currentElement;
			e.rawType = (e.rawType.replace("$$ARTIFACT_ID", artifactId));
		}else if (currentElement instanceof PomParent) {
			PomParent p = (PomParent)currentElement;
			p.setContent(p.getContent().replace("$$ARTIFACT_ID", artifactId));
		}		
	}

	private void appendGroupId(ParserRuleContext parent, String groupId) {
		Object currentElement = getElement(parent);
		if (currentElement==null) return;
		if (currentElement instanceof Entity) {
			Entity e = ((Entity)currentElement);
			e.setRawName(e.getRawName().replace("$$GROUP_ID", groupId));
		}else if (currentElement instanceof Expression) {
			Expression e = (Expression)currentElement;
			e.rawType = (e.rawType.replace("$$GROUP_ID", groupId));
		}else if (currentElement instanceof PomParent) {
			PomParent p = (PomParent)currentElement;
			p.setContent(p.getContent().replace("$$GROUP_ID", groupId));
		}	
	}

	@Override
	public void exitElement(ElementContext ctx) {
		String name = ctx.Name(0).getText();
		if (name.equals("project")) {
			currentEntity.setQualifiedName(currentEntity.getRawName());
			entityRepo.add(currentEntity);
		}else if (name.equals("plugin")) {
			currentEntity.addExpression(ctx, currentExpression);
		}else if (name.equals("dependency")) {
			currentVar.setRawType(currentVar.getRawName());
			currentEntity.addVar(currentVar);
		}else if (name.equals("parent")) {
			context.currentFile().addImport(pomParent);
		}
		super.exitElement(ctx);
	}
}
