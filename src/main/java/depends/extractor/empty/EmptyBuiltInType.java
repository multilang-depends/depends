package depends.extractor.empty;

import depends.entity.repo.BuiltInType;

public class EmptyBuiltInType extends BuiltInType {

	@Override
	public String[] getBuiltInMethods() {
		return new String[] {};
	}

	@Override
	public String[] getBuiltInTypeStr() {
		return new String[] {};
	}

	@Override
	public String[] getBuiltInPrefixStr() {
		return new String[] {};
	}
}
