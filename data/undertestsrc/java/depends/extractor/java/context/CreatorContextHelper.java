package depends.extractor.java.context;

import depends.extractor.java.JavaParser.CreatorContext;

public class CreatorContextHelper {

	public static String getCreatorType(CreatorContext creator) {
		if (creator==null) return null;
		if (creator.createdName()==null) return creator.getText();
		if (creator.createdName().IDENTIFIER()==null)
			return creator.createdName().getText();
		if (creator.createdName().IDENTIFIER().size()>0)
			return creator.createdName().IDENTIFIER(0).getText();
		return null;
	}
}
