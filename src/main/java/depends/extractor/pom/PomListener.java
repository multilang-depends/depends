package depends.extractor.pom;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import depends.entity.Entity;
import depends.entity.Expression;
import depends.entity.VarEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.xml.XMLParser.ElementContext;
import depends.extractor.FileParser;
import depends.extractor.xml.XMLParserBaseListener;
import depends.relations.Inferer;

public class PomListener extends XMLParserBaseListener{
	private PomHandlerContext context;
	private EntityRepo entityRepo;
	PomArtifactEntity currentEntity;
	private VarEntity currentVar;
	Expression currentExpression;
	private PomParent pomParent;
	private PomProcessor parseCreator;
	private List<String> includePaths;
	private Inferer inferer;
	private static String groupIdPattern = "$$GROUP_ID";
	private static String artifactIdPattern = "$$ARTIFACT_ID";
	private static String versionPattern = "$$VERSION";
	private static String elementNamePattern = groupIdPattern+"."+artifactIdPattern + "(" + versionPattern +")";
	public PomListener(String fileFullPath, EntityRepo entityRepo, List<String> includePaths, PomProcessor parseCreator,Inferer inferer) {
		this.context = new PomHandlerContext(entityRepo);
		this.entityRepo = entityRepo;
        this.parseCreator = parseCreator;
        this.includePaths = includePaths;
        this.inferer = inferer;
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
			appendData(ctx,ctx.content().getText(),groupIdPattern);
		}else if (name.equals("artifactId")) {
			appendData(ctx,ctx.content().getText(),artifactIdPattern);
		}else if (name.equals("version")) {
			appendData(ctx,ctx.content().getText(),versionPattern);
		} else if ("properties".equals(getParentElementName(ctx))) {
			if (ctx.content()!=null) {
				currentEntity.addProperty(name, ctx.content().getText());
			}
		}
		super.enterElement(ctx);
	}
	
	@Override
	public void exitElement(ElementContext ctx) {
		String name = ctx.Name(0).getText();
		if (name.equals("project")) {
			if (pomParent!=null) {
				currentEntity.setRawName(currentEntity.getRawName().replace(artifactIdPattern, pomParent.artifactId));
				currentEntity.setRawName(currentEntity.getRawName().replace("$$GROUP_ID", pomParent.groupId));
				currentEntity.setRawName(currentEntity.getRawName().replace("$$VERSION", pomParent.version));
			}
			currentEntity.setQualifiedName(currentEntity.getRawName());
			entityRepo.add(currentEntity);
		}else if (name.equals("plugin")) {
			currentEntity.addExpression(ctx, currentExpression);
		}else if (name.equals("dependency")) {
			currentVar.setRawType(currentVar.getRawName());
			currentEntity.addVar(currentVar);
		}else if (name.equals("parent")) {
			context.currentFile().addImport(pomParent);
			String parentFileName = new PomLocator(includePaths,pomParent).getLocation();
			if (parentFileName!=null) {
				FileParser importedParser = parseCreator.createFileParser(parentFileName);
				try {
					System.out.println("parsing "+parentFileName);
					importedParser.parse();
				} catch (Exception e) {
					System.err.println("parsing error in "+parentFileName);
				}			
			}
			context.currentFile().inferLocalLevelEntities(inferer);
		}
		super.exitElement(ctx);
	}
	


	private Object getElement(ParserRuleContext node) {
		String name = getParentElementName(node);
		if ("project".equals(name)) {
			return currentEntity;
		}else if ("plugin".equals(name)) {
			return currentExpression;
		}else if ("dependency".equals(name)) {
			return currentVar;
		}else if ("parent".equals(name)) {
			return pomParent;
		}
		return null;
	}

	private String getParentElementName(ParserRuleContext node) {
		node = node.getParent();
		if(node==null) return "project";
		node = node.getParent();
		if (!(node instanceof ElementContext)) return "project";
		
		ElementContext p = (ElementContext)node;
		String name = p.Name().get(0).getText();
		return name;
	}

	private void appendData(ParserRuleContext node, String name, String pattern) {
		Object currentElement = getElement(node);
		if (currentElement==null) return;
		if (currentElement instanceof Entity) {
			Entity e = ((Entity)currentElement);
			e.setRawName(e.getRawName().replace(pattern, name));
		}else if (currentElement instanceof Expression) {
			Expression e = (Expression)currentElement;
			e.rawType = (e.rawType.replace(pattern, name));
		}else if (currentElement instanceof PomParent) {
			PomParent p = (PomParent)currentElement;
			p.setContent(p.getContent().replace(pattern, name));
			if (pattern.equals(artifactIdPattern))
				p.artifactId = name;
			else if (pattern.equals(groupIdPattern))
				p.groupId = name;
			else if (pattern.equals(versionPattern))
				p.version = name;
		}		
	}
}
