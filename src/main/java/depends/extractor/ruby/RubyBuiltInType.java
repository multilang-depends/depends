package depends.extractor.ruby;

import depends.entity.repo.BuiltInType;

public class RubyBuiltInType extends BuiltInType {
	public RubyBuiltInType() {
		super.createBuiltInTypes();
	}

	@Override
	public String[] getBuiltInTypeStr() {
		return new String[] { "__ENCODING__", "__LINE__", "__FILE__", "BEGIN", "END", "alias", "and", "begin", "break",
				"case", "class", "def", "defined?", "do", "else", "elsif", "end", "ensure", "false", "for", "if", "in",
				"module", "next", "nil", "not", "or", "redo", "rescue", "retry", "return", "self", "super", "then",
				"true", "undef", "unless", "until", "when", "while", "yield" };
	}

	@Override
	public String[] getBuiltInPrefixStr() {
		return new String[] {};
	}

}
