package depends.extractor.pom;

import depends.entity.Expression;
import depends.entity.VarEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.xml.XMLParser.ElementContext;
import depends.extractor.xml.XMLParserBaseListener;

public class PomListener extends XMLParserBaseListener{
	
	private PomHandlerContext context;
	private EntityRepo entityRepo;
	PomArtifactEntity currentEntity;
	Expression currentExpression;
	private VarEntity currentVar;
	private String elementName;

	public PomListener(String fileFullPath, EntityRepo entityRepo) {
		this.context = new PomHandlerContext(entityRepo);
		this.entityRepo = entityRepo;
		context.startFile(fileFullPath);		
	}

	@Override
	public void enterElement(ElementContext ctx) {
		String name = ctx.Name(0).getText();
		if (name.equals("project")) {
			currentEntity = new PomArtifactEntity("",context.currentFile(),entityRepo.generateId());
			elementName = "";
		}else if (name.equals("plugin")) {
			currentExpression = new Expression(entityRepo.generateId());
			elementName = "";
		}else if (name.equals("dependency")) {
			currentVar = new VarEntity("","",currentEntity,entityRepo.generateId());
			elementName = "";
		}
		//Add attribute
		else if (name.equals("groupId")) {
			elementName = (ctx.content().getText());
		}else if (name.equals("artifactId")) {
			elementName += "."+ctx.content().getText();
		}else if (name.equals("version")) {
			elementName += "("+ctx.content().getText()+")";
		} 
		super.enterElement(ctx);
	}
	
	@Override
	public void exitElement(ElementContext ctx) {
		String name = ctx.Name(0).getText();
		if (name.equals("project")) {
			currentEntity.setRawName(elementName);
			currentEntity.setQualifiedName(elementName);
			entityRepo.add(currentEntity);
		}else if (name.equals("plugin")) {
			currentExpression.rawType = elementName;
			currentEntity.addExpression(ctx, currentExpression);
		}else if (name.equals("dependency")) {
			currentVar.setRawName(elementName);
			currentVar.setRawType(elementName);
		}
		super.exitElement(ctx);
	}
}
