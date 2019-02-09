package depends.extractor.ruby;

import depends.entity.repo.BuiltInType;

public class RubyBuiltInType extends BuiltInType {
	public RubyBuiltInType() {
		super.createBuiltInTypes();
	}

	@Override
	public String[] getBuiltInTypeStr() {
		return new String[] { };
	}

	@Override
	public String[] getBuiltInPrefixStr() {
		return new String[] {};
	}

}
