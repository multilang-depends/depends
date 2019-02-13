package depends.extractor.ruby;

import depends.entity.repo.BuiltInType;

public class RubyBuiltInType extends BuiltInType {
	public RubyBuiltInType() {
		super.createBuiltInTypes();
	}

	@Override
	public String[] getBuiltInTypeStr() {
		return new String[] {};
	}

	@Override
	public String[] getBuiltInPrefixStr() {
		return new String[] {};
	}

	@Override
	public String[] getBuiltInMethods() {
		return new String[] { "+", "-", "*", "/", "**", "%", "&", "<", "<=", ">", ">=", "==", "!=", "===", "<<", ">>",
				"~", "!", "^", "new", "!~", "<=>", "===", "=~", "[]", "class", "clone", "define_singleton_method", "display",
				"dup", "enum_for", "eql?", "extend", "freeze", "frozen?", "inspect", "instance_of?",
				"instance_variable_defined?", "instance_variable_get", "instance_variable_set", "instance_variables",
				"is_a?", "itself", "kind_of?", "method", "methods", "nil?", "object_id", "private_methods",
				"protected_methods", "public_method", "public_methods", "public_send", "remove_instance_variable",
				"respond_to?", "respond_to_missing?", "send", "singleton_class", "singleton_method",
				"singleton_methods", "taint", "tainted?", "tap", "then", "to_enum", "to_s", "trust", "untaint",
				"untrust", "untrusted?", "yield_self","each","join","empty?","copy","size" };
	}

}
