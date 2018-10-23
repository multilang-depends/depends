package depends.extractor.cpp.g4;

import depends.javaextractor.CPP14Parser.SimpletypespecifierContext;

/*
simpletypespecifier
:
	nestednamespecifier? thetypename
	| nestednamespecifier Template simpletemplateid
	| Char
	| Char16
	|...
	| decltypespecifier
 */
public class SimpletypespecifierContextHelper {

	private SimpletypespecifierContext ctx;
	private String typeName;

	public SimpletypespecifierContextHelper(SimpletypespecifierContext ctx) {
		this.ctx = ctx;
		if (ctx.decltypespecifier()!=null) {
			
		}
		else if (ctx.Template()!=null) {
			
		}
		else if (ctx.thetypename()!=null) {
			
		}else { //Char, ...
			typeName = ctx.getText();
		}
	}

	public String getTypeName() {
		return this.typeName;
	}

}
