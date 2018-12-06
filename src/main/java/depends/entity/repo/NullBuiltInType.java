package depends.entity.repo;

import depends.extractor.BuiltInType;

public class NullBuiltInType extends BuiltInType {

	@Override
	public String[] getBuiltInTypeStr() {
		return new String[] {};
	}

	@Override
	public String[] getBuiltInPrefixStr() {
		return new String[] {};
	}

}
