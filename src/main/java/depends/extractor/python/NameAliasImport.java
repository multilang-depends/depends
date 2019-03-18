package depends.extractor.python;

import depends.importtypes.Import;

public class NameAliasImport extends Import {

	private String aliasedName;

	public NameAliasImport(String importedName, String aliasedName) {
		super(importedName);
		this.aliasedName = aliasedName;
	}

}
