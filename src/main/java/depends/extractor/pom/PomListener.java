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

package depends.extractor.pom;

import java.util.List;
import java.util.Stack;

import org.antlr.v4.runtime.ParserRuleContext;

import depends.entity.Expression;
import depends.entity.GenericName;
import depends.entity.VarEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.FileParser;
import depends.extractor.xml.XMLParser.ElementContext;
import depends.extractor.xml.XMLParserBaseListener;
import depends.relations.Inferer;

public class PomListener extends XMLParserBaseListener {
	private PomHandlerContext context;
	private EntityRepo entityRepo;
	PomArtifactEntity currentEntity;
	private VarEntity currentVar;
	Expression currentExpression;
	private PomParent pomParent;
	private PomProcessor parseCreator;
	private List<String> includePaths;
	private Inferer inferer;
	private Stack<PomCoords> pomCoords= new Stack<>();

	public PomListener(String fileFullPath, EntityRepo entityRepo, List<String> includePaths, PomProcessor parseCreator,
			Inferer inferer) {
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
			pomCoords.push(new PomCoords());
			currentEntity = new PomArtifactEntity("", context.currentFile(), entityRepo.generateId());
		} else if (name.equals("plugin")) {
			pomCoords.push(new PomCoords());
			currentExpression = new Expression(entityRepo.generateId());
			currentExpression.setRawType("");
		} else if (name.equals("dependency")) {
			pomCoords.push(new PomCoords());
			currentVar = new VarEntity(GenericName.build(""), GenericName.build(""),
					currentEntity, entityRepo.generateId());
		} else if (name.equals("parent")) {
			pomCoords.push(new PomCoords());
			pomParent = new PomParent("");
		}

		// Add attribute
		else if (name.equals("groupId")) {
			peekPomCoords().groupId = getContentValueOf(ctx);
		} else if (name.equals("artifactId")) {
			peekPomCoords().artifactId = getContentValueOf(ctx);
		} else if (name.equals("version")) {
			peekPomCoords().version = getContentValueOf(ctx);
		} else if ("properties".equals(getParentElementName(ctx))) {
			if (ctx.content() != null) {
				currentEntity.addProperty(name, getContentValueOf(ctx));
			}
		}
		super.enterElement(ctx);
	}

	private PomCoords peekPomCoords() {
		return pomCoords.peek();
	}

	private String getContentValueOf(ElementContext ctx) {
		String text = ctx.content().getText();
		if (text == null)
			return "";
		if (text.contains("${"))
			text = currentEntity.replaceProperty(text);
		return text;
	}

	@Override
	public void exitElement(ElementContext ctx) {
		String name = ctx.Name(0).getText();
		if (name.equals("project")) {
			if (pomParent != null) {
				peekPomCoords().fillFromIfNull(pomParent);
			}
			currentEntity.setRawName(peekPomCoords().getGenericNamePath());
			currentEntity.setQualifiedName(currentEntity.getRawName().uniqName());
			entityRepo.add(currentEntity);
			pomCoords.pop();
		} else if (name.equals("plugin")) {
			peekPomCoords().sureFillVersion(includePaths);
			currentExpression.setRawType(peekPomCoords().getGenericNamePath());
			currentEntity.addExpression(ctx, currentExpression);
			pomCoords.pop();
		} else if (name.equals("dependency")) {
			peekPomCoords().sureFillVersion(includePaths);
			currentVar.setRawType(peekPomCoords().getGenericNamePath());
			//TODO: Depends currently has a limitation: var name cannot be same as var type
			//To be fixed in future
			currentVar.setRawName(new GenericName("_" + currentVar.getRawType().getName()));
			currentEntity.addVar(currentVar);
			pomCoords.pop();
		} else if (name.equals("parent")) {
			pomParent.buildFrom(peekPomCoords());
			context.currentFile().addImport(pomParent);
			String parentFileName = new PomLocator(includePaths, pomParent).getLocation();
			if (parentFileName != null) {
				FileParser importedParser = parseCreator.createFileParser(parentFileName);
				try {
					System.out.println("parsing " + parentFileName);
					importedParser.parse();
				} catch (Exception e) {
					System.err.println("parsing error in " + parentFileName);
				}
			}
			pomCoords.pop();
			context.currentFile().inferLocalLevelEntities(inferer);
		}
		super.exitElement(ctx);
	}


	private String getParentElementName(ParserRuleContext node) {
		node = node.getParent();
		if (node == null)
			return "project";
		node = node.getParent();
		if (!(node instanceof ElementContext))
			return "project";

		ElementContext p = (ElementContext) node;
		String name = p.Name().get(0).getText();
		return name;
	}

	public void done() {
		context.done();
	}

}
