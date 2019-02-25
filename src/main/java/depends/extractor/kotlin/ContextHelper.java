package depends.extractor.kotlin;

import depends.extractor.kotlin.KotlinParser.IdentifierContext;
import depends.extractor.kotlin.KotlinParser.SimpleIdentifierContext;

public class ContextHelper {

	public static String getName(IdentifierContext identifier) {
		StringBuffer sb = new StringBuffer();
		for (SimpleIdentifierContext id:identifier.simpleIdentifier()) {
			if (sb.length()>0) {
				sb.append(".");
			}
			sb.append(id.getText());
		}
		return sb.toString();
	}

}
