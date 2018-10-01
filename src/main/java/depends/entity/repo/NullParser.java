package depends.entity.repo;

import depends.extractor.BuiltInTypeIdenfier;

public class NullParser extends BuiltInTypeIdenfier {

	@Override
	public String[] getBuiltInTypeStr() {
		return new String[] {};
	}

	@Override
	public String[] getBuiltInPrefixStr() {
		return new String[] {};
	}

}
